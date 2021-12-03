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
        headings!!.add("Lift Can")
        urls!!.add("https://www.dropbox.com/s/shd54so4f96pg66/01%20Lift%20Can.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a can on the table.\n" +
                "•\tLift can with your affected hand.\n" +
                "•\tThen place it back on the table.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Lift Pencil")
        urls!!.add("https://www.dropbox.com/s/bn0v62sin9mwlbx/02%20Lift%20Pencil.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a pencil on the table.\n" +
                "•\tLift pencil with your affected hand.\n" +
                "•\tThen place it back on the table.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

    /*    headings!!.add("Lift Paper Clip")
        urls!!.add("https://www.dropbox.com/s/dzhfrxy7w9qfj94/04%20ROMs%20for%20Wrist%2C%20Fingers%20and%20Thumb.mp4?dl=0")
        text!!.add("•\tPatient should be in supine lying position.\n" +
                "•\tCaregiver should be standing at the side of patient.\n" +
                "•\tHold the patient’s affected arm from the wrist then bend the wrist first then straighten it as demonstrated in the video.\n" +
                "•\tRepeat this exercise 10 times.\n" +
                "•\tHold the affected arm from wrist then move the thumb and fingers in all possible directions and return to original position.\n" +
                "•\tRepeat this exercise 10 times.\n")*/

        headings!!.add("Stack Checkers")
        urls!!.add("https://www.dropbox.com/s/hhorvzgjny86dvw/04%20Stack%20Checkers.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place checkers on the table.\n" +
                "•\tPlace yellow checker on green then purple checker on yellow as demonstrated in the video with the affected hand.\n" +
                "•\tThen unstack the checkers.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n" +
                "•\tAlternatives: Blocks, Caroms\n")

        headings!!.add("Flip Cards")
        urls!!.add("https://www.dropbox.com/s/huyp8syxxdik0bs/05%20Flip%20Cards.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place three cards on the table.\n" +
                "•\tFlip cards one by one with your affected hand.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Fold Towel")
        urls!!.add("https://www.dropbox.com/s/rpl876la0tjq9xp/06%20Fold%20Towel.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a towel on the table.\n" +
                "•\tFold it as demonstrated in the video with your affected hand.\n" +
                "•\tThen unfold it.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Drinking Water from Glass")
        urls!!.add("https://www.dropbox.com/s/71sl024i34p7awc/07%20Drinking%20Water%20from%20Glass.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a glass of water on the table.\n" +
                "•\tHold it with your affected hand.\n" +
                "•\tThen bring it close to your mouth and drink.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n")

        headings!!.add("Lifting Glass of Water at 90° Shoulder Flexion")
        urls!!.add("https://www.dropbox.com/s/aydj5nyy368687g/08%20Lifting%20Glass%20of%20Water%20at%2090%C2%B0%20Shoulder%20Flexion.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a glass on the table.\n" +
                "•\tLift a glass of water at the angle of 90° shoulder flexion with elbow extended.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Moving 5 Crystals from Table to Box")
        urls!!.add("https://www.dropbox.com/s/krnwicrj0clg3ht/09%20Moving%205%20Crystals%20from%20Table%20to%20Box.mp4?dl=0")
        text!!.add("•\tSit in front of the table, place 5 crystals (or any alternate items) and an empty box/jar on the table.\n" +
                "•\tPick the crystals one by one and put them inside the box/jar with your affected hand.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n")

        headings!!.add("Wiping the Table")
        urls!!.add("https://www.dropbox.com/s/7infzykr6lmcvi5/10%20Wiping%20the%20Table.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a towel on the table.\n" +
                "•\tExtend your elbow to reach the towel then wipe the table with with your affected hand.\n" +
                "•\tKeep elbow extended while wiping.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Grasping and Releasing Ball")
        urls!!.add("https://www.dropbox.com/s/ju2tufucexuei2p/11%20Grasping%20and%20Releasing%20Ball.mp4?dl=0")
        text!!.add("•\tGrasp a 6cm diameter ball with your affected hand.\n" +
                "•\tThen squeeze the ball in your hand and release it.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Combing Hair")
        urls!!.add("https://www.dropbox.com/s/ujpovcpp56226p9/12%20Combing%20Hair.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a comb on the table.\n" +
                "•\tLift a comb from the table with your affected hand.\n" +
                "•\tThen comb your hair.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n" +
                "•\tRepeat this activity 10 times.\n")

        headings!!.add("Eating with Affected Hand")
        urls!!.add("https://www.dropbox.com/s/kb9ut8iszi317u8/13%20Eating%20with%20Affected%20Hand.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place food on the table.\n" +
                "•\tHold the spoon with your affected hand.\n" +
                "•\tPut the food in the spoon.\n" +
                "•\tThen bring the spoon close to your mouth and eat.\n" +
                "•\tKeep your body straight and shoulders back and avoid compensatory movements of body.\n")

        headings!!.add("Opening and Closing Jar")
        urls!!.add("https://www.dropbox.com/s/li0gacgcftg2nxc/14%20Opening%20and%20Closing%20Jar.mp4?dl=0")
        text!!.add("•\tSit in front of the table and place a jar on the table.\n" +
                "•\tHold the jar with your unaffected hand.\n" +
                "•\tOpen the lid of the jar with your affected hand.\n" +
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