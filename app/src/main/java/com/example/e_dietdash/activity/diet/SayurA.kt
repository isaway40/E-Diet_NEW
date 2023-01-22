package com.example.e_dietdash.activity.diet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R
import com.example.e_dietdash.`object`.Const
import com.example.e_dietdash.`object`.Const.GIZI
import com.example.e_dietdash.`object`.Const.MAKAN
import com.example.e_dietdash.`object`.Const.PATH_COLLECTION
import com.example.e_dietdash.`object`.CustomDialog
import com.example.e_dietdash.activity.MainActivity
import com.example.e_dietdash.adapter.BuahAdapter
import com.example.e_dietdash.model.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*
class SayurA : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val userId = user?.uid
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val current = formatter.format(time)
    lateinit var adapter: BuahAdapter
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mUsersCollection = mFirestore.collection(PATH_COLLECTION)
    val userRef = mUsersCollection.document(userId.toString())
    val ordersRef = userRef.collection(GIZI)
    val makanRef = FirebaseFirestore.getInstance().collection(PATH_COLLECTION).document(userId.toString()).collection(GIZI).document(current).collection(MAKAN)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sayur)

        val kirim = findViewById<Button>(R.id.kirims)
        val query: Query = FirebaseFirestore.getInstance().collection(Const.FOOD)
            .whereEqualTo("category", Const.SAYUR_A)
        val recyclerView: RecyclerView = findViewById(R.id.rv_sayur)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = BuahAdapter(query)
        recyclerView.adapter = adapter

        var natrium = 0
        var kalium = 0
        var serat = 0
        var lemak = 0

        natrium = intent.getStringExtra("natrium")!!.toInt();
        kalium = intent.getStringExtra("kalium")!!.toInt();
        serat = intent.getStringExtra("serat")!!.toInt();
        lemak = intent.getStringExtra("lemak")!!.toInt();

        kirim.setOnClickListener {
            var loop = 0
            var first = 0
            loop = recyclerView.childCount.toString().toInt()
            for (i in 0 until recyclerView.childCount) {
                val viewHolder =
                    recyclerView.getChildViewHolder(recyclerView.getChildAt(i)) as BuahAdapter.BuahViewHolder
                val inputValue = viewHolder.input.text.toString()
                if (inputValue != "") {
                    val buah = viewHolder.snapshot?.toObject(Buah::class.java)
                    NutrientData.natrium += buah!!.natrium!! * inputValue.toInt() / buah.weight!!.toInt()
                    NutrientData.kalium += buah.kalium!! * inputValue.toInt() / buah.weight!!.toInt()
                    NutrientData.serat += buah.serat!! * inputValue.toInt() / buah.weight!!.toInt()
                    NutrientData.lemak += buah.lemak!! * inputValue.toInt() / buah.weight!!.toInt()
                    if (first == 0) {
                        Eaten.nama += Eaten.nama + ",dummy"
                        Eaten.weight += Eaten.weight + ",0"
                        Eaten.satuan += Eaten.satuan + ",0"
                        Eaten.natrium += Eaten.natrium + ",0"
                        Eaten.kalium += Eaten.kalium + ",0"
                        Eaten.serat += Eaten.serat + ",0"
                        Eaten.lemak += Eaten.lemak + ",0"
                        Eaten.berat += Eaten.berat + ",0"
                        first = 1
                    }
                    Eaten.nama += Eaten.nama + "," + buah.name
                    Eaten.weight += Eaten.weight + "," + inputValue
                    Eaten.satuan += Eaten.satuan + "," + buah.satuan
                    Eaten.natrium += Eaten.natrium + "," + buah.natrium
                    Eaten.kalium += Eaten.kalium + "," + buah.kalium
                    Eaten.serat += Eaten.serat + "," + buah.serat
                    Eaten.lemak += Eaten.lemak + "," + buah.lemak
                    Eaten.berat += Eaten.berat + "," + buah.weight
                    loop - 1
                    if (loop == 0) {
                        break
                    }
                }
            }

            var loops = 0
            loops = recyclerView.childCount.toString().toInt()
            for (i in (recyclerView.childCount - 1) downTo 0) {

                var lastMakan = ""
                var lastNama = ""
                var lastSatuan = ""
                var lastNatrium = ""
                var lastKalium = ""
                var lastSerat = ""
                var lastLemak = ""
                var lastBerat = ""
                val dataNama = Eaten.nama
                val namaArray = dataNama?.split(",")
                if (namaArray != null) {
                    lastNama = namaArray.get(namaArray.size - loops)
                }

                val dataMakan = Eaten.weight
                val makanArray = dataMakan?.split(",")
                if (makanArray != null) {
                    lastMakan = makanArray.get(makanArray.size - loops)
                }

                val satuanMakan = Eaten.satuan
                val satuanArray = satuanMakan?.split(",")
                if (satuanArray != null) {
                    lastSatuan = satuanArray.get(satuanArray.size - loops)
                }

                val natriumMakan = Eaten.natrium
                val natriumArray = natriumMakan?.split(",")
                if (natriumArray != null) {
                    lastNatrium = natriumArray.get(natriumArray.size - loops)
                }

                val kaliumMakan = Eaten.kalium
                val kaliumArray = kaliumMakan?.split(",")
                if (kaliumArray != null) {
                    lastKalium = kaliumArray.get(kaliumArray.size - loops)
                }

                val seratMakan = Eaten.serat
                val seratArray = seratMakan?.split(",")
                if (seratArray != null) {
                    lastSerat = seratArray.get(seratArray.size - loops)
                }

                val lemakMakan = Eaten.lemak
                val lemakArray = lemakMakan?.split(",")
                if (lemakArray != null) {
                    lastLemak = lemakArray.get(lemakArray.size - loops)
                }

                val beratMakan = Eaten.berat
                val beratArray = beratMakan?.split(",")
                if (beratArray != null) {
                    lastBerat = beratArray.get(beratArray.size - loops)
                }

                setEat(lastNama, lastMakan, lastSatuan, lastNatrium, lastKalium, lastSerat, lastLemak, lastBerat)
                loops -= 1
                if (i == 0) {
                    break
                }
            }

            natrium += NutrientData.natrium
            kalium += NutrientData.kalium
            serat += NutrientData.serat
            lemak += NutrientData.lemak
            setData(natrium, kalium, serat, lemak)
        }
    }

    private fun setEat(nama: String?, weight: String?, satuan: String?, natrium: String?, kalium: String?, serat: String?, lemak: String?, berat: String?) {
        createKonsumsi(nama, weight, satuan, natrium, kalium, serat, lemak, berat).addOnCompleteListener {
            if (it.isSuccessful) {
                Eaten.nama = ""
                Eaten.weight = ""
            } else {
                CustomDialog.hideLoading()
            }
        }.addOnFailureListener {
            CustomDialog.hideLoading()
        }
    }

    private fun createKonsumsi(nama: String?, weight: String?, satuan: String?, natrium: String?, kalium: String?, serat: String?, lemak: String?, berat: String?): Task<Void> {
        val writeBatch = mFirestore.batch()
        if (nama != null && nama.isNotEmpty()) {
            val weightIncrement = weight?.toDouble()
            val data = hashMapOf(
                "weight" to FieldValue.increment(weightIncrement!!),
                "satuan" to satuan,
                "natrium" to natrium,
                "kalium" to kalium,
                "serat" to serat,
                "lemak" to lemak,
                "berat" to berat
            )
            writeBatch.update(makanRef.document(nama.toString()), data)
        }
        return writeBatch.commit().addOnSuccessListener {
        }.addOnFailureListener { e ->
            val writeBatch = mFirestore.batch()
            if (nama != null && nama.isNotEmpty()) {
                val weights =weight?.toDouble()
                val data = Eat(nama, weights, satuan, natrium, kalium, serat, lemak, berat)
                writeBatch.set(makanRef.document(nama.toString()), data)
            }
            writeBatch.commit()
        }
    }

    private fun setData(natrium: Int?, kalium: Int?, serat: Int?, lemak: Int?) {
        createUser(natrium, kalium, serat, lemak).addOnCompleteListener {
            if (it.isSuccessful) {
                NutrientData.natrium = 0
                NutrientData.lemak = 0
                NutrientData.serat = 0
                NutrientData.kalium = 0
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                CustomDialog.hideLoading()
                Toast.makeText(this, "Gagal", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener {
            CustomDialog.hideLoading()
            Toast.makeText(this, "Error ${it.message}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun createUser(natrium: Int?, kalium: Int?, serat: Int?, lemak: Int?): Task<Void> {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(time)
        val writeBatch = mFirestore.batch()
        val ids = current
        val data = Gizi(natrium, kalium, serat, lemak, current)
        writeBatch.set(ordersRef.document(ids), data)
        return writeBatch.commit()
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.startListening()
    }
}