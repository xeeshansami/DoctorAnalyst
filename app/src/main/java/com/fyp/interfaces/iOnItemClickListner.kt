package com.fyp.interfaces

import android.view.View
import java.text.FieldPosition

interface iOnItemClickListner {
    fun onItemClick(view:View,question:String,position: Int){}
}