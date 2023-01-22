package com.example.e_dietdash.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R
import com.example.e_dietdash.`object`.Const
import com.example.e_dietdash.`object`.Const.GIZI
import com.example.e_dietdash.`object`.Const.PATH_COLLECTION
import com.example.e_dietdash.`object`.CustomDialog
import com.example.e_dietdash.activity.MainActivity
import com.example.e_dietdash.adapter.GiziAdapter
import com.example.e_dietdash.databinding.FragmentHomeBinding
import com.example.e_dietdash.model.Initinal
import com.example.e_dietdash.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var binding: FragmentHomeBinding? = null
    lateinit var Name : TextView
    lateinit var Gender : TextView
    lateinit var Age : TextView
    lateinit var Grade : TextView
    lateinit var Sistolik : TextView
    lateinit var Diastolik : TextView
    lateinit var RvHome : RecyclerView
    lateinit var adapter: GiziAdapter

    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    private val userId = user?.uid
    private var vie  = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        val query: Query = FirebaseFirestore.getInstance().collection(PATH_COLLECTION).document(userId.toString()).collection(Const.GIZI)
        val recyclerView: RecyclerView = view.findViewById(R.id.rv_home)
        Name = view.findViewById(R.id.name)
        Gender = view.findViewById(R.id.gender)
        Age = view.findViewById(R.id.age)
        Grade = view.findViewById(R.id.grade)
        Sistolik = view.findViewById(R.id.sistolik)
        Diastolik = view.findViewById(R.id.diastolik)

        adapter = GiziAdapter(query)
        recyclerView.adapter = adapter

        activity?.let { CustomDialog.showLoading(it) }
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val userId = user?.uid
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val docRef = db.document("users/$userId")
        docRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val data = document.data
                val age = data?.get("intAge")
                val jk = data?.get("jk")
                val strDiastolik = data?.get("strDiastolik")
                val strSistolik = data?.get("strSistolik")
                val nama = data?.get("strName")
                val grad = data?.get("grades")

                vie = grad.toString()
                Name.text = nama.toString()
                Gender.text = jk.toString()
                Age.text = age.toString() + " Tahun"
                Sistolik.text = strSistolik.toString()
                Diastolik.text = strDiastolik.toString()

                UserData.age = age.toString()
                UserData.jk = jk.toString()
                UserData.strDiastolik = strDiastolik.toString()
                UserData.strSistolik = strSistolik.toString()
                UserData.nama = nama.toString()
                UserData.grad = grad.toString()

                CustomDialog.hideLoading()
            }
            when (vie) {
                "3" -> {
                    Grade.text = "Grade 1"
                }
                "2" -> {
                    Grade.text = "Grade 2"
                }
                "1" -> {
                    Grade.text = "Grade 1"
                }
                "0" -> {
                    Grade.text = "Normal"
                }
            }
        }


        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(time)
        val db2 = FirebaseFirestore.getInstance()
        val nestedRef = db2.collection(PATH_COLLECTION).document(userId.toString()).collection(GIZI).document(current)
        nestedRef.get().addOnSuccessListener { documentSnapshot ->
            if (documentSnapshot.exists()) {
                var natrium = documentSnapshot.get("natrium").toString()
                var kalium = documentSnapshot.get("kalium").toString()
                var serat = documentSnapshot.get("serat").toString()
                var lemak = documentSnapshot.get("lemak").toString()

                if (natrium.isNullOrEmpty() || natrium == "" || natrium == "null") {
                    natrium = "0"
                }
                if (kalium.isNullOrEmpty() || kalium == "" || kalium == "null") {
                    kalium = "0"
                }
                if (serat.isNullOrEmpty() || serat == "" || serat == "null") {
                    serat = "0"
                }
                if (lemak.isNullOrEmpty() || lemak == "" || lemak == "null") {
                    lemak = "0"
                }

                UserData.natrium = natrium
                UserData.kalium = kalium
                UserData.serat = serat
                UserData.lemak = lemak
            }
        }.addOnFailureListener {
            Toast.makeText(requireContext(), "gagal ambil data", Toast.LENGTH_SHORT).show()
        }
        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.VERTICAL
        return view
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
        if (Initinal.init == 0){
            val intent = Intent(requireContext(), MainActivity::class.java)
            requireActivity().startActivity(intent)
            Initinal.init = 1
        }
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}