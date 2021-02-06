package com.fyp.interfaces

import android.view.View
import android.widget.VideoView
import java.text.FieldPosition

interface iOnVideoItemClickListner {
    fun onItemClick(view:VideoView,question:String,position: Int){}
}