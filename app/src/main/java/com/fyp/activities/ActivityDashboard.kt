package com.fyp.activities

import android.app.ActivityManager
import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.fyp.R
import com.fyp.utils.Constant
import com.fyp.utils.SessionManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.content_dashboard.*
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*


class ActivityDashboard : AppCompatActivity(), View.OnClickListener {
    private var finalTime = ""
    private val hideHandler = Handler()
    private var sessionManager: SessionManager? = null
    protected var nMyApplication: MyApplication? = null

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
    var counter:CountDownTimer?=null
    var maxCounter: Long = 1000000
    var diff: Long = 1000
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        nMyApplication = application as MyApplication
        nMyApplication!!.onActivityCreated(this, savedInstanceState)
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
        counter= object : CountDownTimer(maxCounter, diff) {
            override fun onTick(millisUntilFinished: Long) {
                val diff: Long = maxCounter - millisUntilFinished
                finalTime=(diff / 1000).toString()
                Log.i("TickTick",finalTime)
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {

            }
        }.start()

    }
    fun convertSeconds(seconds: Int): String? {
        val h = seconds / 3600
        val m = seconds % 3600 / 60
        val s = seconds % 60
        val sh = if (h > 0) "$h h" else ""
        val sm =
            (if (m in 1..9 && h > 0) "0" else "") + if (m > 0) if (h > 0 && s == 0) m.toString() else "$m min" else ""
        val ss =
            if (s == 0 && (h > 0 || m > 0)) "" else (if (s < 10 && (h > 0 || m > 0)) "0" else "") + s.toString() + " " + "sec"
        return sh + (if (h > 0) " " else "") + sm + (if (m > 0) " " else "") + ss
    }
    private fun setData() {
        val rootRef = FirebaseDatabase.getInstance().reference
        val dbRef = rootRef.child("upwork-f2a18-default-rtdb").child("RegisteredUsers")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (d in dataSnapshot.children) {
                        if (d.child("mobile").value == sessionManager!!.getStringVal(Constant.MOBILE)) {
                            val result: HashMap<String, Any> = HashMap()
                            result["CompleteApplicationTime"] = convertSeconds(finalTime.toInt())+""
                            d.key?.let {
                                FirebaseDatabase.getInstance().reference.child("upwork-f2a18-default-rtdb")
                                    .child("RegisteredUsers")
                                    .child(it).updateChildren(result)
                            }
                            break
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            } //onCancelled
        })
    }

    override fun onResume() {
        super.onResume()
        nMyApplication!!.onActivityResumed(this)
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        nMyApplication!!.onActivitySaveInstanceState(this, outState);
    }
    override fun onStart() {
        super.onStart()
        nMyApplication!!.onActivityStarted(this)
    }

    override fun onStop() {
        super.onStop()
        nMyApplication!!.onActivityStopped(this)
    }

    override fun onPause() {
        super.onPause()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // Clear the systemUiVisibility flag
        window?.decorView?.systemUiVisibility = 0
        nMyApplication!!.onActivityPaused(this)

        show()
    }

    override fun onDestroy() {
        super.onDestroy()
        counter!!.cancel()
        dummyButton = null
        fullscreenContent = null
        fullscreenContentControls = null
        nMyApplication!!.onActivityDestroyed(this)
        setData()
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