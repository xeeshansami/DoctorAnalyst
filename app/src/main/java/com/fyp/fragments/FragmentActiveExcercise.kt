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
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.videoObjects
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_active_excercise.*


class FragmentActiveExcercise : Fragment(), View.OnClickListener, iOnBackPressed {
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
        myView = inflater.inflate(R.layout.fragment_active_excercise, container, false)
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
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        headings!!.add("Bilateral Shoulder Flexion with both Hands Interlocked")
        urls!!.add("https://www.dropbox.com/s/qks0tpz1akfztwv/01%20Bilateral%20Shoulder%20Flexion%20with%20both%20Hands%20Interlocked.mp4?dl=0")
        text!!.add("•\tPatient should be in supine lying position.\n" +
                "•\tAsk the patient to clasp/hold the affected hand with the help of unaffected hand.\n" +
                "•\tThen flex both shoulders as demonstrated in the video.\n" +
                "•\tRepeat this exercise 10 times.\n")

        headings!!.add("Shoulder Horizontal Abduction")
        urls!!.add("https://www.dropbox.com/s/gknm7fgon1srk2j/02%20Shoulder%20Horizontal%20Abduction.mp4?dl=0")
        text!!.add("•\tPatient should be in supine lying position.\n" +
                "•\tAsk the patient to clasp/hold the affected hand with the help of unaffected hand.\n" +
                "•\tBring both arms at the level of 90° then move the arms right and left side as demonstrated in the video.\n" +
                "•\tRepeat this exercise 10 times.\n")

        headings!!.add("Shoulder Abduction")
        urls!!.add("https://www.dropbox.com/s/st8mtid468pxhtq/03%20Shoulder%20Abduction.mp4?dl=0")
        text!!.add("•\tPatient should be in supine lying position.\n" +
                "•\tAsk the patient to abduct the affected arm as demonstrated in the video.\n" +
                "•\tRepeat this exercise 10 times.\n")

        headings!!.add("Elbow Flexion and Extension")
        urls!!.add("https://www.dropbox.com/s/2y8hwhz62svsfny/04%20Elbow%20Flexion%20and%20Extension.mp4?dl=0")
        text!!.add("•\tAsk the patient to place the affected arm on the table.\n" +
                "•\tThen bend (flex) and straighten (extend) the elbow as demonstrated in the video.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this exercise 10 times.\n")

        headings!!.add("Elbow Pronation and Supination")
        urls!!.add("https://www.dropbox.com/s/tqto0s786048qy7/05%20Elbow%20Pronation%20and%20Supination.mp4?dl=0")
        text!!.add("•\tAsk the patient to place the affected arm on the table.\n" +
                "•\tPerform the pronation and supination of elbow as demonstrated in the video.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this exercise 10 times.\n")

        headings!!.add("Wrist Flexion and Extension")
        urls!!.add("https://www.dropbox.com/s/lb7ebx240x1hss1/06%20Wrist%20Flexion%20and%20Extension.mp4?dl=0")
        text!!.add("•\tAsk the patient to clasp/hold the affected hand with the help of unaffected hand.\n" +
                "•\tThen bend and straighten the wrist as demonstrated in the video.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this exercise 10 times.\n")

        headings!!.add("Extend Elbow")
        urls!!.add("https://www.dropbox.com/s/mgy6rc815fdalm4/07%20Extend%20Elbow.mp4?dl=0")
        text!!.add("•\tPatient should be sitting at the side of the table.\n" +
                "•\tAsk the patient to place affected arm on the table and straighten the elbow sideways as demonstrated in the video.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Forearm to Box")
        urls!!.add("https://www.dropbox.com/s/ikn39lrhtah1n27/08%20Forearm%20to%20Box.mp4?dl=0")
        text!!.add("•\tPatient should be sitting at the side of the table.\n" +
                "•\tPlace a box on the table.\n" +
                "•\tAsk the patient to place affected forearm on the box then remove it as demonstrated in the video.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Hand to Box")
        urls!!.add("https://www.dropbox.com/s/fsg967twj1iywaa/09%20Hand%20to%20Box.mp4?dl=0")
        text!!.add("•\tPatient should be sitting in front of the table.\n" +
                "•\tPlace a box on the table.\n" +
                "•\tAsk the patient to place affected hand on the box then remove it as demonstrated in the video.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")
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
            R.id.button2 -> {
                //02 Shoulder Horizontal Abduction
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
            R.id.button3 -> {
                //03 Shoulder Abduction
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    2
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button4 -> {
                //04 Elbow Flexion and Extension
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    3
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button5 -> {
                //05 Elbow Pronation and Supination
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    4
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button6 -> {
                //06 Wrist Flexion and Extension
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    5
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button7 -> {
                //07 Extend Elbow
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    6
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button8 -> {
                //08 Forearm to Box
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    7
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button9 -> {
                //09 Hand to Box
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )
                bundle.putInt(
                    Constant.POSITION,
                    8
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentActiveExcercise) {
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