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
import com.fyp.activities.ActivityDashboard
import com.fyp.interfaces.iOnBackPressed
import com.fyp.models.mQuestions
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_passive_excercise.*


class FragmentPassiveExcercise : Fragment(), View.OnClickListener, iOnBackPressed {
    var list = ArrayList<String>()
    var myView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myView = inflater.inflate(R.layout.fragment_passive_excercise, container, false)
        return myView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        addQuestInRv()
        button1.setOnClickListener(this)
        button2.setOnClickListener(this)
        button3.setOnClickListener(this)
    }

    private fun addQuestInRv() {
        val questions =
            (activity as ActivityDashboard).resources!!.getStringArray(R.array.questions_desc_array)
        for (element in questions) {
            list?.add(element)
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
            R.id.button3 -> {
                findNavController().navigate(R.id.action_fragmentVideo)
            }
        }
    }

    override fun onBackPressed(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        return if (navController.currentDestination?.id != R.id.fragmentPassiveExcercise) {
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