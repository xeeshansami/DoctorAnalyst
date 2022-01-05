package com.fyp.network.retrofitBuilder

import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.IOException
import java.lang.reflect.Type

internal class PageAdapter : Converter<ResponseBody, String> {
    @Throws(IOException::class)
    override fun convert(responseBody: ResponseBody): String {
        val document = Jsoup.parse(responseBody.string())
        val value = document.select("script")[1]
        return value.html()
    }

    companion object {
        val FACTORY: Converter.Factory = object : Converter.Factory() {
            override fun responseBodyConverter(
                type: Type,
                annotations: Array<Annotation>,
                retrofit: Retrofit
            ): Converter<ResponseBody, *>? {
                return if (type === String::class.java) PageAdapter() else null
            }
        }
    }
}