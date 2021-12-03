package com.fyp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.mQuestions
import com.fyp.models.videoObjects
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_passive_excercise.*


class FragmentPassiveExcercise : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = ArrayList<videoObjects>()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        headings!!.add("ROMs for Elbow")
        urls!!.add("https://www.dropbox.com/s/vv8v3ytzaox12fi/03%20ROMs%20for%20Elbow.mp4?dl=0")
        text!!.add("•\tPatient should be in supine lying position.\n" +
                "•\tCaregiver should be standing at the side of patient.\n" +
                "•\tHold the patient’s affected arm from the wrist then bend the elbow and straighten it as demonstrated in the video.\n" +
                "•\tRepeat this exercise 10 times.\n")

        headings!!.add("ROMs for Wrist, Fingers and Thumb")
        urls!!.add("https://www.dropbox.com/s/dzhfrxy7w9qfj94/04%20ROMs%20for%20Wrist%2C%20Fingers%20and%20Thumb.mp4?dl=0")
        text!!.add("•\tPatient should be in supine lying position.\n" +
                "•\tCaregiver should be standing at the side of patient.\n" +
                "•\tHold the patient’s affected arm from the wrist then bend the wrist first then straighten it as demonstrated in the video.\n" +
                "•\tRepeat this exercise 10 times.\n" +
                "•\tHold the affected arm from wrist then move the thumb and fingers in all possible directions and return to original position.\n" +
                "•\tRepeat this exercise 10 times.\n")
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