package com.fyp.models

import android.os.Parcel
import android.os.Parcelable

class mQuestions() : Parcelable {
    var questions=""
    var position=0

    constructor(parcel: Parcel) : this() {
        questions = parcel.readString().toString()
        position = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(questions)
        parcel.writeInt(position)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<mQuestions> {
        override fun createFromParcel(parcel: Parcel): mQuestions {
            return mQuestions(parcel)
        }

        override fun newArray(size: Int): Array<mQuestions?> {
            return arrayOfNulls(size)
        }
    }
}