package com.example.e_dietdash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Edit(
    var nama: String? = null,
    var weight: Int? = 0,
    var satuan: String? = null,
    var natrium: String? = null,
    var kalium: String? = null,
    var serat: String? = null,
    var lemak: String? = null,
    var berat: String? = null,
): Parcelable
