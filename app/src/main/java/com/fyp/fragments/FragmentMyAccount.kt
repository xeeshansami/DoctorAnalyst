package com.fyp.fragments

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.LogActivity
import com.fyp.interfaces.iOnBackPressed
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import kotlinx.android.synthetic.main.fragment_account.*
import kotlinx.android.synthetic.main.fragment_update.*
import okhttp3.MultipartBody
import okhttp3.RequestBody


class FragmentMyAccount : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = ArrayList<String>()
    var myView: View? = null
    private var sessionManager: SessionManager? = null

//    private var firebaseAuth: FirebaseAuth? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_account, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        sessionManager = SessionManager(activity as ActivityDashboard)
//        firebaseAuth = FirebaseAuth.getInstance()
        updateAnAccountTv.setOnClickListener(this)
        changePasswordTv.setOnClickListener(this)
        logoutTv.setOnClickListener(this)
        addQuestInRv()
    }

    private fun addQuestInRv() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.updateAnAccountTv -> {
                findNavController().navigate(R.id.action_fragmentMyAccount_to_fragmentUpdate)
            }
            R.id.changePasswordTv -> {
                findNavController().navigate(R.id.action_fragmentMyAccount_to_fragmentChangePassword)
            }
            R.id.logoutTv -> {
                updateAppTime()
            }
        }
    }


    fun updateAppTime() {
        (activity as ActivityDashboard).globalClass?.showDialog( (activity as ActivityDashboard))
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("phone", sessionManager!!.getStringVal(Constant.MOBILE)!!)
            .addFormDataPart(
                "completeApplicationTime,",
                (activity as ActivityDashboard).finalTime
            )
            .build()
        HBLHRStore.instance?.updateAppTime(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
                    Toast.makeText(activity, "Log out", Toast.LENGTH_SHORT).show()
                    sessionManager!!.setStringVal(Constant.MOBILE,"")
                    var intent = Intent(activity, LogActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    (activity as ActivityDashboard).startActivity(intent)
                    (activity as ActivityDashboard).finish()
                    (activity as ActivityDashboard).globalClass?.hideLoader()
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith( (activity as ActivityDashboard), response.message)
                    (activity as ActivityDashboard).globalClass?.hideLoader()
                }
            })
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentMyAccount) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            true
        }
    }
}