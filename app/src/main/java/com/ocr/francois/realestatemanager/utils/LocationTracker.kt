package com.ocr.francois.realestatemanager.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import pub.devrel.easypermissions.EasyPermissions

class LocationTracker(private val context: Context) {

    private val location = MutableLiveData<Location>()
    private val locationRequest = LocationRequest()
        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        .setInterval(LOCATION_REQUEST_INTERVAL.toLong())
        .setSmallestDisplacement(LOCATION_REQUEST_SMALLEST_DISPLACEMENT.toFloat())
        .setFastestInterval(LOCATION_REQUEST_FASTEST_DISPLACEMENT.toLong())
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    //TODO: add location request for location update

    @SuppressLint("MissingPermission")
    fun getLocation(): MutableLiveData<Location> {

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult?) {
                locationResult ?: return
                setLocation(locationResult.lastLocation!!)
            }
        }

        if (hasLocationPermissions()) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { result ->
                if (result != null) {
                    location.value = result
                }
            }
        }
        return location
    }

    private fun setLocation(newLocation: Location) {
        location.value = newLocation
    }

    fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    private fun hasLocationPermissions(): Boolean {
        return EasyPermissions.hasPermissions(
            context,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    companion object {

        const val LOCATION_REQUEST_INTERVAL = 30000
        const val LOCATION_REQUEST_SMALLEST_DISPLACEMENT = 100
        const val LOCATION_REQUEST_FASTEST_DISPLACEMENT = 2000
    }
}