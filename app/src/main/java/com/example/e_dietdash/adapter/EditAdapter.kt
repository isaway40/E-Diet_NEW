package com.example.e_dietdash.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R
import com.example.e_dietdash.`object`.Const
import com.example.e_dietdash.`object`.CustomDialog
import com.example.e_dietdash.activity.MainActivity
import com.example.e_dietdash.model.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class EditAdapter(query: Query) : FirestoreAdapter<EditAdapter.EditViewHolder>(query) {
    class EditViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        val userId = user?.uid
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        val current = formatter.format(time)
        val qualityConsumed: TextView = itemView.findViewById(R.id.quantity_consumed)
        val quality: TextView = itemView.findViewById(R.id.quantity)
        val edit: ImageButton = itemView.findViewById(R.id.btn_edit)
        val kirim: Button = itemView.findViewById(R.id.kirim)
        val delete: ImageButton = itemView.findViewById(R.id.btn_delete)
        val listNa: TextView = itemView.findViewById(R.id.consumed_na)
        val listK: TextView = itemView.findViewById(R.id.consumed_k)
        val listSe: TextView = itemView.findViewById(R.id.consumed_fiber)
        val listLe: TextView = itemView.findViewById(R.id.consumed_fat)
        val satuan: TextView = itemView.findViewById(R.id.satuan)
        val edit_consumed: EditText = itemView.findViewById(R.id.edit_comsumed)
        val name_consumed: TextView = itemView.findViewById(R.id.name_consumed)
        var snapshot: DocumentSnapshot? = null
        private val mFirestore = FirebaseFirestore.getInstance()
        private val mUsersCollection = mFirestore.collection(Const.PATH_COLLECTION)
        val userRef = mUsersCollection.document(userId.toString())
        val ordersRef = userRef.collection(Const.GIZI)
        val makanRef = FirebaseFirestore.getInstance().collection(Const.PATH_COLLECTION).document(userId.toString()).collection(
            Const.GIZI).document(current).collection(Const.MAKAN)
        var nutrienNatrium = 0
        var nutrienKalium = 0
        var nutrienSerat = 0
        var nutrienLemak = 0

        fun bind(snapshot: DocumentSnapshot) {
            this.snapshot = snapshot
            val edits: Edit? = snapshot.toObject(Edit::class.java)
            val natrium = edits?.natrium.toString().toInt()
            val kalium = edits?.kalium.toString().toInt()
            val serat = edits?.serat.toString().toInt()
            val lemak = edits?.lemak.toString().toInt()
            val berat = edits?.berat.toString().toInt()
            val nama = edits?.nama.toString()
            val weight = edits?.weight

            listNa.text = natrium.toString()
            listK.text = kalium.toString()
            listSe.text = serat.toString()
            listLe.text = lemak.toString()

            name_consumed.text = nama
            qualityConsumed.text = weight.toString()
            satuan.text = edits?.satuan
            edit_consumed.setText(weight.toString())

            edit.setOnClickListener {
                edit.visibility = View.GONE
                delete.visibility = View.GONE
                quality.visibility = View.GONE
                qualityConsumed.visibility = View.GONE
                kirim.visibility = View.VISIBLE
                satuan.visibility = View.VISIBLE
                edit_consumed.visibility = View.VISIBLE
            }
            kirim.setOnClickListener {
                edit.visibility = View.VISIBLE
                delete.visibility = View.VISIBLE
                quality.visibility = View.VISIBLE
                qualityConsumed.visibility = View.VISIBLE
                kirim.visibility = View.GONE
                satuan.visibility = View.GONE
                edit_consumed.visibility = View.GONE
                val input = edit_consumed.getText().toString().toInt()
                if (input > 0) {
                    val beforeNatrium = natrium * weight!! / berat
                    val beforeKalium = kalium * weight / berat
                    val beforeSerat = serat * weight / berat
                    val beforeLemak = lemak * weight / berat

                    val afterNatrium = natrium * input / berat
                    val afterKalium = kalium * input / berat
                    val afterSerat = serat * input / berat
                    val afterLemak = lemak * input / berat

                    nutrienNatrium = NutrientData.natrium - beforeNatrium + afterNatrium
                    nutrienKalium = NutrientData.kalium - beforeKalium + afterKalium
                    nutrienSerat = NutrientData.serat - beforeSerat + afterSerat
                    nutrienLemak = NutrientData.lemak - beforeLemak + afterLemak

                    createKonsumsi(nama, input.toString(), edits.satuan, natrium.toString(), kalium.toString(), serat.toString(), lemak.toString(), berat.toString())
                    setData(nutrienNatrium, nutrienKalium, nutrienSerat, nutrienLemak)
                }
            }

            delete.setOnClickListener {
                val builder = AlertDialog.Builder(itemView.context)
                builder.setMessage("Hapus data ?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { _, _ ->
                        // Delete selected note from database
                        makanRef.document(nama).delete().addOnSuccessListener {
                            Toast.makeText(itemView.context, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener {
                                Toast.makeText(itemView.context, "Data gagal dihapus", Toast.LENGTH_SHORT).show()
                        }

                        val beforeNatrium = natrium * weight!! / berat
                        val beforeKalium = kalium * weight / berat
                        val beforeSerat = serat * weight / berat
                        val beforeLemak = lemak * weight / berat

                        nutrienNatrium = NutrientData.natrium - beforeNatrium
                        nutrienKalium = NutrientData.kalium - beforeKalium
                        nutrienSerat = NutrientData.serat - beforeSerat
                        nutrienLemak = NutrientData.lemak - beforeLemak

                        setData(nutrienNatrium, nutrienKalium, nutrienSerat, nutrienLemak)
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        dialog.dismiss()
                    }
                val alert = builder.create()
                alert.show()
            }
        }

        private fun setData(natrium: Int?, kalium: Int?, serat: Int?, lemak: Int?) {
            createUser(natrium, kalium, serat, lemak).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(itemView.context, "Berhasil update data", Toast.LENGTH_SHORT).show()
                    NutrientData.natrium = nutrienNatrium
                    NutrientData.kalium = nutrienKalium
                    NutrientData.serat = nutrienSerat
                    NutrientData.lemak = nutrienLemak
                    val intent = Intent(itemView.context, MainActivity::class.java)
                    itemView.context.startActivity(intent)
                } else {
                    CustomDialog.hideLoading()
                    Toast.makeText(itemView.context, "Gagal", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                CustomDialog.hideLoading()
                Toast.makeText(itemView.context, "Error ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        private fun createUser(natrium: Int?, kalium: Int?, serat: Int?, lemak: Int?): Task<Void> {
            val writeBatch = mFirestore.batch()
            val ids = current
            val data = Gizi(natrium, kalium, serat, lemak, current)
            writeBatch.set(ordersRef.document(ids), data)
            return writeBatch.commit()
        }

        private fun createKonsumsi(nama: String?, weight: String?, satuan: String?, natrium: String?, kalium: String?, serat: String?, lemak: String?, berat: String?): Task<Void> {
            val writeBatch = mFirestore.batch()
            if (nama != null && nama.isNotEmpty()) {
                val weights =weight?.toDouble()
                val data = Eat(nama, weights, satuan, natrium, kalium, serat, lemak, berat)
                writeBatch.set(makanRef.document(nama.toString()), data)
            }
            return writeBatch.commit()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditViewHolder {
        return EditViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.list_consumed, parent, false)
        )
    }

    override fun onBindViewHolder(holder: EditViewHolder, position: Int) {
        getSnapshot(position)?.let { snapshot -> holder.bind(snapshot) }
    }
}