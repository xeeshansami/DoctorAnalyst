package com.fyp.network.models.request.base


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

class RegisterRequest() : Parcelable {
    @SerializedName("age")
    var age: String = ""
    @SerializedName("city")
    var city: String = ""
    @SerializedName("gender")
    var gender: String = ""
    @SerializedName("name")
    var name: String = ""
    @SerializedName("phone")
    var phone: String = ""

    constructor(parcel: Parcel) : this() {
        age = parcel.toString()
        city = parcel.toString()
        gender = parcel.toString()
        name = parcel.toString()
        phone = parcel.toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(age)
        parcel.writeString(city)
        parcel.writeString(gender)
        parcel.writeString(name)
        parcel.writeString(phone)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RegisterRequest> {
        override fun createFromParcel(parcel: Parcel): RegisterRequest {
            return RegisterRequest(parcel)
        }

        override fun newArray(size: Int): Array<RegisterRequest?> {
            return arrayOfNulls(size)
        }
    }
}