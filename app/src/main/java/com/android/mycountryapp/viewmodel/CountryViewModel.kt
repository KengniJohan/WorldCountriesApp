package com.android.mycountryapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.mycountryapp.repository.CountryRepository
import com.android.mycountryapp.viewmodel.states.CountryState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val repository: CountryRepository
    ) : ViewModel()
{

    private var countryMutableState = MutableStateFlow<CountryState>(CountryState.Empty)
    val countryState = countryMutableState.asStateFlow()

    private var countriesMutableState = MutableStateFlow<CountryState>(CountryState.Empty)
    val countriesState = countriesMutableState.asStateFlow()

    fun getAll() {

        countriesMutableState.value = CountryState.Loading

        viewModelScope.launch(Dispatchers.IO){

            try {
                val countries = repository.getAll()
                if (countries.isSuccessful) {
                    if (!countries.body().isNullOrEmpty()){
                        countriesMutableState.value = CountryState.Success.SuccessCountries(countries.body()!!)
                    } else {
                        countriesMutableState.value = CountryState.Error("Unable to fetch data !")
                    }
                }
            } catch (exception: HttpException) {
                countriesMutableState.value = CountryState.Error("No internet connection !")
            } catch (exception: IOException) {
                countriesMutableState.value = CountryState.Error("Something went wrong !")
            }

        }

    }

    fun getByName(countryName: String) {

        countryMutableState.value = CountryState.Loading

        viewModelScope.launch(Dispatchers.IO) {

            try {
                val country = repository.getByName(countryName)
                countryMutableState.value = CountryState.Success.SuccessCountry(country)
            } catch (exception: HttpException) {
                countryMutableState.value = CountryState.Error("No internet connection !")
            } catch (exception: IOException) {
                countryMutableState.value = CountryState.Error("Something went wrong !")
            }

        }
    }

}