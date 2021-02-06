package com.fyp.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings.PluginState
import android.widget.MediaController
import androidx.recyclerview.widget.RecyclerView
import com.fyp.R
import com.fyp.interfaces.iOnVideoItemClickListner
import com.fyp.models.mExercise
import kotlinx.android.synthetic.main.exercise_list_view.view.*
import kotlinx.android.synthetic.main.quest_list_view.view.*


class ExerciseAdapters(
    context: Context,
    private val mList: ArrayList<mExercise>,
    val onItemClickListner: iOnVideoItemClickListner
) :
    RecyclerView.Adapter<ExerciseAdapters.MyViewHolder>() {
    var context=context
    var itemClickListner: iOnVideoItemClickListner?=null
    init {
        itemClickListner=onItemClickListner
    }
    open inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val exerciseTv = view.tvExercise!!
        val vDView = view.vDView!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.exercise_list_view, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.exerciseTv.text = mList[position].videoName
        holder.vDView.setVideoPath((mList[position].videoUrl))
        //sets MediaController in the video view
        holder.vDView.seekTo(1)
        //give focus to a specific view
        holder.vDView.requestFocus();
        holder.vDView.setOnClickListener {
            itemClickListner?.onItemClick(holder.vDView, mList[position].videoUrl, position)
        }
    }

}
