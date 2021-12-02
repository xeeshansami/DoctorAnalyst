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
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import kotlinx.android.synthetic.main.fragment_signin.*


class FragmentSignin() : Fragment(), View.OnClickListener {

    private var firebaseAuth: FirebaseAuth? = null
    private var sessionManager: SessionManager? = null
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
        sessionManager = SessionManager(activity as LogActivity)
        signInBtn.setOnClickListener(this)
        signInAccountDescTxt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.signInBtn -> {
                if (validation()) {
//                    var uname = username.text.toString().trim()
//                    signIn(uname)
                    findNavController().navigate(R.id.action_signin_to_dashboard)
//                    sessionManager?.setStringVal(Constant.USER_NAME,username)
                    Toast.makeText(activity, "Login successfully...", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.signInAccountDescTxt -> {
                findNavController().navigate(R.id.action_signin_to_signup)
            }
        }
    }

    private fun validation(): Boolean {
        var uname = username.text.toString().trim()
        return if (uname.isNullOrEmpty()) {
            username.error = "Please enter the mobile number"
            username.requestFocus()
            false
        }  else {
            true
        }
    }

    private fun signIn(username: String) {
        val progressDialog =
            ProgressDialog.show(activity, "Please wait", "Logging in...", true)
        firebaseAuth!!.signInWithEmailAndPassword(
            username,""
        )
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    (activity as LogActivity).finish()
                    findNavController().navigate(R.id.action_signin_to_dashboard)
                    sessionManager?.setStringVal(Constant.USER_NAME,username)
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