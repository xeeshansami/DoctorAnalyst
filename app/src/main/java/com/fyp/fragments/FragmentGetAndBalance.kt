package com.fyp.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.adapters.ExerciseAdapters
import com.fyp.adapters.QuestionAdapters
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnItemClickListner
import com.fyp.mQuestions
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_questions.*
import kotlinx.android.synthetic.main.fragment_upper_lib_rehabilation.*


class FragmentGetAndBalance : Fragment(), View.OnClickListener ,iOnBackPressed,
    iOnItemClickListner {
    var list = ArrayList<String>()
    var myView: View? = null
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
            list?.add(element)
        }
        rvExercise.apply {
            layoutManager = LinearLayoutManager((activity as ActivityDashboard))
            adapter = ExerciseAdapters(
                activity as ActivityDashboard,
                list!!,
                this@FragmentGetAndBalance
            )
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onItemClick(view: View, question: String, position: Int) {

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