package com.hbl.hblaccountopeningapp.network.ResponseHandlers.handler

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import com.fyp.network.models.response.base.BaseResponse
import com.fyp.utils.GlobalClass
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.SocketTimeoutException


abstract class BaseRH<T> : Callback<T> {
    var status = ""
    var message = ""
    var ETBNTBFLAG = ""
    val globalClass = GlobalClass.applicationContext!!.applicationContext as GlobalClass

    @SuppressLint("WrongConstant")
    override fun onResponse(call: Call<T>, response: Response<T>) {
        try {
            if (response.isSuccessful) {//200
                val body = response.body()
                onSuccess(body)
            }else{
                Toast.makeText(globalClass,"Something went wrong, coordinate with your administrator, and try again",2000).show()
                globalClass?.hideLoader()
            }
        } catch (ex: Exception) {
        }

    }



    override fun onFailure(call: Call<T>, t: Throwable) {
        t.message?.let { Log.i("JsonError", it) }
        if (t is SocketTimeoutException) { /*When server no response in 30 seconds*/
            onFailure(
                BaseResponse(t.message.toString()
                )
            )
        } else if (t.message!!.contains(" Unable to resolve")) {
            onFailure(
                BaseResponse(t.message.toString()
                )
            )
        } else { /*When something unexpected error occurred.*/
            onFailure(
                BaseResponse(t.message.toString()
                )
            )
        }
    }

    protected abstract fun onSuccess(response: T?)
    protected abstract fun onFailure(response: BaseResponse?)
}