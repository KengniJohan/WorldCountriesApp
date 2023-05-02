package com.android.mycountryapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryName(
    val common: String ,
    val official: String
) : Parcelable
