package com.hbl.hblaccountopeningapp.network.store

import android.app.Application
import com.fyp.network.models.request.base.RegisterRequest
import com.fyp.utils.Config
import com.fyp.utils.GlobalClass
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.*
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.handler.*
import com.hbl.hblaccountopeningapp.network.apiInterface.APIInterface
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.hbl.hblaccountopeningapp.network.models.request.base.*
import com.hbl.hblaccountopeningapp.network.retrofitBuilder.RetrofitBuilder
import com.hbl.hblaccountopeningapp.network.timeoutInterface.IOnConnectionTimeoutListener
import okhttp3.RequestBody

open class HBLHRStore : Application(), IOnConnectionTimeoutListener {

    override fun onConnectionTimeout() {}

    companion object {
        private val consumerStore: HBLHRStore? = null

        //    APIInterface globalBaseUrl = RetrofitBuilder.INSTANCE.getRetrofitInstance(GlobalClass.applicationContext, RetrofitEnums.URL_HBL);
        val instance: HBLHRStore?
            get() = consumerStore ?: HBLHRStore()
    }


   //TODO register
    fun register(
        url: RetrofitEnums?,
        request: RequestBody,
        callback: RegisterCallBack
    ) {
        var privateInstanceRetrofit: APIInterface? =
            GlobalClass.applicationContext?.let { RetrofitBuilder.getRetrofitInstance(it,  url!!, Config.API_CONNECT_TIMEOUT) }
        privateInstanceRetrofit?.register(request)?.enqueue(RegisterBaseHR(callback))
    }
    //TODO login
    fun login(
        url: RetrofitEnums?,
        request: RequestBody,
        callback: RegisterCallBack
    ) {
        var privateInstanceRetrofit: APIInterface? =
            GlobalClass.applicationContext?.let { RetrofitBuilder.getRetrofitInstance(it,  url!!, Config.API_CONNECT_TIMEOUT) }
        privateInstanceRetrofit?.login(request)?.enqueue(RegisterBaseHR(callback))
    }
 //TODO appTime
    fun appTime(
        url: RetrofitEnums?,
        request: RequestBody,
        callback: RegisterCallBack
    ) {
        var privateInstanceRetrofit: APIInterface? =
            GlobalClass.applicationContext?.let { RetrofitBuilder.getRetrofitInstance(it,  url!!, Config.API_CONNECT_TIMEOUT) }
        privateInstanceRetrofit?.appTime(request)?.enqueue(RegisterBaseHR(callback))
    }

 //TODO updateAppTime
    fun updateAppTime(
        url: RetrofitEnums?,
        request: RequestBody,
        callback: RegisterCallBack
    ) {
        var privateInstanceRetrofit: APIInterface? =
            GlobalClass.applicationContext?.let { RetrofitBuilder.getRetrofitInstance(it,  url!!, Config.API_CONNECT_TIMEOUT) }
        privateInstanceRetrofit?.updateAppTime(request)?.enqueue(RegisterBaseHR(callback))
    }

    //TODO history
    fun history(
        url: RetrofitEnums?,
        request: RequestBody,
        callback: RegisterCallBack
    ) {
        var privateInstanceRetrofit: APIInterface? =
            GlobalClass.applicationContext?.let { RetrofitBuilder.getRetrofitInstance(it,  url!!, Config.API_CONNECT_TIMEOUT) }
        privateInstanceRetrofit?.history(request)?.enqueue(RegisterBaseHR(callback))
    }

}