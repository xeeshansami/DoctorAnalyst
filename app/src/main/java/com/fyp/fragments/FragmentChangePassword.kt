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
import androidx.annotation.NonNull
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.LogActivity
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_change_password.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class FragmentChangePassword() : Fragment(), View.OnClickListener {
    private var mDatabase: DatabaseReference? = null
    private var sessionManager: SessionManager? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        sessionManager = SessionManager(activity as ActivityDashboard)
        updateBtn.setOnClickListener(this)
    }

    private fun validation(): Boolean {
        var oldPwdET = oldPwd.text.toString().trim()
        var newPwdET = newPwd.text.toString().trim()
        var confirmNewPasswordET = confirmNewPassword.text.toString().trim()
        return if (oldPwdET.isNullOrEmpty()) {
            oldPwd.error = "Please enter the old password!"
            oldPwd.requestFocus()
            false
        } else if (newPwdET.isNullOrEmpty()) {
            newPwd.error = "Please enter the new password!"
            newPwd.requestFocus()
            false
        } else if (confirmNewPasswordET.isNullOrEmpty()) {
            confirmNewPassword.error = "Please enter the confirm password!"
            confirmNewPassword.requestFocus()
            false
        } else if (newPwdET != confirmNewPasswordET) {
            newPwd.error = "New and confirm password is not match"
            newPwd.requestFocus()
            confirmNewPassword.setText("")
            false
        } else {
            true
        }
    }

    fun updatePwd(currPwd: String, newPass: String) {
        val progressDialog =
            ProgressDialog.show(
                activity,
                "Please wait",
                "Checking and changing the password...",
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
                            updateDB("password", newPass)
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

    fun updateDB(key: String, value: String) {
        try {
            var firebaseAuth = FirebaseAuth.getInstance()
            mDatabase = FirebaseDatabase.getInstance().getReference("fyproject-6150d");
            mDatabase!!.child("RegisteredUsers").child(firebaseAuth.currentUser!!.uid)
                .child(key).setValue(value);
            Toast.makeText(
                activity,
                sessionManager?.getStringVal(Constant.USER_NAME)
                    .toString() + ": password successfully changed!",
                Toast.LENGTH_LONG
            ).show()
            findNavController().popBackStack()
        } catch (ex: Exception) {
            Toast.makeText(
                activity,
                ex.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.updateBtn -> {
                if (validation()) {
                    var oldPwdET = oldPwd.text.toString().trim()
                    var confirmNewPasswordET = confirmNewPassword.text.toString().trim()
                    updatePwd(oldPwdET, confirmNewPasswordET)
                }
            }
        }
    }

}