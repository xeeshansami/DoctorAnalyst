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
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnVideoItemClickListner
import com.fyp.models.mExercise
import kotlinx.android.synthetic.main.fragment_functional_screen.*


class FragmentFunctionalScreen : Fragment(), View.OnClickListener, iOnBackPressed,
    iOnVideoItemClickListner {
    var list = ArrayList<mExercise>()
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
    }

    override fun onItemClick(view: VideoView, question: String, position: Int) {
        if (videoPlay) {
            videoPlay = false
            view.start()
        } else {
            videoPlay = true
            view.pause()
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button1 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }
            R.id.button2 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
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