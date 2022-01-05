package com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks

import com.fyp.network.models.response.base.BaseResponse
import retrofit2.Callback

interface RegisterCallBack {
    fun Success(response: BaseResponse)
    fun Failure(response: BaseResponse)
}