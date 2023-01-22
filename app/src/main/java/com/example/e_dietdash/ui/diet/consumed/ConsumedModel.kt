package com.example.e_dietdash.ui.diet.consumed

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class ConsumedModel (
    var nameConsumed : String? = null,
    var substanceConsumed : String? = null,
    var consumedNa : String? = null,
    var consumedK : String? = null,
    var consumedFiber : String? = null,
    var consumedFat : String? = null
) : Parcelable