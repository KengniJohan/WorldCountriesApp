package com.android.mycountryapp.utils

sealed class Screen(val route: String) {

    object CountriesList : Screen("countriesList")

    object CountryDetails : Screen("countryDetails")

}
