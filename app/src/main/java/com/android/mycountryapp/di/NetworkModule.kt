package com.android.mycountryapp.di

import com.android.mycountryapp.data.CountryDataApiService
import com.android.mycountryapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun okHttp() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    fun retrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }

    @Provides
    fun countryDataApiService(retrofit: Retrofit) : CountryDataApiService {
        return retrofit.create(CountryDataApiService::class.java)
    }
}