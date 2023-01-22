package com.example.e_dietdash.ui.profile

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.e_dietdash.R
import com.example.e_dietdash.`object`.CustomDialog
import com.example.e_dietdash.activity.QuestionActivity
import com.example.e_dietdash.activity.SplashActivity
import com.example.e_dietdash.databinding.FragmentHomeBinding
import com.example.e_dietdash.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {
    private var binding: FragmentHomeBinding? = null
    private lateinit var auth : FirebaseAuth
    lateinit var Ages : TextView
    lateinit var Jks : TextView
    lateinit var Names : TextView
    var umur = ""
    var jk= ""
    var nama = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        Ages = view.findViewById(R.id.ages)
        Jks = view.findViewById(R.id.genders)
        Names = view.findViewById(R.id.names)
        val btnLogout = view.findViewById<Button>(R.id.btn_logout)
        val btnEdit = view.findViewById<Button>(R.id.btn_update)

        Ages.text = UserData.age
        Jks.text = UserData.jk
        Names.text = UserData.nama

        btnEdit.setOnClickListener {
            startActivity(Intent(requireContext(), QuestionActivity::class.java).apply {
                putExtra(QuestionActivity.REQ_EDIT, true)
            })
        }
        btnLogout.setOnClickListener {
            initFirebaseAuth()
            auth.signOut()
            val intent = Intent(requireContext(), SplashActivity::class.java)
            startActivity(intent)
            val activity = requireActivity()
            activity.finish()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }
}