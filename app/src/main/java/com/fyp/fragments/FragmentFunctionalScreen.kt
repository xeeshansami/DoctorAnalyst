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
        urls!!.add("https://strokex.xoqax.com/videos/functional-lift-can.mp4")
        text!!.add(resources.getString(R.string.lift_can_str))

        headings!!.add(resources.getString(R.string.lift_pencil))
        urls!!.add("https://strokex.xoqax.com/videos/functional-lift-pencil.mp4")
        text!!.add(resources.getString(R.string.lift_pencil_str))

        headings!!.add("Lift Paper Clip")
        urls!!.add("https://strokex.xoqax.com/videos/functional-lift-paper-clip.mp4")
        text!!.add(resources.getString(R.string.stack_checkers_str))

        headings!!.add(resources.getString(R.string.stack_checkers))
        urls!!.add("https://strokex.xoqax.com/videos/functional-stack-checkers.mp4")
        text!!.add(resources.getString(R.string.stack_checkers_str))

        headings!!.add(resources.getString(R.string.flip_cards))
        urls!!.add("https://strokex.xoqax.com/videos/functional-flip-cards.mp4")
        text!!.add(resources.getString(R.string.flip_cards_str))

        headings!!.add(resources.getString(R.string.fold_towel))
        urls!!.add("https://strokex.xoqax.com/videos/functional-fold-towel.mp4")
        text!!.add(resources.getString(R.string.fold_towel_str))

        headings!!.add(resources.getString(R.string.drinking_water_from_glass))
        urls!!.add("https://strokex.xoqax.com/videos/functional-drinking-water-from-glass.mp4")
        text!!.add(resources.getString(R.string.drinking_water_from_glass_str))

        headings!!.add(resources.getString(R.string.lifting_glass_of_water_at_90_shoulder_flexion))
        urls!!.add("https://strokex.xoqax.com/videos/functional-lifting-glass-of-water-at-90-shoulder-flexion.mp4")
        text!!.add(resources.getString(R.string.lifting_glass_of_water_at_90_shoulder_flexion_str))

        headings!!.add(resources.getString(R.string.moving_5_crystals_from_table_to_box))
        urls!!.add("https://strokex.xoqax.com/videos/functional-moving-5-crystals-from-table-to-box.mp4")
        text!!.add(resources.getString(R.string.moving_5_crystals_from_table_to_box_str))

        headings!!.add(resources.getString(R.string.wiping_the_table))
        urls!!.add("https://strokex.xoqax.com/videos/functional-wiping-the-table.mp4")
        text!!.add(resources.getString(R.string.wiping_the_table_str))

        headings!!.add(resources.getString(R.string.grasping_and_releasing_ball))
        urls!!.add("https://strokex.xoqax.com/videos/functional-grasping-and-releasing-ball.mp4")
        text!!.add(resources.getString(R.string.grasping_and_releasing_ball_str))

        headings!!.add(resources.getString(R.string.combing_hair))
        urls!!.add("https://strokex.xoqax.com/videos/functional-combing-hair.mp4")
        text!!.add(resources.getString(R.string.combing_hair_str))

        headings!!.add(resources.getString(R.string.eating_with_affected_hand))
        urls!!.add("https://strokex.xoqax.com/videos/functional-eating-with-affected-hand.mp4")
        text!!.add(resources.getString(R.string.eating_with_affected_hand_str))

        headings!!.add(resources.getString(R.string.opening_and_closing_jar))
        urls!!.add("https://strokex.xoqax.com/videos/functional-opening-and-closing-jar.mp4")
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