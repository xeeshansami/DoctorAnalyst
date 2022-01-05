package com.hbl.hblaccountopeningapp.network.ResponseHandlers.handler

import com.fyp.network.models.response.base.BaseResponse
import com.hbl.hblaccountopeningapp.network.ResponseHandlers.callbacks.RegisterCallBack

class RegisterBaseHR(callBack: RegisterCallBack) : BaseRH<BaseResponse>() {
    var callback: RegisterCallBack = callBack
    override fun onSuccess(response: BaseResponse?) {
        response?.let { callback.Success(it) }
    }

    override fun onFailure(response: BaseResponse?) {
        response?.let { callback.Failure(it) }
    }
}