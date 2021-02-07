package com.fyp.fragments

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.fyp.utils.Util
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_signup.*
import kotlinx.android.synthetic.main.fragment_signup.confirmEPassword
import kotlinx.android.synthetic.main.fragment_signup.dateOfBirthTv
import kotlinx.android.synthetic.main.fragment_signup.etPassword
import kotlinx.android.synthetic.main.fragment_signup.firstNameTv
import kotlinx.android.synthetic.main.fragment_signup.lastNameTv
import kotlinx.android.synthetic.main.fragment_signup.mobileTv
import kotlinx.android.synthetic.main.fragment_update.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FragmentUpdate() : Fragment() ,iOnBackPressed, View.OnClickListener {
    val myCalendar = Calendar.getInstance()
    private var mDatabase: DatabaseReference? = null
    private var sessionManager: SessionManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        openDateDialog()
    }
    fun init(){
        updateBtn.setOnClickListener(this)
        sessionManager = SessionManager(activity as ActivityDashboard)
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
    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        if (navController.currentDestination?.id != R.id.fragmentUpdate) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            return true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            return true
        }
    }

    fun updateDB() {
        try {
            var pwd = etPassword.text.toString().trim()
            var confirmPwd = confirmEPassword.text.toString().trim()
            updatePwd(pwd,confirmPwd)
        } catch (ex: Exception) {
            Toast.makeText(
                activity,
                ex.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun updatePwd(currPwd: String, newPass: String) {
        val progressDialog =
            ProgressDialog.show(
                activity,
                "Please wait",
                "Checking and updating profile...",
                true
            )
        var user = FirebaseAuth.getInstance().currentUser;
        var credential = EmailAuthProvider
            .getCredential(sessionManager?.getStringVal(Constant.USER_NAME).toString(), currPwd);
        user?.reauthenticate(credential)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    user.updatePassword(newPass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            var firebaseAuth = FirebaseAuth.getInstance()
                            var fName = firstNameTv.text.toString().trim()
                            var lName = lastNameTv.text.toString().trim()
                            var mobile = mobileTv.text.toString().trim()
                            var dateOfBirth = dateOfBirthTv.text.toString().trim()
                            mDatabase = FirebaseDatabase.getInstance().getReference("fyproject-6150d").child("RegisteredUsers").child(firebaseAuth.currentUser!!.uid);
                            mDatabase!!.child("fName").setValue(fName);
                            mDatabase!!.child("lName").setValue(lName);
                            mDatabase!!.child("mobile").setValue(mobile);
                            mDatabase!!.child("dateOfBirth").setValue(dateOfBirth);
                            mDatabase!!.child("password").setValue(newPass);
                            Toast.makeText(
                                activity,
                                sessionManager?.getStringVal(Constant.USER_NAME)
                                    .toString() + ": profile successfully updated!",
                                Toast.LENGTH_LONG
                            ).show()
                            findNavController().popBackStack()
                        } else {
                            Toast.makeText(
                                activity,
                                it.exception?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    };
                } else {
                    Toast.makeText(activity, it.exception?.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                progressDialog.dismiss()
            };
    }
    private fun validation(): Boolean {
        var fName = firstNameTv.text.toString().trim()
        var lName = lastNameTv.text.toString().trim()
        var mobile = mobileTv.text.toString().trim()
        var dateOfBirth = dateOfBirthTv.text.toString().trim()
        var pwd = etPassword.text.toString().trim()
        var newPwdET = newPwd.text.toString().trim()
        var confirmPwd = confirmNewPassword.text.toString().trim()
        return if (fName.isNullOrEmpty()) {
            firstNameTv.error = "Please enter the first name!"
            firstNameTv.requestFocus()
            false
        } else if (lName.isNullOrEmpty()) {
            lastNameTv.error = "Please enter the last name!"
            lastNameTv.requestFocus()
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
            etPassword.error = "Please enter the old password!"
            etPassword.requestFocus()
            false
        } else if (newPwdET.isNullOrEmpty()) {
            newPwd.error = "Please enter the new password!"
            newPwd.requestFocus()
            false
        } else if (confirmPwd.isNullOrEmpty()) {
            confirmNewPassword.error = "Please enter the confirm new password!"
            confirmNewPassword.requestFocus()
            false
        } else if (confirmPwd != pwd) {
            newPwd.error = "New and confirm password is not match"
            newPwd.requestFocus()
            confirmNewPassword.setText("")
            false
        } else {
            true
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.updateBtn->{
                if(validation()){
                    updateDB()
                }
            }
        }
    }
}