package com.fyp.models

import android.os.Parcel
import android.os.Parcelable

class videoObjects() : Parcelable {
    var videoUrl=""
    var heading=""
    var text=""

    constructor(parcel: Parcel) : this() {
        videoUrl = parcel.readString().toString()
        heading = parcel.readString().toString()
        text = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoUrl)
        parcel.writeString(heading)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<videoObjects> {
        override fun createFromParcel(parcel: Parcel): videoObjects {
            return videoObjects(parcel)
        }

        override fun newArray(size: Int): Array<videoObjects?> {
            return arrayOfNulls(size)
        }
    }
}