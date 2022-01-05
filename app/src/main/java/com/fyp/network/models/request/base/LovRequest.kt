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


class LovRequest() : Parcelable {
    @SerializedName("identifier")
    var identifier: String = ""

    @SerializedName("condition")
    var condition: Boolean = false

    @SerializedName("conditionmatrix")
    var conditionmatrix: String = ""

    @SerializedName("conditionname")
    var conditionname: String = ""

    @SerializedName("conditionvalue")
    var conditionvalue: String = ""

    @SerializedName("CUST_SEGMENT")
    var CUST_SEGMENT: String = ""

    @SerializedName("BR_TYPE")
    var BR_TYPE: String = ""

    @SerializedName("gender")
    var gender: String = ""


    constructor(parcel: Parcel) : this() {
        gender = parcel.readString().toString()
        identifier = parcel.readString().toString()
        condition = parcel.readByte() != 0.toByte()
        conditionmatrix = parcel.readString().toString()
        conditionname = parcel.readString().toString()
        conditionvalue = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(gender)
        parcel.writeString(identifier)
        parcel.writeByte(if (condition) 1 else 0)
        parcel.writeString(conditionmatrix)
        parcel.writeString(conditionname)
        parcel.writeString(conditionvalue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LovRequest> {
        override fun createFromParcel(parcel: Parcel): LovRequest {
            return LovRequest(parcel)
        }

        override fun newArray(size: Int): Array<LovRequest?> {
            return arrayOfNulls(size)
        }
    }
}
