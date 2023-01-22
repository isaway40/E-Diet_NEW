package com.example.e_dietdash.activity.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.e_dietdash.`object`.CustomDialog
import com.example.e_dietdash.activity.QuestionActivity
import com.example.e_dietdash.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.profile_password
import kotlinx.android.synthetic.main.fragment_profile.*

class SignupActivity : AppCompatActivity() {

    lateinit var binding : ActivitySignupBinding
    lateinit var genders: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnRegister.setOnClickListener {
            val email = profile_emails.text.toString().trim()
            val password = profile_password.text.toString().trim()
            val password_confirm = profile_password_confirm.text.toString().trim()
            CustomDialog.showLoading(this)
            if (checkValidation(email, password, password_confirm)){
                registerToServer(email, password)
            }
        }
    }

    private fun checkValidation(email: String, password: String, password_confirm: String): Boolean {
        if (email.isEmpty()){
            profile_emails.error = "email tidak boleh kosong"
            profile_emails.requestFocus()
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            profile_emails.error = "gunakan alamat email yang valid"
            profile_emails.requestFocus()
        }else if (password.isEmpty()){
            profile_password.error = "password tidak boleh kosong"
            profile_password.requestFocus()
        }else if (password != password_confirm){
            profile_password.error = "password tidak cocok"
            profile_password_confirm.error = "password tidak cocok"
            profile_password.requestFocus()
            profile_password_confirm.requestFocus()
        }else{
            profile_password.error = null
            profile_password_confirm.error = null
            return true
        }
        CustomDialog.hideLoading()
        return false
    }

    private fun registerToServer(email: String, password: String) {
        FirebaseAuth.getInstance()
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{task ->
                CustomDialog.hideLoading()
                if (task.isSuccessful){
                    startActivity(Intent(this, QuestionActivity::class.java).apply {
                        putExtra(QuestionActivity.REQ_EDIT, false)
                    })
                    finishAffinity()
                }
            }
            .addOnFailureListener { exception ->
                CustomDialog.hideLoading()
                Toast.makeText(this, exception.message, Toast.LENGTH_SHORT).show()
            }
    }
}