package com.android.mycountryapp.viewmodel.states

import com.android.mycountryapp.data.models.CountriesData
import com.android.mycountryapp.data.models.Country

sealed class CountryState {

    object Empty : CountryState()

    object Loading : CountryState()

    data class Error(val message: String) : CountryState()

    sealed class Success : CountryState() {

        data class SuccessCountries(val countries: List<CountriesData>) : Success()

        data class SuccessCountry(val country: List<Country>) : Success()

    }

}
