package com.fyp.fragments

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.LogActivity
import com.fyp.interfaces.iOnBackPressed
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_signup.*
import java.text.SimpleDateFormat
import java.util.*


class FragmentSignup() : Fragment(), View.OnClickListener, iOnBackPressed {
    private val myCalendar: Calendar = Calendar.getInstance()
    private var firebaseAuth: FirebaseAuth? = null
    private var mDatabase:DatabaseReference?=null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        openDateDialog()
    }

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance();
        registerBtn.setOnClickListener(this)
    }

    private fun openDateDialog() {
        dateOfBirthTv!!.setOnClickListener {
            var datePicker =
                activity?.let {
                    DatePickerDialog(
                        it,
                        R.style.DialogTheme,
                        date,
                        myCalendar[Calendar.YEAR],
                        myCalendar[Calendar.MONTH],
                        myCalendar[Calendar.DAY_OF_MONTH]
                    )
                }
//            datePicker?.getDatePicker()?.setMaxDate(System.currentTimeMillis())
            datePicker?.show()
        }
    }

    var date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        // TODO Auto-generated method stub
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        myCalendar.add(Calendar.DATE, 0);
        // Set the Calendar new date as minimum date of date picker
        //val myFormat = "dd-MMM-yyyy" //In which you need put here
        val myFormat = "yyyy-MM-dd"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        dateOfBirthTv.setText(sdf.format(myCalendar.time))
    }


    private fun validation(): Boolean {
        var fName = firstNameTv.text.toString().trim()
        var lName = lastNameTv.text.toString().trim()
        var email = emailTv.text.toString().trim()
        var mobile = mobileTv.text.toString().trim()
        var dateOfBirth = dateOfBirthTv.text.toString().trim()
        var pwd = etPassword.text.toString().trim()
        var confirmPwd = confirmEPassword.text.toString().trim()
        return if (fName.isNullOrEmpty()) {
            firstNameTv.error = "Please enter the first name!"
            firstNameTv.requestFocus()
            false
        } else if (lName.isNullOrEmpty()) {
            lastNameTv.error = "Please enter the last name!"
            lastNameTv.requestFocus()
            false
        } else if (email.isNullOrEmpty()) {
            emailTv.error = "Please enter the email!"
            emailTv.requestFocus()
            false
        } else if (!isValidEmail(email)) {
            emailTv.error = "Please enter valid email address!"
            emailTv.requestFocus()
            false
        } else if (mobile.isNullOrEmpty()) {
            mobileTv.error = "Please enter the mobile number!"
            mobileTv.requestFocus()
            false
        } else if (mobile.length < 11) {
            mobileTv.error =
                "Please enter 11 digits of mobile number and also start with 03xxxxxxxxx!"
            mobileTv.requestFocus()
            false
        } else if (dateOfBirth.isNullOrEmpty()) {
            dateOfBirthTv.error = "Please select date of birth!"
            dateOfBirthTv.requestFocus()
            false
        } else if (pwd.isNullOrEmpty()) {
            etPassword.error = "Please enter the password!"
            etPassword.requestFocus()
            false
        } else if (confirmPwd.isNullOrEmpty()) {
            confirmEPassword.error = "Please enter the confirm password!"
            confirmEPassword.requestFocus()
            false
        } else if (confirmPwd != pwd) {
            etPassword.error = "Password and confirm password is not match"
            etPassword.requestFocus()
            confirmEPassword.setText("")
            false
        } else {
            true
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.registerBtn -> {
                if (validation()) {
                    var fName = firstNameTv.text.toString().trim()
                    var lName = lastNameTv.text.toString().trim()
                    var email = emailTv.text.toString().trim()
                    var mobile = mobileTv.text.toString().trim()
                    var dateOfBirth = dateOfBirthTv.text.toString().trim()
                    var confirmPwd = confirmEPassword.text.toString().trim()
                    signup(email, confirmPwd, fName, lName, mobile, dateOfBirth)
                }
            }
            R.id.signInBtn -> {
                onBackPressed()
            }
        }
    }

    private fun signup(
        email: String,
        password: String,
        fName: String,
        lName: String,
        mobile: String,
        dateOfBirth: String
    ) {
        val progressDialog = ProgressDialog.show(activity, "Please wait", "Registration...", true)
        firebaseAuth?.createUserWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(activity as LogActivity,
                OnCompleteListener<AuthResult?> { task ->
                    Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful)
                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (task.isSuccessful) {
                        addToFirebaseDB(email, password, fName, lName, mobile, dateOfBirth)
                    }else{
                        Toast.makeText(
                            activity, task.exception?.message.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    progressDialog.dismiss()
                })
    }

    private fun addToFirebaseDB(
        email: String,
        password: String,
        fName: String,
        lName: String,
        mobile: String,
        dateOfBirth: String
    ) {
        mDatabase = FirebaseDatabase.getInstance().getReference("fyproject-6150d");
        val hashMap: HashMap<String, String> = HashMap()
        hashMap["email"] = email
        hashMap["password"] = password
        hashMap["fName"] = fName
        hashMap["lName"] = lName
        hashMap["mobile"] = mobile
        hashMap["dateOfBirth"] = dateOfBirth
        mDatabase?.child("RegisteredUsers")
            ?.child(firebaseAuth?.uid.toString())
            ?.setValue(hashMap)
            ?.addOnCompleteListener {
                (activity as LogActivity).finish()
                findNavController().navigate(R.id.action_signup_to_dashboard)
                Toast.makeText(
                    activity, "Registration successfully...",
                    Toast.LENGTH_SHORT
                ).show()
            }?.addOnFailureListener { e ->
                Toast.makeText(
                    activity,
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun isValidEmail(target: String?): Boolean {
        return if (target == null) {
            false
        } else {
            //android Regex to check the email address Validation
            Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        if (navController.currentDestination?.id != R.id.signup) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            return true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            return true
        }
    }


}