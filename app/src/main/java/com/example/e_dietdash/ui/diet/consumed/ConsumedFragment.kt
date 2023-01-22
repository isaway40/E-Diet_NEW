package com.example.e_dietdash.ui.diet.consumed

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.e_dietdash.R
import com.example.e_dietdash.databinding.FragmentDietBinding

class ConsumedFragment : Fragment() {

    private var binding: FragmentDietBinding? = null
    lateinit var Date : TextView
    lateinit var RvConsumed : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_consumed, container, false)

        Date = view.findViewById(R.id.date_consumed)
        RvConsumed = view.findViewById(R.id.rv_consumed)



        val lm = LinearLayoutManager(activity)
        lm.orientation = LinearLayoutManager.HORIZONTAL

        return view
    }

    val ArrayConsumed : ArrayList<ConsumedModel>get() {

        val array_consumed = ArrayList<ConsumedModel>()

        //1
        val data1 = ConsumedModel()
        data1.nameConsumed = "Apel"
        data1.substanceConsumed = "200 gram"
        data1.consumedNa = "45"
        data1.consumedK = "654"
        data1.consumedFiber = "88"
        data1.consumedFat = "52"

        //2
        val data2 = ConsumedModel()
        data2.nameConsumed = "Jeruk"
        data2.substanceConsumed = "75 gram"
        data2.consumedNa = "32"
        data2.consumedK = "64"
        data2.consumedFiber = "90"
        data2.consumedFat = "32"

        //3
        val data3 = ConsumedModel()
        data3.nameConsumed = "Daging Ayam"
        data3.substanceConsumed = "400 gram"
        data3.consumedNa = "99"
        data3.consumedK = "78"
        data3.consumedFiber = "80"
        data3.consumedFat = "68"

        array_consumed.add(data1)
        array_consumed.add(data2)
        array_consumed.add(data3)

        return array_consumed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}