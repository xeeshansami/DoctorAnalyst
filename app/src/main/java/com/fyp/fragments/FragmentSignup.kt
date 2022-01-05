package com.fyp.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.LogActivity
import com.fyp.interfaces.iOnBackPressed
import com.fyp.network.models.request.base.RegisterRequest
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.Constant
import com.fyp.utils.GlobalClass
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.google.gson.Gson
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import kotlinx.android.synthetic.main.fragment_signin.*
import kotlinx.android.synthetic.main.fragment_signup.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.util.*
import kotlin.collections.ArrayList


class FragmentSignup() : Fragment(), View.OnClickListener, iOnBackPressed {
    private val myCalendar: Calendar = Calendar.getInstance()

    //    private var firebaseAuth: FirebaseAuth? = null
//    private var mDatabase:DatabaseReference?=null
    private var sessionManager: SessionManager? = null
    val globalClass = GlobalClass.applicationContext!!.applicationContext as GlobalClass
    private var list = ArrayList<String>()
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
        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                //Handle the back pressed
//                Toast.makeText(activity,"asdas",2000).show()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    }

    fun register() {
        (activity as LogActivity).globalClass?.showDialog(activity)
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("name", firstNameTv.text.toString().trim())
            .addFormDataPart("age", age.text.toString().trim())
            .addFormDataPart("phone", mobileTv.text.toString().trim())
            .addFormDataPart("gender", spinner.selectedItem.toString().trim())
            .addFormDataPart("city", cityTv.text.toString().trim())
            .build()
        HBLHRStore.instance?.register(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
                    if (response.message.contains("inserted")) {
                        sessionManager!!.setStringVal(
                            Constant.MOBILE,
                            mobileTv.text.toString().trim()
                        )
                        Log.i("NUMBERCHECK",   sessionManager!!.getStringVal(Constant.MOBILE)!!)
                        ToastUtils.showToastWith(activity, "Registration successfully...")
                        (activity as LogActivity).finish()
                        findNavController().navigate(R.id.action_signup_to_dashboard)
                    } else if (response.message.contains("exist")) {
                        ToastUtils.showToastWith(activity, "Account Already Exist!","")
                    }else{
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


    fun init() {
        sessionManager = SessionManager(activity as LogActivity)
//        firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(this)
        registerBtn.setOnClickListener(this)
        addQuestInRv()
    }

    private fun addQuestInRv() {
        val questions = (activity as LogActivity).resources!!.getStringArray(R.array.gender)
        list.clear()
        for (element in questions) {
            list.add(element)
        }
        val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
            activity as LogActivity,
            R.layout.quest_list_view, R.id.text1,
            list
        )
        adapter.setDropDownViewResource(R.layout.quest_list_view)
        spinner.setAdapter(adapter)
    }

    private fun validation(): Boolean {
        var fName = firstNameTv.text.toString().trim()
        var ageValue = age.text.toString().trim()
        var mobile = mobileTv.text.toString().trim()
        var cityTvValue = cityTv.text.toString().trim()
        return if (fName.isNullOrEmpty()) {
            firstNameTv.error = resources.getString(R.string.fname_err)
            firstNameTv.requestFocus()
            false
        } else if (ageValue.isNullOrEmpty()) {
            age.error = resources.getString(R.string.lname_err)
            age.requestFocus()
            false
        } else if (cityTvValue.isNullOrEmpty()) {
            age.error = resources.getString(R.string.city_err)
            age.requestFocus()
            false
        } else if (mobile.isNullOrEmpty()) {
            mobileTv.error = resources.getString(R.string.mobile_err)
            mobileTv.requestFocus()
            false
        } else if (!mobile.toString().startsWith("03")) {
            mobileTv.error = resources.getString(R.string.mobile_err_2)
            mobileTv.requestFocus()
            false
        } else if (mobile.length < 11) {
            mobileTv.error =
                resources.getString(R.string.mobile_digits_err)
            mobileTv.requestFocus()
            false
        } else {
            true
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.registerBtn -> {
                if (validation()) {
                    register()
                }
            }
            R.id.login -> {
                onBackPressed()
            }
        }
    }

    private fun signup(
        fName: String,
        lName: String,
        mobile: String,
    ) {
        val progressDialog = ProgressDialog.show(activity, "Please wait", "Registration...", true)
//        firebaseAuth?.createUserWithEmailAndPassword(fName, lName)
//            ?.addOnCompleteListener(activity as LogActivity,
//                OnCompleteListener<AuthResult?> { task ->
//                    Log.d("TAG", "createUserWithEmail:onComplete:" + task.isSuccessful)
//                    // If sign in fails, display a message to the user. If sign in succeeds
//                    // the auth state listener will be notified and logic to handle the
//                    // signed in user can be handled in the listener.
//                    if (task.isSuccessful) {
////                        addToFirebaseDB(fName, lName, mobile, age, cityTv, gender)
//                    }else{
//                        Toast.makeText(
//                            activity, task.exception?.message.toString(),
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                    progressDialog.dismiss()
//                })
    }

    private fun addToFirebaseDB(
        fName: String,
        lName: String,
        mobile: String,
        age: String,
        cityTv: String,
        gender: String,
    ) {
        var progressDialog =
            ProgressDialog.show(activity, "Please wait", "Registration is in process...", true)
//        mDatabase = FirebaseDatabase.getInstance().getReference("upwork-f2a18-default-rtdb");
//        val hashMap: HashMap<String, String> = HashMap()
//        hashMap["fName"] = fName
//        hashMap["lName"] = lName
//        hashMap["city"] = cityTv
//        hashMap["age"] = age
//        hashMap["gender"] = gender
//        hashMap["mobile"] = mobile
//        mDatabase?.child("RegisteredUsers")
//            ?.push()
//            ?.setValue(hashMap)
//            ?.addOnCompleteListener {
//                sessionManager!!.setStringVal(Constant.MOBILE, mobile)
//                (activity as LogActivity).finish()
//                findNavController().navigate(R.id.action_signup_to_dashboard)
//                Toast.makeText(
//                    activity, "Registration successfully...",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }?.addOnFailureListener { e ->
//                Toast.makeText(
//                    activity,
//                    e.message,
//                    Toast.LENGTH_LONG
//                ).show()
//            }

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
        val navController = requireActivity().findNavController(R.id.fragment1)
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