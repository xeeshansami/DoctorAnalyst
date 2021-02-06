package com.fyp.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebSettings.PluginState
import android.widget.MediaController
import android.widget.VideoView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.adapters.ExerciseAdapters
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnVideoItemClickListner
import com.fyp.models.mExercise
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_questions.*
import kotlinx.android.synthetic.main.fragment_upper_lib_rehabilation.*


class FragmentGetAndBalance : Fragment(), View.OnClickListener ,iOnBackPressed,
    iOnVideoItemClickListner {
    var list = ArrayList<mExercise>()
    var myView: View? = null
    var videoPlay=true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_get_and_balance, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        addQuestInRv()
    }

    private fun addQuestInRv(){
        val questions = (activity as ActivityDashboard).resources!!.getStringArray(R.array.exercise_array)
        list.clear()
        for (element in questions) {
            var execise= mExercise()
            execise.apply {
                videoName=element
                videoUrl="https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4?_=1"
            }
            list.add(execise)
        }
        rvExercise.apply {
            layoutManager = LinearLayoutManager(
                (activity as ActivityDashboard),
                LinearLayoutManager.VERTICAL,
                true
            )
            adapter = ExerciseAdapters(
                activity as ActivityDashboard,
                list!!,
                this@FragmentGetAndBalance
            )
            adapter?.notifyDataSetChanged()
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