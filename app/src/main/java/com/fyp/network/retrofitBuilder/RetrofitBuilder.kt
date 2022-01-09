package com.hbl.hblaccountopeningapp.network.retrofitBuilder

import android.content.Context
import com.fyp.network.gson.GsonProvider
import com.fyp.network.retrofitBuilder.SafeSLLOkHttpClient
import com.fyp.utils.Config
import com.hbl.hblaccountopeningapp.network.apiInterface.APIInterface
import com.hbl.hblaccountopeningapp.network.enums.RetrofitEnums
import com.readystatesoftware.chuck.ChuckInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitBuilder {
    private val retrofitHashMap = HashMap<String, APIInterface>()
    fun getRetrofitInstance(context: Context, url: RetrofitEnums, timeout: Long): APIInterface {
        val baseUrl = url.url
        val okHttpClient =
            getOkHttpClient(context,
                enableNetworkInterceptor(baseUrl),
                timeout)
        if (!retrofitHashMap.containsKey(baseUrl)
            || retrofitHashMap[baseUrl] == null
        ) {
            synchronized(this) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(baseUrl)
//                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(GsonProvider.getInstance()))
                    .client(okHttpClient)
                val restAPI = retrofit.build().create<APIInterface>(APIInterface::class.java)
                retrofitHashMap[baseUrl] = restAPI
                return restAPI
            }
        }
        return retrofitHashMap[baseUrl]!!
    }

    private fun enableNetworkInterceptor(baseUrl: String): Boolean {
        return baseUrl == RetrofitEnums.URL_HBL.url
    }

    private fun getOkHttpClient(context: Context, isHblLink: Boolean, timeout: Long): OkHttpClient {
        val interceptor = HttpLoggingInterceptor().apply {
            level = Config.LOG_LEVEL_API
        }
        val builder = OkHttpClient.Builder()
            .addInterceptor(interceptor)
//            .addInterceptor(ChuckInterceptor(context))
            .callTimeout(timeout, TimeUnit.SECONDS)
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
        builder.addNetworkInterceptor(NetworkInterceptorHBL())
        return builder.build()
    }

    public class NetworkInterceptorHBL() : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val original = chain.request()
            var token = "test"
            val headerTag = original.header(APIInterface.HEADER_TAG)
            val builder = original.newBuilder()
            if (headerTag == null && token != null && !token.equals(
                    "",
                    ignoreCase = true
                )
            ) {
                builder.addHeader("hrtoken", token)
            }
            var acctoken = ""
//            if (GlobalClass.sharedPreferenceManager.loginData.getTOKEN().isNullOrEmpty()) {
//                acctoken = ""
//            } else {
//                acctoken = GlobalClass.sharedPreferenceManager.loginData.getTOKEN().toString()
//            }
//            var accrefreshtoken = ""
//            if (GlobalClass.sharedPreferenceManager.loginData.getREFRESHTOKEN().isNullOrEmpty()) {
//                accrefreshtoken = ""
//            } else {
//                accrefreshtoken =
//                    GlobalClass.sharedPreferenceManager.loginData.getREFRESHTOKEN().toString()
//            }


            val request = builder
//                .addHeader("os", "ANDROID") // @TODO: add OS method
//                .addHeader("ip", "1.1.1.1") // @TODO: add IP method
////                .addHeader("latlong", GlobalClass.LatLong)
//                .addHeader("macadress", "123456") // @TODO: add MAC Address method
//                .addHeader("channel-id", "2") // @TODO: add MAC Address method
//                .addHeader("aoftoken", acctoken) // @TODO: add acc token method
//                .addHeader("deviceID", GlobalClass.deviceID()) // @TODO: add acc token method
//                .addHeader("aofrefreshtoken", accrefreshtoken) // @TODO: add acc token method
//                .addHeader("Content-Type", "application/json; charset=utf-8")
                .removeHeader(APIInterface.HEADER_TAG)
                .method(original.method, original.body)
                .build()
            val response = chain.proceed(request)
            val response2 = response
            /* if (!response.isSuccessful) {
                 val strResponse = response2.body!!.string()
                 try {
                     val jsonObject = JSONObject(strResponse)
                     if (jsonObject.has("status")) {
                         val statusCode = jsonObject.getString("status")
 
                         if (statusCode != Constants.STATUS_OK_00 && statusCode != Constants.STATUS_HOSPITALS_TRUE) {
                             if (jsonObject.has("message") && jsonObject.getString("message") is String) {
                                 ToastUtils.showToastWith(GlobalClass.applicationContext, jsonObject.getString("message"))
 
                                 if (statusCode == Constants.STATUS_OK_97) {
                                     // Token Expire.
                                     // Handle it.
                                     EventBus.getDefault().post(Constants.EVENT_LOGOUT)
                                 }
                                 return response
                             }
                         }
                     }
                 } catch (e: Exception) {
 
                 }
             }*/

            return response
        }

    }
}
