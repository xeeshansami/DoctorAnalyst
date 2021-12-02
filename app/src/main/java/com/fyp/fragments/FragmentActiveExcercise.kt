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
import kotlinx.android.synthetic.main.fragment_active_excercise.*


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
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
        button4.setOnClickListener(this)
        button5.setOnClickListener(this)
        button6.setOnClickListener(this)
        button7.setOnClickListener(this)
        button8.setOnClickListener(this)
        button9.setOnClickListener(this)
        addQuestInRv()
    }

    private fun addQuestInRv() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button1 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }R.id.button2 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }R.id.button3 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            } R.id.button4 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }R.id.button5 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }R.id.button6 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            } R.id.button7 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }R.id.button8 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }R.id.button9 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
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