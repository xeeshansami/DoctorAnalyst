package com.fyp.activities

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.interfaces.iOnBackPressed
import kotlinx.android.synthetic.main.content_dashboard.*


class ActivityDashboard : AppCompatActivity(), View.OnClickListener {
    private val hideHandler = Handler()

    @Suppress("InlinedApi")
    private val hidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
        val flags =
            View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window?.decorView?.systemUiVisibility = flags
        supportActionBar?.hide()
    }

    private var dummyButton: Button? = null
    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init()
        fullscreenContent = findViewById(R.id.fullscreen_content)
    }


    override fun onResume() {
        super.onResume()
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onPause() {
        super.onPause()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // Clear the systemUiVisibility flag
        window?.decorView?.systemUiVisibility = 0
        show()
    }

    override fun onDestroy() {
        super.onDestroy()
        dummyButton = null
        fullscreenContent = null
        fullscreenContentControls = null
    }


    @Suppress("InlinedApi")
    private fun show() {
        // Show the system bar
        fullscreenContent?.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        // Schedule a runnable to display UI elements after a delay
        hideHandler.removeCallbacks(hidePart2Runnable)
        supportActionBar?.show()
    }

    fun init() {
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
        when (v!!.id) {
            R.id.tvAccount -> {
                switchFragment(R.id.fragmentMyAccount)
            }
            R.id.tvHome -> {
                switchFragment(R.id.dashboard)
            }
            R.id.tvUrdu -> {
                Toast.makeText(this, "App is in urdu language come soon", Toast.LENGTH_LONG).show()
            }
        }
    }
}