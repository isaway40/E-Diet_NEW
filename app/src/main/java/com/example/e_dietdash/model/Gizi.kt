package com.example.e_dietdash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Gizi(
    var natrium: Int? = 0,
    var kalium: Int? = 0,
    var serat: Int? = 0,
    var lemak: Int? = 0,
    var date: String? = "yyyy-mm-dd"
): Parcelable
