package com.example.e_dietdash.ui.education

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R

class EducationAdapterDo (var data : ArrayList<EducationModelDo>, var context: Activity?) : RecyclerView.Adapter<EducationAdapterDo.MyViewHolder>() {

    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val DoHead = view.findViewById<TextView>(R.id.do_head)
        val DoBody = view.findViewById<TextView>(R.id.do_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.list_educationdo, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.DoHead.text = data[position].doHead
        holder.DoBody.text = data[position].doBody
    }

    override fun getItemCount(): Int {
        return data.size
    }

}