package com.fyp.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.BoringLayout
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import kotlinx.android.synthetic.main.content_dashboard.*


class ActivityDashboard : AppCompatActivity(), View.OnClickListener {
    private val hideHandler = Handler()
    private var sessionManager: SessionManager? = null

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

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        init()
        fullscreenContent = findViewById(R.id.fullscreen_content)
        sessionManager = SessionManager(this)
        if (sessionManager!!.getIntVal(Constant.LANGUAGE) == 1 || sessionManager!!.getIntVal(
                Constant.LANGUAGE
            ) == 0
        ) {
            AppLang.AppLang(this, "en")
        } else {
            AppLang.AppLang(this, "ur")
        }
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
                switchFragment(R.id.startdashboard)
            }
            R.id.tvUrdu -> {
                finish()
                var intent = Intent(this, LanguageActivity::class.java)
                startActivity(intent)
                sessionManager!!.setIntVal(Constant.LANGUAGE, 0)
            }
        }
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.fragment)
//        val id: Int = navController.currentDestination!!.id
//        val fragment: Fragment? = supportFragmentManager.findFragmentById(id)
//        val fragment2=fragment!!.childFragmentManager.popBackStack()
        if (navController.navigateUp()) {
//            navController.navigateUp()
        } else {
            finish()
//        }
        }
    }
}