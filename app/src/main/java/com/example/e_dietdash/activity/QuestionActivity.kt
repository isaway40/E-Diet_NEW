package com.example.e_dietdash.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.text.Editable
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.e_dietdash.R
import com.example.e_dietdash.`object`.Const.PATH_COLLECTION
import com.example.e_dietdash.`object`.CustomDialog
import com.example.e_dietdash.model.Data
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_question.*
import kotlinx.android.synthetic.main.activity_question.profile_age
import kotlinx.android.synthetic.main.activity_question.profile_diastolik
import kotlinx.android.synthetic.main.activity_question.profile_gender
import kotlinx.android.synthetic.main.activity_question.profile_sistolik
import kotlinx.android.synthetic.main.fragment_profile.*

class QuestionActivity : AppCompatActivity() {
    companion object {
        const val REQ_EDIT = "req_edit"
    }

    private lateinit var genders: String
    val auth = FirebaseAuth.getInstance()
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mUsersCollection = mFirestore.collection(PATH_COLLECTION)
    val user = auth.currentUser
    val userId = user?.uid
    private var isEdit = false
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)
        isEdit = intent.getBooleanExtra(REQ_EDIT, false)

        btn_lanjut.setOnClickListener{
            CustomDialog.showLoading(this)
            val nama = profile_names.text.toString().trim()
            val age = profile_age.text.toString().trim()
            val sistolik = profile_sistolik.text.toString().trim()
            val diastolik = profile_diastolik.text.toString().trim()
            if (checkValidation(nama, age, genders, sistolik, diastolik)){
                saveData()
            }
        }
        initView()
    }

    private fun initView() {
        if (isEdit) {
            CustomDialog.showLoading(this)
            btn_lanjut.text = getString(R.string.update)
            db = FirebaseFirestore.getInstance()
            val docRef = mUsersCollection.document(userId.toString())
            docRef.get().addOnSuccessListener { document ->
                if (document != null) {
                    val data = document.data
                    val age = data?.get("intAge")
                    val jk = data?.get("jk")
                    val strDiastolik = data?.get("strDiastolik")
                    val strSistolik = data?.get("strSistolik")
                    val nama = data?.get("strName")
                    profile_names.text = Editable.Factory.getInstance().newEditable(nama.toString())
                    profile_age.text = Editable.Factory.getInstance().newEditable(age.toString())
                    profile_sistolik.text =
                        Editable.Factory.getInstance().newEditable(strSistolik.toString())
                    profile_diastolik.text =
                        Editable.Factory.getInstance().newEditable(strDiastolik.toString())
                    when (jk) {
                        "Laki-laki" -> profile_gender.check(R.id.gender_male)
                        "Perempuan" -> profile_gender.check(R.id.gender_female)
                    }
                    genders = jk.toString()
                    CustomDialog.hideLoading()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkValidation(nama: String, age: String, genders: String, sistolik: String, diastolik: String): Boolean {
        if (genders.isEmpty()){
            profile_gender.requestFocus()
        }else if (genders=="-"){
            profile_gender.requestFocus()
        }else if (nama.isEmpty()){
            profile_names.error = "nama tidak boleh kosong"
            profile_names.requestFocus()
        }else if (age.isEmpty()){
            profile_age.error = "umur tidak boleh kosong"
            profile_age.requestFocus()
        }else if (sistolik.isEmpty()){
            profile_sistolik.error = "data sistolik tidak boleh kosong"
            profile_sistolik.requestFocus()
        }else if (diastolik.isEmpty()){
            profile_diastolik.error = "data diastolik tidak boleh kosong"
            profile_diastolik.requestFocus()
        }else{
            return true
        }
        CustomDialog.hideLoading()
        return false
    }

    private fun saveData() {
        setData(userId)
    }

    private fun setData(strId: String?) {
        createUser(strId).addOnCompleteListener {
            if (it.isSuccessful) {
                if (isEdit){
                    Toast.makeText(this, "Sukses perbarui data", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                CustomDialog.hideLoading()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                }
            } else {
                CustomDialog.hideLoading()
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            CustomDialog.hideLoading()
            Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUser(strId: String?): Task<Void> {
        var grade = 1
        val sisto = profile_sistolik.text.toString().toInt()
        val diasto = profile_diastolik.text.toString().toInt()
        if (sisto > 180 && diasto > 110){
            grade = 3
        } else if (sisto > 160 && diasto > 105){
            grade = 2
        } else if (sisto > 150 && diasto > 95){
            grade = 1
        } else if (sisto > 120 && diasto > 80){
            grade = 0
        }
        val writeBatch = mFirestore.batch()
        val path = profile_names.text.toString()
        val id = strId ?: path
        val name = profile_names.text.toString()
        val age = profile_age.text.toString()
        val sistolik = profile_sistolik.text.toString()
        val diastolik = profile_diastolik.text.toString()
        val jk = genders
        val grades = grade

        val data = Data(id, name, sistolik.toInt(), age.toInt(), diastolik.toInt(), jk, grades)
        writeBatch.set(mUsersCollection.document(id), data)
        return writeBatch.commit()
    }

    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked
            when (view.getId()) {
                R.id.gender_male ->
                    if (checked) {
                        genders = "Laki-laki";
                    }
                R.id.gender_female ->
                    if (checked) {
                        genders = "Perempuan";
                    }
            }
        }
    }
}