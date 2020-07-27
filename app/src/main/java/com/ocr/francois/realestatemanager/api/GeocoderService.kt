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
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.google.com/maps/api/geocode/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
//https://maps.google.com/maps/api/geocode/json?address=paris&&key=AIzaSyAF9UHLhqtzUeWQ3HdbMQmV6xkTLYJrYCc