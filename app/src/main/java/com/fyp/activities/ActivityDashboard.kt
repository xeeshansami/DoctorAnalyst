package com.fyp.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.interfaces.iOnBackPressed


class ActivityDashboard : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
    }

    override fun onBackPressed() {
        val fragment = this.supportFragmentManager.findFragmentById(R.id.fragment)
        (fragment as? iOnBackPressed)?.doBack()?.not()?.let {
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
}