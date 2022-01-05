package com.hbl.hblaccountopeningapp.network.apiInterface

import com.hbl.hblaccountopeningapp.network.retrofitBuilder.Page
import okhttp3.HttpUrl
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface PageService {
    @GET
    operator fun get(@Url url: HttpUrl?): Call<Page?>?
}