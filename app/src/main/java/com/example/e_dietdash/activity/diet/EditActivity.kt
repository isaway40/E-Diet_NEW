package com.example.e_dietdash.activity.diet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R
import com.example.e_dietdash.`object`.Const
import com.example.e_dietdash.adapter.EditAdapter
import com.example.e_dietdash.model.Edit
import com.example.e_dietdash.model.NutrientData
import com.example.e_dietdash.ui.education.EducationModelDo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class EditActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val userId = user?.uid
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val current = formatter.format(time)
    lateinit var adapter: EditAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_consumed)

        NutrientData.natrium = intent.getStringExtra("natrium")!!.toInt();
        NutrientData.kalium = intent.getStringExtra("kalium")!!.toInt();
        NutrientData.serat = intent.getStringExtra("serat")!!.toInt();
        NutrientData.lemak = intent.getStringExtra("lemak")!!.toInt();

        val datecon = findViewById<TextView>(R.id.date_consumed)
        val inputDate = current
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dates = inputFormat.parse(inputDate)
        val outputFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        val outputDate = outputFormat.format(dates!!)

        val query: Query = FirebaseFirestore.getInstance().collection(Const.PATH_COLLECTION).document(userId.toString()).collection(
            Const.GIZI).document(current).collection(Const.MAKAN).whereNotEqualTo("nama", "dummy")

        datecon.text = outputDate
        val recyclerView: RecyclerView = findViewById(R.id.rv_consumed)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = EditAdapter(query)
        recyclerView.adapter = adapter

    }
    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}