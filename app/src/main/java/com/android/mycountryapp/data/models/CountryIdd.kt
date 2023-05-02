package com.android.mycountryapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryIdd(
    val root: String,
    val suffixes: List<String>
) : Parcelable
