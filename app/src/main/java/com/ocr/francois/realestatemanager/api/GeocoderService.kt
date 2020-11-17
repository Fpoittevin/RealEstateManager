package com.ocr.francois.realestatemanager.api

import com.ocr.francois.realestatemanager.BuildConfig
import com.ocr.francois.realestatemanager.models.GeocodeResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderService {

    @GET("json?key=" + BuildConfig.GOOGLE_API_KEY)
    fun getLocation(@Query("address") address: String): Call<GeocodeResponse>

    companion object {

        private const val GOOGLE_GEOCODE_BASE_URL = "https://maps.google.com/maps/api/geocode/"

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(GOOGLE_GEOCODE_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}