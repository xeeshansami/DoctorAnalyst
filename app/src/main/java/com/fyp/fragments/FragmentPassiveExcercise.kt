package com.fyp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.AppLang
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.mQuestions
import com.fyp.models.videoObjects
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import kotlinx.android.synthetic.main.fragment_passive_excercise.*


class FragmentPassiveExcercise : Fragment(), View.OnClickListener, iOnBackPressed {
    var list : ArrayList<videoObjects>?=null
    var headings = ArrayList<String>()
    var urls = ArrayList<String>()
    var text = ArrayList<String>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_passive_excercise, container, false)
        return myView
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
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


    private fun init() {
        list = ArrayList<videoObjects>()
        urls = ArrayList<String>()
        text = ArrayList<String>()
        headings = ArrayList<String>()
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        headings!!.add(resources.getString(R.string.roms_for_elbow))
        urls!!.add("https://strokex.xoqax.com/videos/passive-roms-for-elbow.mp4")
        text!!.add(resources.getString(R.string.roms_for_elbow_str))


        headings!!.add(resources.getString(R.string.roms_for_wrist_fingers_and_thumb))
        urls!!.add("https://strokex.xoqax.com/videos/passive-roms-for-wrist-fingers-and-thumb.mp4")
        text!!.add(resources.getString(R.string.roms_for_wrist_fingers_and_thumb_str))
        for (x in 0 until  headings.size) {
            var obj = videoObjects()
            obj.heading=headings[x]
            obj.videoUrl=urls[x]
            obj.text=text[x]
            list!!.add(obj)
        }
    }


    override fun onClick(v: View?) {
        var bundle = Bundle()
        when (v!!.id) {
            R.id.button1 -> {
                findNavController().navigate(R.id.action_passive_excercise2, bundle)
            }
            R.id.button2 -> {
                //01 Bilateral Shoulder Flexion with both Hands Interlocked
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )

                bundle.putInt(
                    Constant.POSITION,
                    0
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button3 -> {
                //01 Bilateral Shoulder Flexion with both Hands Interlocked
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    1
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentPassiveExcercise) {
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