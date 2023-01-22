package com.example.e_dietdash.ui.diet

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.example.e_dietdash.R
import com.example.e_dietdash.`object`.Const
import com.example.e_dietdash.activity.MainActivity
import com.example.e_dietdash.activity.diet.*
import com.example.e_dietdash.databinding.FragmentDietBinding
import com.example.e_dietdash.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DietFragment : Fragment() {

    private var binding: FragmentDietBinding? = null
    private lateinit var viewPager: ViewPager

    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_diet, container, false)

        var nat = UserData.natrium
        var kal = UserData.kalium
        var ser = UserData.serat
        var lem = UserData.lemak

        if (nat.isNullOrEmpty() || nat == "" || nat == "null") {
            nat = "0"
        }
        if (kal.isNullOrEmpty() || kal == "" || kal == "null") {
            kal = "0"
        }
        if (ser.isNullOrEmpty() || ser == "" || ser == "null") {
            ser = "0"
        }
        if (lem.isNullOrEmpty() || lem == "" || lem == "null") {
            lem = "0"
        }

        val natrium = nat
        val kalium = kal
        val serat = ser
        val lemak = lem

        val natriums: TextView = view.findViewById(R.id.natrium)
        val kaliums: TextView = view.findViewById(R.id.kalium)
        val serats: TextView = view.findViewById(R.id.serat)
        val lemakss: TextView = view.findViewById(R.id.Lema)

                when (UserData.grad) {
                    "3" -> {
                        natriums.text = "200-400 mg"
                    }
                    "2" -> {
                        natriums.text = "600-800 mg"
                    }
                    "1" -> {
                        natriums.text = "1000-1200 mg"
                    }
                    "0" -> {
                        natriums.text = "1200-2300 mg"
                    }
                }
                kaliums.text = "4700 mg"
                serats.text = "25000 g"
                lemakss.text = "50000 g"

                val edit = view.findViewById<ImageButton>(R.id.btn_more)
                edit.setOnClickListener {
                    val intent = Intent (activity, EditActivity::class.java)
                    intent.putExtra("natrium", natrium);
                    intent.putExtra("kalium", kalium);
                    intent.putExtra("serat", serat);
                    intent.putExtra("lemak", lemak);
                    activity?.startActivity(intent)
                }

                val linearLayout = view.findViewById<CardView>(R.id.to_buah)
                linearLayout.setOnClickListener {
                    val intent = Intent (activity, BuahActivity::class.java)
                    intent.putExtra("natrium", natrium);
                    intent.putExtra("kalium", kalium);
                    intent.putExtra("serat", serat);
                    intent.putExtra("lemak", lemak);
                    activity?.startActivity(intent)
                }

                val linearLayoutb = view.findViewById<CardView>(R.id.to_buahb)
                linearLayoutb.setOnClickListener {
                    val intent = Intent (activity, BuahBActivity::class.java)
                    intent.putExtra("natrium", natrium);
                    intent.putExtra("kalium", kalium);
                    intent.putExtra("serat", serat);
                    intent.putExtra("lemak", lemak);
                    activity?.startActivity(intent)
                }

                val sayurA = view.findViewById<CardView>(R.id.sayurB)
                sayurA.setOnClickListener {
                    val intent = Intent (activity, SayurA::class.java)
                    intent.putExtra("natrium", natrium);
                    intent.putExtra("kalium", kalium);
                    intent.putExtra("serat", serat);
                    intent.putExtra("lemak", lemak);
                    activity?.startActivity(intent)
                }

                val sayurB = view.findViewById<CardView>(R.id.sayurA)
                sayurB.setOnClickListener {
                    val intent = Intent (activity, SayurB::class.java)
                    intent.putExtra("natrium", natrium);
                    intent.putExtra("kalium", kalium);
                    intent.putExtra("serat", serat);
                    intent.putExtra("lemak", lemak);
                    activity?.startActivity(intent)
                }

                val lemaks = view.findViewById<CardView>(R.id.lemak)
                lemaks.setOnClickListener {
                    val intent = Intent (activity, LemakActivity::class.java)
                    intent.putExtra("natrium", natrium);
                    intent.putExtra("kalium", kalium);
                    intent.putExtra("serat", serat);
                    intent.putExtra("lemak", lemak);
                    activity?.startActivity(intent)
                }

        val lmmenu = LinearLayoutManager(activity)
        lmmenu.orientation = LinearLayoutManager.HORIZONTAL
        val lmnutrition = LinearLayoutManager(activity)
        lmnutrition.orientation = LinearLayoutManager.VERTICAL

        val mainActivity = activity as MainActivity
        viewPager = mainActivity.findViewById(R.id.viewPager)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}