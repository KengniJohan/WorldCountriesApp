package com.android.mycountryapp.data

import com.android.mycountryapp.data.models.CountriesData
import com.android.mycountryapp.data.models.Country
import com.android.mycountryapp.utils.ALL_COUNTRIES
import com.android.mycountryapp.utils.DETAILS_COUNTRY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryDataApiService {

    @GET(ALL_COUNTRIES)
    suspend fun getAll() : List<CountriesData>

    @GET("$DETAILS_COUNTRY/{name}")
    suspend fun getByName(
        @Path("name") countryName: String
    ) : List<Country>

}