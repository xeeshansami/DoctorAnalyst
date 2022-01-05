package com.fyp.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.activities.LanguageActivity
import com.fyp.activities.LogActivity
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager


/**
 * An example full-screen fragment that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FragmentSplashScreen : Fragment() {
    private val hideHandler = Handler()
    private var sessionManager: SessionManager? = null
//    private var firebaseAuth: FirebaseAuth? = null

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
        activity?.window?.decorView?.systemUiVisibility = flags
        (activity as? AppCompatActivity)?.supportActionBar?.hide()
    }


    private var dummyButton: Button? = null
    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullscreenContent = view.findViewById(R.id.fullscreen_content)
        init()
    }

    fun init() {
//        firebaseAuth = FirebaseAuth.getInstance()
        sessionManager = SessionManager(activity as LogActivity)
    }

    override fun onStart() {
        super.onStart()
        if (!sessionManager!!.getStringVal(Constant.MOBILE).isNullOrEmpty()) {
            Handler().postDelayed({
                var intent = Intent(activity as LogActivity, LanguageActivity::class.java)
                startActivity(intent)
                (activity as LogActivity).finish()
            }, 3000)

        } else {
            sendToScreen()
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        if(firebaseAuth?.currentUser?.uid!=null){
//            sendToScreen(R.id.action_splashScreen_to_dashboard)
//        }else{
//        sendToScreen(R.id.action_splashScreen_to_signin)
//        }
    }

    override fun onPause() {
        super.onPause()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // Clear the systemUiVisibility flag
        activity?.window?.decorView?.systemUiVisibility = 0
        show()
    }

    fun sendToScreen() {
        Handler().postDelayed({

            findNavController().navigateUp();
            findNavController().navigate(
                R.id.action_splashScreen_to_signin)
        }, 3000)

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
        (activity as? AppCompatActivity)?.supportActionBar?.show()
    }
}