package com.fyp.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.annotation.RequiresApi
import com.hbl.hblaccountopeningapp.network.store.HBLHRStore
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.*

class GlobalClass : HBLHRStore(), Application.ActivityLifecycleCallbacks {
    private var progressDialog: TransparentProgressDialog? = null
    private var finalTime = ""
    var tag = "TrackingActivity"
    private var sessionManager: SessionManager? = null
    override fun onCreate() {
        super.onCreate()
        sessionManager = SessionManager(this)
        Companion.applicationContext = applicationContext    }

    companion object {
        private val consumerStore: GlobalClass? = null
        var applicationContext: Context? = null
        val instance: GlobalClass?
            get() = consumerStore ?: GlobalClass()
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
    fun startTime(): String {
        val current_data_time = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())
        return current_data_time.format(Date())
    }

    fun endTime(): String {
        val current_data_time = SimpleDateFormat("dd-MMM-yyyy hh:mm:ss a", Locale.getDefault())
        return current_data_time.format(Date())
    }

    /*private fun setData(isStart: Boolean) {
        val rootRef = FirebaseDatabase.getInstance().reference
        val dbRef = rootRef.child("upwork-f2a18-default-rtdb").child("RegisteredUsers")
        dbRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (d in dataSnapshot.children) {
                        if (d.child("mobile").value == sessionManager!!.getStringVal(Constant.MOBILE)) {
                            val result: HashMap<String, Any> = HashMap()
                            if (isStart) {
                                result["DateTimeOpenApp"] = startTime()
                            } else {
                                result["DateTimeCloseApp"] = endTime()
                            }
                            d.key?.let {
                                FirebaseDatabase.getInstance().reference.child("upwork-f2a18-default-rtdb")
                                    .child("RegisteredUsers")
                                    .child(it).child("Records").push().setValue(result)
                            }
                            break
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            } //onCancelled
        })
    }*/

    private fun countDownStart() {
        object : CountDownTimer(30000, 1000) {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTick(millisUntilFinished: Long) {
                finalTime = LocalTime.ofSecondOfDay(millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
            }
        }.start()
    }

    override fun onActivityStopped(activity: Activity) {
        Log.i(tag, activity.localClassName)
    }

    override fun onActivityStarted(activity: Activity) {
        Log.i(tag, activity.localClassName)
    }

    @SuppressLint("LongLogTag")
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        Log.i(tag, activity.localClassName)
    }

    override fun onActivityResumed(activity: Activity) {
        Log.i(tag, activity.localClassName)
    }

    override fun onActivityPaused(activity: Activity) {
        Log.i(tag, activity.localClassName)
    }

    override fun onActivityDestroyed(activity: Activity) {
        Log.i(tag, activity.localClassName)
//        setData(false)
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        Log.i(tag, activity.localClassName)
        countDownStart()
//        setData(true)
    }
}