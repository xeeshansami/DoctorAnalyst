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
        headings!!.add(resources.getString(R.string.bilateral_shoulder_flexion_with_both_hands_interlocked))
        urls!!.add("https://www.dropbox.com/s/qks0tpz1akfztwv/01%20Bilateral%20Shoulder%20Flexion%20with%20both%20Hands%20Interlocked.mp4?dl=0")
        text!!.add(resources.getString(R.string.bilateral_shoulder_flexion_with_both_hands_interlocked_Str))

        headings!!.add(resources.getString(R.string.shoulder_horizontal_abduction))
        urls!!.add("https://www.dropbox.com/s/gknm7fgon1srk2j/02%20Shoulder%20Horizontal%20Abduction.mp4?dl=0")
        text!!.add(resources.getString(R.string.shoulder_horizontal_abduction_str))

        headings!!.add(resources.getString(R.string.shoulder_abduction))
        urls!!.add("https://www.dropbox.com/s/st8mtid468pxhtq/03%20Shoulder%20Abduction.mp4?dl=0")
        text!!.add(resources.getString(R.string.shoulder_abduction_str))

        headings!!.add(resources.getString(R.string.elbow_flexion_and_extension))
        urls!!.add("https://www.dropbox.com/s/2y8hwhz62svsfny/04%20Elbow%20Flexion%20and%20Extension.mp4?dl=0")
        text!!.add(resources.getString(R.string.elbow_flexion_and_extension_str))

        headings!!.add(resources.getString(R.string.elbow_pronation_and_supination))
        urls!!.add("https://www.dropbox.com/s/tqto0s786048qy7/05%20Elbow%20Pronation%20and%20Supination.mp4?dl=0")
        text!!.add(resources.getString(R.string.elbow_pronation_and_supination_str))

        headings!!.add(resources.getString(R.string.wrist_flexion_and_extension))
        urls!!.add("https://www.dropbox.com/s/lb7ebx240x1hss1/06%20Wrist%20Flexion%20and%20Extension.mp4?dl=0")
        text!!.add(resources.getString(R.string.wrist_flexion_and_extension_str))

        headings!!.add(resources.getString(R.string.extend_elbow))
        urls!!.add("https://www.dropbox.com/s/mgy6rc815fdalm4/07%20Extend%20Elbow.mp4?dl=0")
        text!!.add(resources.getString(R.string.extend_elbow_str))

        headings!!.add(resources.getString(R.string.forearm_to_box))
        urls!!.add("https://www.dropbox.com/s/ikn39lrhtah1n27/08%20Forearm%20to%20Box.mp4?dl=0")
        text!!.add(resources.getString(R.string.forearm_to_box_str))

        headings!!.add(resources.getString(R.string.hand_to_box))
        urls!!.add("https://www.dropbox.com/s/fsg967twj1iywaa/09%20Hand%20to%20Box.mp4?dl=0")
        text!!.add(resources.getString(R.string.hand_to_box_str))
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