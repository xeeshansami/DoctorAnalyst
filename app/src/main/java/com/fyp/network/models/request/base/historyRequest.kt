/* 
Copyright (c) 2020 Kotlin CustomerAofAccountInfo Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */
package com.hbl.hblaccountopeningapp.network.models.request.base

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


class historyRequest() : Parcelable {
    @SerializedName("pageName")
    var pageName: String = ""

    @SerializedName("phone")
    var phone: String = ""

    @SerializedName("exerciseName")
    var exerciseName: String = ""

    @SerializedName("videoScreenTime")
    var videoScreenTime: String = ""

    @SerializedName("videoUrl")
    var videoUrl: String = ""


    constructor(parcel: Parcel) : this() {
        pageName = parcel.readString().toString()
        phone = parcel.readString().toString()
        exerciseName = parcel.readString().toString()
        videoUrl = parcel.readString().toString()
        videoUrl = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pageName)
        parcel.writeString(phone)
        parcel.writeString(exerciseName)
        parcel.writeString(videoScreenTime)
        parcel.writeString(videoUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<historyRequest> {
        override fun createFromParcel(parcel: Parcel): historyRequest {
            return historyRequest(parcel)
        }

        override fun newArray(size: Int): Array<historyRequest?> {
            return arrayOfNulls(size)
        }
    }
}
