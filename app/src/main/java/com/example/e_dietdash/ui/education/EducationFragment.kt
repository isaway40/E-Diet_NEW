package com.example.e_dietdash.ui.education

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R
import com.example.e_dietdash.databinding.FragmentEducationBinding
import com.example.e_dietdash.model.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EducationFragment : Fragment() {

    private var binding: FragmentEducationBinding? = null
    lateinit var Grade : TextView
    lateinit var RvEducationDo : RecyclerView
    lateinit var RvEducationDont : RecyclerView
    var watch = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_education, container, false)

        when (UserData.age) {
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


        Grade = view.findViewById(R.id.grade)
        RvEducationDo = view.findViewById(R.id.rv_educationdo)
        RvEducationDont = view.findViewById(R.id.rv_educationdont)

        val lmdo = LinearLayoutManager(activity)
        lmdo.orientation = LinearLayoutManager.VERTICAL
        val lmdont = LinearLayoutManager(activity)
        lmdont.orientation = LinearLayoutManager.VERTICAL

        val adapterEducationDo = EducationAdapterDo(ArrayEducationDo,activity)
        RvEducationDo.setHasFixedSize(true)
        RvEducationDo.layoutManager = lmdo
        RvEducationDo.adapter = adapterEducationDo

        val adapterEducationDont = EducationAdapterDont(ArrayEducationDont,activity)
        RvEducationDont.setHasFixedSize(true)
        RvEducationDont.layoutManager = lmdont
        RvEducationDont.adapter = adapterEducationDont

        return view
    }

    val ArrayEducationDo : ArrayList<EducationModelDo>get() {

        val array_educationdo = ArrayList<EducationModelDo>()

        //1
        val data1 = EducationModelDo()
        data1.doHead = "Olahraga"
        data1.doBody = "Olahraga yang cukup setiap harinya, minimal 30 menit sekali"

        //2
        val data2 = EducationModelDo()
        data2.doHead = "Istirahat"
        data2.doBody = "Tidur yang cukup setiap malamnya, sekitar 8 jam"

        //3
        val data3 = EducationModelDo()
        data3.doHead = "Rutin Makan"
        data3.doBody = "Makan tidak perlu banyak yang penting rutin, 3 kali sehari"

        array_educationdo.add(data1)
        array_educationdo.add(data2)
        array_educationdo.add(data3)

        return array_educationdo
    }

    val ArrayEducationDont : ArrayList<EducationModelDont>get() {

        val array_educationdont = ArrayList<EducationModelDont>()

        //1
        val data1 = EducationModelDont()
        data1.dontHead = "Udud"
        data1.dontBody = "Jangan udud coy!"

        //2
        val data2 = EducationModelDont()
        data2.dontHead = "Kobam"
        data2.dontBody = "Yaelah ini apalagi"

        //3
        val data3 = EducationModelDont()
        data3.dontHead = "Malas"
        data3.dontBody = "1 of 7 ddeadly sins"

        array_educationdont.add(data1)
        array_educationdont.add(data2)
        array_educationdont.add(data3)

        return array_educationdont
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}