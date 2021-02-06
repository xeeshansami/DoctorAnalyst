package com.fyp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.adapters.QuestionAdapters
import kotlinx.android.synthetic.main.fragment_dashboard.*


class FragmentDashboard : Fragment() {
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
        init()
    }

    private fun init(){
        addQuestInRv()
    }

    private fun addQuestInRv(){
        val questions = (activity as ActivityDashboard).resources!!.getStringArray(R.array.questions_array)
        for (element in questions) {
            list?.add(element)
        }
        rvQuestions.apply {
            layoutManager = LinearLayoutManager((activity as ActivityDashboard))
            adapter = QuestionAdapters(activity as ActivityDashboard, list!!)
            adapter?.notifyDataSetChanged()
        }
    }
}