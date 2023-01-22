package com.example.e_dietdash.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R
import com.example.e_dietdash.model.Gizi
import com.example.e_dietdash.model.UserData
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class GiziAdapter(query: Query) : FirestoreAdapter<GiziAdapter.GiziViewHolder>(query) {

    class GiziViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val natrium: TextView = itemView.findViewById(R.id.percent_na)
        private val kalium: TextView = itemView.findViewById(R.id.percent_k)
        private val serat: TextView = itemView.findViewById(R.id.percent_fiber)
        private val lemak: TextView = itemView.findViewById(R.id.percent_fat)
        private val date: TextView = itemView.findViewById(R.id.date)
        private val status_natrium: TextView = itemView.findViewById(R.id.status_na)
        private val status_kalium: TextView = itemView.findViewById(R.id.status_k)
        private val status_serat: TextView = itemView.findViewById(R.id.status_fiber)
        private val status_lemak: TextView = itemView.findViewById(R.id.status_fat)


        fun bind(snapshot: DocumentSnapshot) {
            val gizi: Gizi? = snapshot.toObject(Gizi::class.java)
            val inputDate = gizi?.date.toString()
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val dates = inputFormat.parse(inputDate)
            val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
            val outputDate = outputFormat.format(dates!!)
            natrium.text = gizi?.natrium.toString()
            kalium.text = gizi?.kalium.toString()
            serat.text = gizi?.serat.toString()
            lemak.text = gizi?.lemak.toString()
            date.text = outputDate

            var gnat = 0
            gnat = gizi?.natrium!!

            if (UserData.grad == "1") {
               when {
                   gnat < 1000 -> status_natrium.text = "kurang"
                   gnat > 1200 -> status_natrium.text = "lebih"
                   gnat in 1001..1199 -> status_natrium.text = "cukup"
               }
            }
            if (UserData.grad == "2") {
                when {
                    gnat < 600 -> status_natrium.text = "kurang"
                    gnat > 800 -> status_natrium.text = "lebih"
                    gnat in 601..799 -> status_natrium.text = "cukup"
                }
            }
            if (UserData.grad == "3") {
                when {
                    gnat < 200 -> status_natrium.text = "kurang"
                    gnat > 400 -> status_natrium.text = "lebih"
                    gnat in 201..399 -> status_natrium.text = "cukup"
                }
            }
            when {
                gizi?.kalium!! < 4300 -> status_kalium.text = "kurang"
                gizi.kalium!! > 5100 -> status_kalium.text = "lebih"
                gizi.kalium!! in 4301..5100 -> status_kalium.text = "cukup"
            }
            when {
                gizi?.serat!! < 21000 -> status_serat.text = "kurang"
                gizi.serat!! > 28000 -> status_serat.text = "lebih"
                gizi.serat!! in 21001..28000 -> status_serat.text = "cukup"
            }
            when {
                gizi?.lemak!! < 40000 -> status_lemak.text = "kurang"
                gizi.lemak!! > 60000 -> status_lemak.text = "lebih"
                gizi.lemak!! in 40001..60000 -> status_lemak.text = "cukup"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiziViewHolder {
        return GiziViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.list_home, parent, false)
        )
    }

    override fun onBindViewHolder(holder: GiziViewHolder, position: Int) {
        getSnapshot(position)?.let { snapshot -> holder.bind(snapshot) }
    }
}