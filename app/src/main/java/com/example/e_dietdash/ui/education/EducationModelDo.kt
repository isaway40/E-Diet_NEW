package com.example.e_dietdash.ui.education

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class EducationModelDo (
    var doHead : String? = null,
    var doBody : String? = null
) : Parcelable