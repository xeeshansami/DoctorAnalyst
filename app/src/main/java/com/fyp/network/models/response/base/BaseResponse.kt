package com.fyp.network.models.response.base


import com.google.gson.annotations.SerializedName

class BaseResponse(msg:String) {
    @SerializedName("message")
    var message: String = ""
}