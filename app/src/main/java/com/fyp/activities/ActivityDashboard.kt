package com.fyp.activities

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.*
import android.provider.Settings
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.fyp.R
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.*
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import kotlinx.android.synthetic.main.content_dashboard.*
import kotlinx.android.synthetic.main.fragment_signin.*
import kotlinx.android.synthetic.main.fragment_signup.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*


class ActivityDashboard : AppCompatActivity(), View.OnClickListener {
     var finalTime = ""
    private val hideHandler = Handler()
    private var sessionManager: SessionManager? = null
    var globalClass: GlobalClass? = null
    private var progressDialog: TransparentProgressDialog? = null

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
    var counter: CountDownTimer? = null
    var maxCounter: Long = 1000000
    var diff: Long = 1000

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        globalClass = GlobalClass.applicationContext!!.applicationContext as GlobalClass
        sessionManager = SessionManager(this)
        init()
        appTime()
        fullscreenContent = findViewById(R.id.fullscreen_content)

        if (sessionManager!!.getIntVal(Constant.LANGUAGE) == 1 || sessionManager!!.getIntVal(
                Constant.LANGUAGE
            ) == 0
        ) {
            AppLang.AppLang(this, "en")
        } else {
            AppLang.AppLang(this, "ur")
        }
        counter()
    }

    fun counter() {
        if (counter != null) {
            counter!!.cancel()
            counter!!.onFinish()
            counter = null
        }
        counter = object : CountDownTimer(maxCounter, diff) {
                override fun onTick(millisUntilFinished: Long) {
                    val diff: Long = maxCounter - millisUntilFinished
                    finalTime = (diff / 1000).toString()
                    Log.i("TickTick", finalTime)
                    //here you can have your logic to set text to edittext
                }

                override fun onFinish() {
                    counter!!.cancel()
                }
            }.start()
    }
    fun updateAppTime() {
        globalClass?.showDialog(this)
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("phone", sessionManager!!.getStringVal(Constant.MOBILE)!!)
            .addFormDataPart(
                "completeApplicationTime",
                finalTime
            )
            .build()
        HBLHRStore.instance?.updateAppTime(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
//                    ToastUtils.showToastWith(this@ActivityDashboard, response.message)
                    Log.i("Counter", "2")
                    counter()
                    globalClass?.hideLoader()
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith(this@ActivityDashboard, response.message)
                    globalClass?.hideLoader()
                }
            })
    }

    fun appTime() {
        globalClass?.showDialog(this)
        var lang = ""
        if (sessionManager!!.getIntVal(Constant.LANGUAGE) == 1) {
            lang = "english"
        } else {
            lang = "urdu"
        }
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("phone", sessionManager!!.getStringVal(Constant.MOBILE)!!)
            .addFormDataPart("language", lang)
            .build()
        HBLHRStore.instance?.appTime(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
//                    ToastUtils.showToastWith(this@ActivityDashboard, response.message, "")
                    updateHistoryAppTime()
                    globalClass?.hideLoader()
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith(this@ActivityDashboard, response.message, "")
                    globalClass?.hideLoader()
                }
            })
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

    override fun onResume() {
        super.onResume()
        window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        Log.i("Counter", "1")
        updateAppTime()
        super.onStop()
    }

    override fun onPause() {
        Log.i("Counter", "0")
//        updateAppTime()
        super.onPause()
        window?.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        // Clear the systemUiVisibility flag
        window?.decorView?.systemUiVisibility = 0
        show()
    }

    override fun onDestroy() {
        Log.i("Counter", "2")
        counter!!.cancel()
        counter!!.onFinish()
        dummyButton = null
        fullscreenContent = null
        fullscreenContentControls = null
//        updateAppTime()
        super.onDestroy()
    }

    fun updateHistoryAppTime() {
        var request = sessionManager!!.getHistory()
        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("pageName", request!!.pageName)
            .addFormDataPart("phone", request!!.phone)
            .addFormDataPart("exerciseName", request!!.exerciseName)
            .addFormDataPart("videoScreenTime", request!!.videoScreenTime)
            .addFormDataPart("videoUrl", request!!.videoUrl)
            .build()
        HBLHRStore.instance?.history(
            RetrofitEnums.URL_HBL,
            requestBody, object : RegisterCallBack {
                @SuppressLint("WrongConstant")
                override fun Success(response: BaseResponse) {
//                    ToastUtils.showToastWith(activity as ActivityDashboard, "His", "")
                    Log.i("Counter", "1")
                    globalClass?.hideLoader()
                }

                override fun Failure(response: BaseResponse) {
                    ToastUtils.showToastWith(this@ActivityDashboard, response.message, "")
                    globalClass?.hideLoader();
                }
            })
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

    fun getProgressDialogInstance(context: Context?): TransparentProgressDialog? {
        if (progressDialog == null) progressDialog = TransparentProgressDialog(
            context!!
        )
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