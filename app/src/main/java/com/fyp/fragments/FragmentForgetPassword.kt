package com.fyp.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.utils.Util
import kotlinx.android.synthetic.main.fragment_forget_password.*
import java.util.*


class FragmentForgetPassword() : Fragment(), View.OnClickListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forget_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        resetPasswordTv.setOnClickListener(this)
    }

    private fun validation(): Boolean {
        var uname = emailTv.text.toString().trim()
        return if (uname.isNullOrEmpty()) {
            emailTv.error = resources.getString(R.string.mob_err)
            emailTv.requestFocus()
            false
        } else if (!Util.isValidEmail(uname)) {
            emailTv.error = resources.getString(R.string.email_err)
            emailTv.requestFocus()
            false
        } else {
            true
        }
    }

    @SuppressLint("WrongConstant")
    fun forgetPwd(email: String) {
        val progressDialog =
            ProgressDialog.show(activity, "Please wait", "Reset email emailing...", true)
        /*FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        activity,
                        "Reset password link have sent to your this $email please check",
                        3000
                    ).show()
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(
                        activity,
                        task.exception!!.message.toString(),
                        3000
                    ).show()
                }
                progressDialog.dismiss()
            }*/
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.resetPasswordTv -> {
                if (validation()) {
                    var uname = emailTv.text.toString().trim()
                    forgetPwd(uname)
                }
            }
        }
    }
}