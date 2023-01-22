package com.example.e_dietdash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Buah(
    var category: String? = null,
    var kalium: Int? = 0,
    var lemak: Int? = 0,
    var name: String? = null,
    var natrium: Int? = 0,
    var satuan: String? = null,
    var serat: Int? = 0,
    var weight: Int? = 0
): Parcelable
