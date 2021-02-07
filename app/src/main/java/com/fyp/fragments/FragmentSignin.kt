package com.fyp.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.LogActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.fragment_signin.*


class FragmentSignin() : Fragment(), View.OnClickListener {

    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        signInBtn.setOnClickListener(this)
        signUpBtn.setOnClickListener(this)
        forgetPwdTxt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.signInBtn -> {
                if (validation()) {
                    var uname = username.text.toString().trim()
                    var pwd = etPassword.text.toString().trim()
                    signIn(username = uname, password = pwd)
                }
            }
            R.id.signUpBtn -> {
                findNavController().navigate(R.id.action_signin_to_signup)
            }
            R.id.forgetPwdTxt -> {
                findNavController().navigate(R.id.action_signin_to_fragmentForgetPassword)
            }
        }
    }

    private fun validation(): Boolean {
        var uname = username.text.toString().trim()
        var pwd = etPassword.text.toString().trim()
        return if (uname.isNullOrEmpty()) {
            username.error = "Please enter the username"
            username.requestFocus()
            false
        } else if (pwd.isNullOrEmpty()) {
            username.error = "Please enter the password"
            username.requestFocus()
            false
        } else {
            true
        }
    }

    private fun signIn(username: String, password: String) {
        val progressDialog =
            ProgressDialog.show(activity, "Please wait", "Logging in...", true)
        firebaseAuth!!.signInWithEmailAndPassword(
            username,
            password
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    (activity as LogActivity).finish()
                    findNavController().navigate(R.id.action_signin_to_dashboard)
                    Toast.makeText(activity, "Login successfully...", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("ERROR", task.exception?.message.toString())
                    Toast.makeText(activity, task.exception!!.message, Toast.LENGTH_LONG)
                        .show()
                }
                progressDialog.dismiss()
            }
    }
}