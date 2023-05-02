package com.android.mycountryapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryFlags(
    val png: String ,
    val svg: String ,
    val alt: String
) : Parcelable
