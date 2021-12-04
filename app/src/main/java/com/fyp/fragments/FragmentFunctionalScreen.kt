package com.fyp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnVideoItemClickListner
import com.fyp.models.videoObjects
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_functional_screen.*


class FragmentFunctionalScreen : Fragment(), View.OnClickListener, iOnBackPressed,
    iOnVideoItemClickListner {
    var list = ArrayList<videoObjects>()
    var headings = ArrayList<String>()
    var urls = ArrayList<String>()
    var text = ArrayList<String>()
    var myView: View? = null
    var videoPlay = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_functional_screen, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
//        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        button10.setOnClickListener(this)
        button11.setOnClickListener(this)
        button12.setOnClickListener(this)
        button13.setOnClickListener(this)
        button14.setOnClickListener(this)
        inputData()
    }

    private fun inputData() {
        headings!!.add(resources.getString(R.string.lift_can))
        urls!!.add("https://www.dropbox.com/s/shd54so4f96pg66/01%20Lift%20Can.mp4?dl=0")
        text!!.add(resources.getString(R.string.lift_can_str))

        headings!!.add(resources.getString(R.string.lift_pencil))
        urls!!.add("https://www.dropbox.com/s/bn0v62sin9mwlbx/02%20Lift%20Pencil.mp4?dl=0")
        text!!.add(resources.getString(R.string.lift_pencil_str))

    /*    headings!!.add("Lift Paper Clip")
        urls!!.add("https://www.dropbox.com/s/dzhfrxy7w9qfj94/04%20ROMs%20for%20Wrist%2C%20Fingers%20and%20Thumb.mp4?dl=0")
        text!!.add("•\tPatient should be in supine lying position.\n" +
                "•\tCaregiver should be standing at the side of patient.\n" +
                "•\tHold the patient’s affected arm from the wrist then bend the wrist first then straighten it as demonstrated in the video.\n" +
                "•\tRepeat this exercise 10 times.\n" +
                "•\tHold the affected arm from wrist then move the thumb and fingers in all possible directions and return to original position.\n" +
                "•\tRepeat this exercise 10 times.\n")*/

        headings!!.add(resources.getString(R.string.stack_checkers))
        urls!!.add("https://www.dropbox.com/s/hhorvzgjny86dvw/04%20Stack%20Checkers.mp4?dl=0")
        text!!.add(resources.getString(R.string.stack_checkers_str))

        headings!!.add(resources.getString(R.string.flip_cards))
        urls!!.add("https://www.dropbox.com/s/huyp8syxxdik0bs/05%20Flip%20Cards.mp4?dl=0")
        text!!.add(resources.getString(R.string.flip_cards_str))

        headings!!.add(resources.getString(R.string.fold_towel))
        urls!!.add("https://www.dropbox.com/s/rpl876la0tjq9xp/06%20Fold%20Towel.mp4?dl=0")
        text!!.add(resources.getString(R.string.fold_towel_str))

        headings!!.add(resources.getString(R.string.drinking_water_from_glass))
        urls!!.add("https://www.dropbox.com/s/71sl024i34p7awc/07%20Drinking%20Water%20from%20Glass.mp4?dl=0")
        text!!.add(resources.getString(R.string.drinking_water_from_glass_str))

        headings!!.add(resources.getString(R.string.lifting_glass_of_water_at_90_shoulder_flexion))
        urls!!.add("https://www.dropbox.com/s/aydj5nyy368687g/08%20Lifting%20Glass%20of%20Water%20at%2090%C2%B0%20Shoulder%20Flexion.mp4?dl=0")
        text!!.add(resources.getString(R.string.lifting_glass_of_water_at_90_shoulder_flexion_str))

        headings!!.add(resources.getString(R.string.moving_5_crystals_from_table_to_box))
        urls!!.add("https://www.dropbox.com/s/krnwicrj0clg3ht/09%20Moving%205%20Crystals%20from%20Table%20to%20Box.mp4?dl=0")
        text!!.add(resources.getString(R.string.moving_5_crystals_from_table_to_box_str))

        headings!!.add(resources.getString(R.string.wiping_the_table))
        urls!!.add("https://www.dropbox.com/s/7infzykr6lmcvi5/10%20Wiping%20the%20Table.mp4?dl=0")
        text!!.add(resources.getString(R.string.wiping_the_table_str))

        headings!!.add(resources.getString(R.string.grasping_and_releasing_ball))
        urls!!.add("https://www.dropbox.com/s/ju2tufucexuei2p/11%20Grasping%20and%20Releasing%20Ball.mp4?dl=0")
        text!!.add(resources.getString(R.string.grasping_and_releasing_ball_str))

        headings!!.add(resources.getString(R.string.combing_hair))
        urls!!.add("https://www.dropbox.com/s/ujpovcpp56226p9/12%20Combing%20Hair.mp4?dl=0")
        text!!.add(resources.getString(R.string.combing_hair_str))

        headings!!.add(resources.getString(R.string.eating_with_affected_hand))
        urls!!.add("https://www.dropbox.com/s/kb9ut8iszi317u8/13%20Eating%20with%20Affected%20Hand.mp4?dl=0")
        text!!.add(resources.getString(R.string.eating_with_affected_hand_str))

        headings!!.add(resources.getString(R.string.opening_and_closing_jar))
        urls!!.add("https://www.dropbox.com/s/li0gacgcftg2nxc/14%20Opening%20and%20Closing%20Jar.mp4?dl=0")
        text!!.add(resources.getString(R.string.opening_and_closing_jar_str))

        for (x in 0 until  headings.size) {
            var obj = videoObjects()
            obj.heading=headings[x]
            obj.videoUrl=urls[x]
            obj.text=text[x]
            list!!.add(obj)
        }
    }

    override fun onItemClick(view: VideoView, question: String, position: Int) {

    }

    override fun onClick(v: View?) {
        var bundle = Bundle()
        when (v!!.id) {
            R.id.button1 -> {
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
//            R.id.button3 -> {
//                bundle.putParcelableArrayList(
//                    Constant.WEBVIEW_LINK,
//                    list
//                )
//
//                bundle.putInt(
//                    Constant.POSITION,
//                    2
//                )
//                findNavController().navigate(R.id.action_fragmentVideo, bundle)
//            }
            R.id.button4 -> {
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
            R.id.button10 -> {
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )

                bundle.putInt(
                    Constant.POSITION,
                    9
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button11 -> {
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )

                bundle.putInt(
                    Constant.POSITION,
                    10
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button12 -> {
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )

                bundle.putInt(
                    Constant.POSITION,
                    11
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button13 -> {
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )

                bundle.putInt(
                    Constant.POSITION,
                    12
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button4 -> {
                bundle.putParcelableArrayList(
                    Constant.WEBVIEW_LINK,
                    list
                )

                bundle.putInt(
                    Constant.POSITION,
                    14
                )
                findNavController().navigate(R.id.action_fragmentVideo, bundle)
            }
            R.id.button2 -> {
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
            R.id.button2 -> {
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