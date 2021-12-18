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
import kotlinx.android.synthetic.main.fragment_excercise_screen.*


class FragmentExcerciseScreen : Fragment(), View.OnClickListener ,iOnBackPressed,
    iOnVideoItemClickListner {
    var list = ArrayList<videoObjects>()
    var myView: View? = null
    var videoPlay=true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_excercise_screen, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        list = ArrayList<videoObjects>()
        but2.setOnClickListener(this)
        but3.setOnClickListener(this)
        but4.setOnClickListener(this)
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
        when(v!!.id){
            R.id.but2->{
                findNavController().navigate(R.id.action_active_excercise)
            } R.id.but3->{
                findNavController().navigate(R.id.action_passive_excercise)
            }R.id.but4->{
            findNavController().navigate(R.id.action_functional_excercise)
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