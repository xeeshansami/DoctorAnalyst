package com.hbl.hblaccountopeningapp.network.apiInterface


import com.fyp.network.models.request.base.RegisterRequest
import com.fyp.network.models.response.base.BaseResponse
import com.hbl.hblaccountopeningapp.network.models.request.base.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface APIInterface {
    @POST("register.php")
    fun register(@Body body:RequestBody): Call<BaseResponse>

    @POST("login.php")
    fun login(@Body body:RequestBody): Call<BaseResponse>

    @POST("appTime.php")
    fun appTime(@Body body:RequestBody): Call<BaseResponse>

    @POST("updateAppTime.php")
    fun updateAppTime(@Body body:RequestBody): Call<BaseResponse>

    @POST("history.php")
    fun history(@Body body:RequestBody): Call<BaseResponse>

    @POST("updateUser.php")
    fun updateUser(@Body body:RequestBody): Call<BaseResponse>

    companion object {
        const val HEADER_TAG = "@"
        const val HEADER_POSTFIX = ": "
        const val HEADER_TAG_PUBLIC = "public"
    }
}