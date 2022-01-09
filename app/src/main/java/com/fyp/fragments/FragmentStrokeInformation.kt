package com.fyp.fragments

import android.os.Build
import android.os.Bundle
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
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager


class FragmentStrokeInformation : Fragment(), View.OnClickListener ,iOnBackPressed,
    iOnVideoItemClickListner {
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