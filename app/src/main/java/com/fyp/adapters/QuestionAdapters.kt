package com.fyp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fyp.R
import kotlinx.android.synthetic.main.quest_list_view.view.*

class QuestionAdapters(context: Context,private val mList:ArrayList<String>) :
    RecyclerView.Adapter<QuestionAdapters.MyViewHolder>() {
    var context=context

    open inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val questTv = view.questTv!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context)
            .inflate(R.layout.quest_list_view, parent, false)
        return MyViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.questTv.setText(mList.get(position))
    }
}
