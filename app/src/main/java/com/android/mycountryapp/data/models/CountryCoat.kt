package com.android.mycountryapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryCoat(
    val png: String,
    val svg: String
) : Parcelable
