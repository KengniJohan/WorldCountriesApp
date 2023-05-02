package com.android.mycountryapp.repository

import com.android.mycountryapp.data.CountryDataApiService
import com.android.mycountryapp.data.models.CountriesData
import com.android.mycountryapp.data.models.Country
import javax.inject.Inject

class CountryRepository @Inject constructor(
    private val countryService: CountryDataApiService
    )
{
    suspend fun getAll() : List<CountriesData> = countryService.getAll()

    suspend fun getByName(countryName: String) : List<Country> = countryService.getByName(countryName)

}