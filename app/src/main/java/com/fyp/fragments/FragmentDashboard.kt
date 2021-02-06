package com.fyp.fragments

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.adapters.QuestionAdapters
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnItemClickListner
import com.fyp.mQuestions
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_dashboard.*


class FragmentDashboard : Fragment(), iOnItemClickListner,iOnBackPressed {
    var list=ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        addQuestInRv()
        onBackPressed(view)
    }

    private fun addQuestInRv(){
        val questions = (activity as ActivityDashboard).resources!!.getStringArray(R.array.questions_array)
        list.clear()
        for (element in questions) {
            list?.add(element)
        }
        rvQuestions.apply {
            layoutManager = LinearLayoutManager((activity as ActivityDashboard))
            adapter = QuestionAdapters(
                activity as ActivityDashboard,
                list!!,
                this@FragmentDashboard
            )
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onItemClick(view: View, question: String, position: Int) {
        val bundle = Bundle()
        var quest=mQuestions()
        quest.position=position
        quest.questions=question.toString()
        bundle.putParcelable(Constant.QUESTIONS, quest)
        findNavController().navigate(R.id.action_dashboard_to_fragmentQuestions, bundle)
    }
    private fun onBackPressed(view: View) {
        view.isFocusableInTouchMode = true
        view.requestFocus()
        view.setOnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && keyCode == KeyEvent.ACTION_UP) {
                val navController = requireActivity().findNavController(R.id.fragment)
                if (navController.currentDestination?.id == R.id.dashboard) {
                    Log.i("onBackPress", "Not Up Finish All Fragment")
                    requireActivity().finish()
                } else {
                    Log.i("onBackPress", "Up")
                    navController.popBackStack()
                }
                true
            } else {
                false
            }
        }
    }

    override fun doBack(): Boolean {
        val navController = requireActivity().findNavController(R.id.fragment)
        if (navController.currentDestination?.id != R.id.dashboard) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            requireActivity().finish()
            return true
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
            return true
        }
    }
}