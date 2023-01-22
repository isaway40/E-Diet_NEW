package com.example.e_dietdash.ui.education

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R

class EducationAdapterDont (var data : ArrayList<EducationModelDont>, var context: Activity?) : RecyclerView.Adapter<EducationAdapterDont.MyViewHolder>() {

    class MyViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val DontHead = view.findViewById<TextView>(R.id.dont_head)
        val DontBody = view.findViewById<TextView>(R.id.dont_body)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.list_educationdont, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.DontHead.text = data[position].dontHead
        holder.DontBody.text = data[position].dontBody
    }

    override fun getItemCount(): Int {
        return data.size
    }

}