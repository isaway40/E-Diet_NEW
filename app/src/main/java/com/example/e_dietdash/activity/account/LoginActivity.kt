package com.example.e_dietdash.activity.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.e_dietdash.`object`.CustomDialog
import com.example.e_dietdash.activity.MainActivity
import com.example.e_dietdash.databinding.ActivityLoginBinding
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initFirebaseAuth()

        binding.btnBack.setOnClickListener {
            finish()
        }

        tv_forgot.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val email = profile_email.text.toString().trim()
            val password = profile_password.text.toString().trim()
            CustomDialog.showLoading(this)
            if (checkValidation(email, password)){
                loginToServer(email, password)
            }
        }
    }

    private fun loginToServer(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        fireBaseAuth(credential)
    }

    private fun fireBaseAuth(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                CustomDialog.hideLoading()
                if (task.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
            }
            .addOnFailureListener { exception ->
                CustomDialog.hideLoading()
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkValidation(email: String, password: String): Boolean {
        if (email.isEmpty()){
            profile_email.error = "email tidak boleh kosong"
            profile_email.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            profile_email.error = "gunakan alamat email yang valid"
            profile_email.requestFocus()
        }else if (password.isEmpty()){
            profile_password.error = "password tidak boleh kosong"
            profile_password.requestFocus()
        }else{
            return true
        }
        CustomDialog.hideLoading()
        return false
    }

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }
}