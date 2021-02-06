package com.fyp.models

import android.os.Parcel
import android.os.Parcelable

class mExercise() : Parcelable {
    var videoUrl=""
    var videoName=""

    constructor(parcel: Parcel) : this() {
        videoUrl = parcel.readString().toString()
        videoName = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(videoUrl)
        parcel.writeString(videoName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<mExercise> {
        override fun createFromParcel(parcel: Parcel): mExercise {
            return mExercise(parcel)
        }

        override fun newArray(size: Int): Array<mExercise?> {
            return arrayOfNulls(size)
        }
    }
}