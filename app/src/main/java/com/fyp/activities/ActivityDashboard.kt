package com.fyp.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.interfaces.iOnBackPressed
import kotlinx.android.synthetic.main.content_dashboard.*


class ActivityDashboard : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init()
    }
    fun init(){
        tvAccount.setOnClickListener(this)
        tvHome.setOnClickListener(this)
        tvUrdu.setOnClickListener(this)
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.fragment)
        (fragment as? iOnBackPressed)?.onBackPressed()?.not()?.let {
            super.onBackPressed()
        }
        val navController = findNavController(R.id.fragment)
        if (!navController.popBackStack()) {
            Log.i("onBackPress", "Not Up Finish All Fragment")
            finish()
        } else {
            Log.i("onBackPress", "Up")
            navController.popBackStack()
        }
    }
    private fun switchFragment(startDestId: Int) {
//        val fragmentContainer = view?.findViewById<View>(R.id.nav_host)
//        val navController = Navigation.findNavController(fragmentContainer!!)
        val navController = findNavController(R.id.fragment)
        val inflater = navController.navInflater
        val graph = navController.graph
        graph.startDestination = startDestId
        navController.graph = graph
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tvAccount->{
                switchFragment(R.id.fragmentUpdate)
            }  R.id.tvHome->{
                switchFragment(R.id.dashboard)
            }  R.id.tvUrdu->{
                Toast.makeText(this,"App is in urdu language come soon",Toast.LENGTH_LONG).show()
            }
        }
    }
}