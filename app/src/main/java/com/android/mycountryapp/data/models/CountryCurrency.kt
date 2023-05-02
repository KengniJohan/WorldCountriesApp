package com.android.mycountryapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CountryCurrency(
    val name: String,
    val symbol: String
) :  Parcelable
