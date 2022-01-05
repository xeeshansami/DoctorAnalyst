package com.fyp.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
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
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.Constant
import com.fyp.utils.GlobalClass
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import kotlinx.android.synthetic.main.fragment_signin.*
import kotlinx.android.synthetic.main.fragment_signup.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.HashMap


class FragmentSignin() : Fragment(), View.OnClickListener {

//    private var firebaseAuth: FirebaseAuth? = null
    private var sessionManager: SessionManager? = null
    val globalClass = GlobalClass.applicationContext!!.applicationContext as GlobalClass
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
//        firebaseAuth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(activity as LogActivity)
        signInBtn.setOnClickListener(this)
        signInAccountDescTxt.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.signInBtn -> {
                if (validation()) {
                    login()
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
            username.error = resources.getString(R.string.mob_err)
            username.requestFocus()
            false
        } else if (!uname.toString().startsWith("03")) {
            username.error = resources.getString(R.string.mobile_err_2)
            username.requestFocus()
            false
        } else if (uname.length!= 11) {
            username.error = resources.getString(R.string.mob_err_digits)
            username.requestFocus()
            false
        } else {
            true
        }
    }
    fun login() {
        (activity as LogActivity). globalClass?.showDialog(activity)
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("phone", username.text.toString().trim())
            .build()
        HBLHRStore.instance?.login(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
                    if (response.message.contains("exist")) {
                        sessionManager!!.setStringVal(Constant.MOBILE,  username.text.toString().trim())
                        Log.i("NUMBERCHECK",   sessionManager!!.getStringVal(Constant.MOBILE)!!)
                        ToastUtils.showToastWith(activity, "Registration successfully...")
                        (activity as LogActivity).finish()
                        findNavController().navigate(R.id.action_signin_to_dashboard)
                    } else{
                        ToastUtils.showToastWith(activity, response.message,"")
                    }
                    (activity as LogActivity).globalClass?.hideLoader()
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith(activity, response.message, "")
                    (activity as LogActivity).globalClass?.hideLoader()
                }
            })
    }

    private fun signIn(mobile: String) {
        var check = false
        var progressDialog = ProgressDialog.show(
            activity,
            resources.getString(R.string.please_wait),
            resources.getString(R.string.loading_in),
            true
        )
//        firebaseAuth!!.signInWithEmailAndPassword(
//            username,password
//        )
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    (activity as LogActivity).finish()
//                    findNavController().navigate(R.id.action_signin_to_dashboard)
//                    sessionManager?.setStringVal(Constant.USER_NAME,username)
//                    Toast.makeText(activity, "Login successfully...", Toast.LENGTH_SHORT).show()
//                } else {
//                    Log.e("ERROR", task.exception?.message.toString())
//                    Toast.makeText(activity, task.exception!!.message, Toast.LENGTH_LONG)
//                        .show()
//                }
//                progressDialog.dismiss()
//            }
//        val rootRef = FirebaseDatabase.getInstance().reference
//        val dbRef = rootRef.child("upwork-f2a18-default-rtdb").child("RegisteredUsers")
//        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (d in dataSnapshot.children) {
//                        if (d.child("mobile").value == mobile) {
//                            check = true
//                            break
//                        } else {
//                            check = false
//                        }
//                    }
//                    if (check) {
//                        sessionManager!!.setStringVal(Constant.MOBILE, mobile)
//                        (activity as LogActivity).finish()
//                        findNavController().navigate(R.id.action_signin_to_dashboard)
//                        sessionManager?.setStringVal(Constant.USER_NAME, mobile)
//                        Toast.makeText(activity,  resources.getString(R.string.login_success), Toast.LENGTH_SHORT)
//                            .show()
//                        progressDialog.dismiss()
//                    } else {
//                        Toast.makeText(
//                            activity,
//                            resources.getString(R.string.login_err), Toast.LENGTH_LONG
//                        )
//                            .show()
//                        progressDialog.dismiss()
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                progressDialog.dismiss()
//            } //onCancelled
//        })
    }
}