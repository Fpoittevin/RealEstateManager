package com.ocr.francois.realestatemanager.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.LocationServices
import pub.devrel.easypermissions.EasyPermissions

class LocationTracker(private val context: Context) {

    private val location = MutableLiveData<Location>()
    //private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getLocation(): MutableLiveData<Location> {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        if (EasyPermissions.hasPermissions(
                context,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { result ->
                location.value = result
            }
        }
        return location
    }
}