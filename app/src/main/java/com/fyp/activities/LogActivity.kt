package com.fyp.activities

import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.utils.Constant
import com.fyp.utils.GlobalClass
import com.fyp.utils.SessionManager
import com.fyp.utils.TransparentProgressDialog

class LogActivity : AppCompatActivity() {
    private val hideHandler = Handler()
    private var progressDialog: TransparentProgressDialog? = null
    @JvmField
    public var globalClass: GlobalClass? = null
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
    private var sessionManager: SessionManager? = null
    private var dummyButton: Button? = null
    private var fullscreenContent: View? = null
    private var fullscreenContentControls: View? = null

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_logs)
        fullscreenContent = findViewById(R.id.fullscreen_content)
        globalClass = GlobalClass.applicationContext!!.applicationContext as GlobalClass
        sessionManager = SessionManager(this)
//        if (sessionManager!!.getIntVal(Constant.LANGUAGE) == 1||sessionManager!!.getIntVal(Constant.LANGUAGE) == 0) {
//            AppLang.AppLang(this, "en")
//        } else {
//            AppLang.AppLang(this, "ur")
//        }
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

    override fun onBackPressed() {
        val navController = findNavController(R.id.fragment1)
        if (navController.navigateUp()) {
            navController.navigateUp()
        } else {
            finish()
        }
    }
    fun getProgressDialogInstance(context: Context?): TransparentProgressDialog? {
        if (progressDialog == null) progressDialog = TransparentProgressDialog(
            context!!)
        return progressDialog
    }
    fun showDialog(context: Context?) {
        progressDialog = getProgressDialogInstance(context)
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    fun hideLoader() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.cancel()
            progressDialog = null
        }
    }
}