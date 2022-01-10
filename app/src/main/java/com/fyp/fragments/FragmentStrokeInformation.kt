package com.fyp.fragments

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.AppLang
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnVideoItemClickListner
import com.fyp.models.videoObjects
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.Constant
import com.fyp.utils.GlobalClass
import com.fyp.utils.SessionManager
import com.fyp.utils.ToastUtils
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import kotlinx.android.synthetic.main.fragment_stroke_information.*
import okhttp3.MultipartBody
import okhttp3.RequestBody


class FragmentStrokeInformation : Fragment(), View.OnClickListener ,iOnBackPressed,
    iOnVideoItemClickListner {
    var finalTime = ""
    var counter: CountDownTimer? = null
    val globalClass = GlobalClass.applicationContext!!.applicationContext as GlobalClass
    var maxCounter: Long = 1000000
    var diff: Long = 1000
    var list = ArrayList<videoObjects>()
    var myView: View? = null
    var videoPlay=true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_stroke_information, container, false)
        return myView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager= SessionManager(activity as ActivityDashboard)
        if (sessionManager!!.getIntVal(Constant.LANGUAGE) == 1) {
            AppLang.AppLang(activity, "en")
        } else {
            AppLang.AppLang(activity, "ur")
        }
    }
    var sessionManager: SessionManager? = null
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onResume() {
        super.onResume()
        counter()
        if (sessionManager!!.getIntVal(Constant.LANGUAGE) == 1) {
            AppLang.AppLang(activity, "en")
        } else {
            AppLang.AppLang(activity, "ur")
        }
    }

    override fun onItemClick(view: VideoView, question: String, position: Int) {
        if(videoPlay) {
            videoPlay=false
            view.start()
        }else{
            videoPlay=true
            view.pause()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {

        }
    }
    fun counter() {
        if (counter != null) {
            counter!!.cancel()
            counter!!.onFinish()
            counter = null
        }
        counter =
            object : CountDownTimer(maxCounter, diff) {
                override fun onTick(millisUntilFinished: Long) {
                    val diff: Long = maxCounter - millisUntilFinished
                    finalTime = (diff / 1000).toString()
                    Log.i("TickTick", finalTime)
                    //here you can have your logic to set text to edittext
                }

                override fun onFinish() {
                    counter!!.cancel()
                }
            }.start()
    }

    override fun onStop() {
        updateHistoryAppTime()
        super.onStop()
    }
    fun updateHistoryAppTime() {
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("pageName", header.text.toString())
            .addFormDataPart("phone", sessionManager!!.getStringVal(Constant.MOBILE).toString())
            .addFormDataPart("exerciseName",header1.text.toString())
            .addFormDataPart("videoScreenTime", finalTime)
            .addFormDataPart("videoUrl", text1.text.toString())
            .build()
        HBLHRStore.instance?.history(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
//                    ToastUtils.showToastWith(activity as ActivityDashboard, "His", "")
                    Log.i("Counter", "1")
                    counter()
                    globalClass?.hideLoader()
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith(activity, response.message, "")
                    globalClass?.hideLoader();
                }
            })
    }


    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentGetAndBalance) {
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