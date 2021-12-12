package com.fyp.fragments

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_signup.firstNameTv
import kotlinx.android.synthetic.main.fragment_signup.age
import kotlinx.android.synthetic.main.fragment_signup.mobileTv
import kotlinx.android.synthetic.main.fragment_update.*
import java.util.*


class FragmentUpdate : Fragment() ,iOnBackPressed, View.OnClickListener {
    val myCalendar = Calendar.getInstance()
    private var mDatabase: DatabaseReference? = null
    private var sessionManager: SessionManager? = null
    var firebaseAuth = FirebaseAuth.getInstance()
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
//        getDataFromFirebase()
    }
    fun init(){
        updateBtn.setOnClickListener(this)
        firebaseAuth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(activity as ActivityDashboard)
    }



    fun getDataFromFirebase(){
        val progressDialog =
                ProgressDialog.show(
                        activity,
                        "Please wait",
                        "Fetching your profile...",
                        true
                )
        mDatabase = FirebaseDatabase.getInstance().getReference("fyproject-6150d")
                .child("RegisteredUsers").child(firebaseAuth.currentUser!!.uid)
        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (data in dataSnapshot.children) {
                    if (data.key == "fName") {
                        firstNameTv.setText(data.value.toString())
                    }
                    if (data.key == "lName") {
                        age.setText(data.value.toString())
                    }
                    if (data.key == "mobile") {
                        mobileTv.setText(data.value.toString())
                    }
                    if (data.key == "dateOfBirth") {
                        dateOfBirthTv.text = data.value.toString()
                    }
                    progressDialog.dismiss()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog.dismiss()
            }
        })
    }

    private fun updateDB() {
        try {
            var pwd = etPassword.text.toString().trim()
            var confirmPwd = confirmNewPassword.text.toString().trim()
            updatePwd(pwd, confirmPwd)
        } catch (ex: Exception) {
            Toast.makeText(
                    activity,
                    ex.message,
                    Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updatePwd(currPwd: String, newPass: String) {
        val progressDialog =
            ProgressDialog.show(
                    activity,
                    "Please wait",
                    "Checking and updating profile...",
                    true
            )
        var user = FirebaseAuth.getInstance().currentUser
        var credential = EmailAuthProvider
            .getCredential(sessionManager?.getStringVal(Constant.USER_NAME).toString(), currPwd)
        user?.reauthenticate(credential)
            ?.addOnCompleteListener {
                if (it.isSuccessful) {
                    user.updatePassword(newPass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            var fName = firstNameTv.text.toString().trim()
                            var lName = age.text.toString().trim()
                            var mobile = mobileTv.text.toString().trim()
                            var dateOfBirth = dateOfBirthTv.text.toString().trim()
                            mDatabase = FirebaseDatabase.getInstance().getReference("fyproject-6150d").child("RegisteredUsers").child(firebaseAuth.currentUser!!.uid)
                            mDatabase!!.child("fName").setValue(fName)
                            mDatabase!!.child("lName").setValue(lName)
                            mDatabase!!.child("mobile").setValue(mobile)
                            mDatabase!!.child("dateOfBirth").setValue(dateOfBirth)
                            mDatabase!!.child("password").setValue(newPass)
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
                    }
                } else {
                    Toast.makeText(activity, it.exception?.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
                progressDialog.dismiss()
            }
    }
    private fun validation(): Boolean {
        var fName = firstNameTv.text.toString().trim()
        var lName = age.text.toString().trim()
        var mobile = mobileTv.text.toString().trim()
        var dateOfBirth = dateOfBirthTv.text.toString().trim()
        var pwd = etPassword.text.toString().trim()
        var newPwdET = newPwd.text.toString().trim()
        var confirmPwd = confirmNewPassword.text.toString().trim()
        return if (fName.isNullOrEmpty()) {
            firstNameTv.error =resources.getString(R.string.fname_err)
            firstNameTv.requestFocus()
            false
        } else if (lName.isNullOrEmpty()) {
            age.error = resources.getString(R.string.lname_err)
            age.requestFocus()
            false
        } else if (mobile.isNullOrEmpty()) {
            mobileTv.error = resources.getString(R.string.mob_err)
            mobileTv.requestFocus()
            false
        } else if (mobile.length < 11) {
            mobileTv.error =
                resources.getString(R.string.mobile_digits_err)
            mobileTv.requestFocus()
            false
        } else if (dateOfBirth.isNullOrEmpty()) {
            dateOfBirthTv.error = resources.getString(R.string.birth_err)
            dateOfBirthTv.requestFocus()
            false
        } else if (pwd.isNullOrEmpty()) {
            etPassword.error = resources.getString(R.string.pwd_err)
            etPassword.requestFocus()
            false
        } else if (newPwdET.isNullOrEmpty()) {
            newPwd.error = resources.getString(R.string.newPwd_err)
            newPwd.requestFocus()
            false
        } else if (confirmPwd.isNullOrEmpty()) {
            confirmNewPassword.error =  resources.getString(R.string.confirm_err)
            confirmNewPassword.requestFocus()
            false
        } else if (confirmPwd != newPwdET) {
            newPwd.error =  resources.getString(R.string.matchPwd_err)
            newPwd.requestFocus()
            confirmNewPassword.setText("")
            false
        } else {
            true
        }
    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.updateBtn -> {
                if (validation()) {
                    updateDB()
                }
            }
        }
    }

    override fun onBackPressed(): Boolean {
        return true
    }
}