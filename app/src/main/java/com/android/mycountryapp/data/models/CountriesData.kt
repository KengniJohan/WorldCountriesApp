package com.android.mycountryapp.data.models

data class CountriesData(
    val name: CountryName ,
    val idd: CountryIdd ,
    val currencies: Map<String, CountryCurrency> ,
    val capital: List<String> ,
    val region: String ,
    val subregion: String ,
    val languages: Map<String, String> ,
    val cca2: String ,
    val cca3: String ,
    val area: Double? ,
    val population: Long ,
    val flags: List<String> ,
    val coatOfArms: CountryCoat ,
    val latlng: List<Double>
)
