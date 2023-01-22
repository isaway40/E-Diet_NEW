package com.example.e_dietdash.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Data(
    var strId: String = "0",
    var strName: String? = null,
    var strSistolik: Int? = 0,
    var intAge: Int? = 0,
    var strDiastolik: Int? = 0,
    var jk: String? = null,
    var grades: Int? = 0
): Parcelable
