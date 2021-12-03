package com.fyp.fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.ActivityDashboard
import com.fyp.activities.AppLang
import com.fyp.interfaces.iOnBackPressed
import com.fyp.interfaces.iOnItemClickListner
import com.fyp.models.mQuestions
import com.fyp.utils.Constant
import kotlinx.android.synthetic.main.fragment_dashboard.*
import java.util.*
import kotlin.collections.ArrayList


class FragmenLanguageScreen : Fragment(), iOnItemClickListner,iOnBackPressed, View.OnClickListener {
    var list=ArrayList<String>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_language_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
    }

    private fun init(view: View) {
        but2.setOnClickListener(this)
        but3.setOnClickListener(this)
        addQuestInRv()
        onBackPressed(view)
    }

    private fun addQuestInRv(){
        val questions = (activity as ActivityDashboard).resources!!.getStringArray(R.array.questions_array)
        list.clear()
        for (element in questions) {
            list?.add(element)
        }
    }
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    fun setLocale(activity: Activity, languageCode: String?) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val resources = activity.resources
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun onItemClick(view: View, question: String, position: Int) {
        val bundle = Bundle()
        var quest= mQuestions()
        quest.position=position
        quest.questions=question
        bundle.putParcelable(Constant.QUESTIONS, quest)
//        findNavController().navigate(R.id.action_dashboard_to_fragmentQuestions, bundle)
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

    override fun onBackPressed(): Boolean {
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

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.but2->{
                AppLang.AppLang(requireActivity(),"en")
//                findNavController().navigate(R.id.action_fragment_language_screen_to_dashboard)
            }R.id.but3->{
            AppLang.AppLang(requireActivity(),"ur")
//                findNavController().navigate(R.id.action_fragment_language_screen_to_dashboard)
            }
        }
    }
}