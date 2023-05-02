package com.android.mycountryapp.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.ArrayList

@Parcelize
data class Country(
    val name: CountryName ,
    val idd: CountryIdd ,
    val currencies: Map<String, CountryCurrency> ,
    val capital: ArrayList<String> ,
    val region: String ,
    val subregion: String ,
    val languages: Map<String, String> ,
    val cca2: String ,
    val fifa: String? ,
    val area: Double? ,
    val population: Long ,
    val flags: CountryFlags ,
    val coatOfArms: CountryCoat ,
    val latlng: List<Double>
) : Parcelable
