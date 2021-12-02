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


class FragmentActiveExcercise : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = ArrayList<String>()
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
        addQuestInRv()
    }

    private fun addQuestInRv() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.upperLibRehTv -> {
                findNavController().navigate(R.id.action_fragmentRehabilitation_to_fragmentUpperLibRehabilition)
            }R.id.mobTraTv -> {
                findNavController().navigate(R.id.action_fragmentRehabilitation_to_fragmentMobilityTraning)
            }R.id.gaitAndBalTv -> {
                findNavController().navigate(R.id.action_fragmentRehabilitation_to_fragmentGetAndBalance)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentRehabilitation) {
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