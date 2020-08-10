package com.ocr.francois.realestatemanager.ui.mapView

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.utils.LocationTracker
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel
import pub.devrel.easypermissions.EasyPermissions


class MapViewActivity : AppCompatActivity(), OnMapReadyCallback,
    EasyPermissions.PermissionCallbacks {

    private val propertyViewModel: PropertyViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }
    private lateinit var map: GoogleMap
    private val locationTracker = LocationTracker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)

        //TODO: check network
        checkLocationPermissions()
    }

    private fun checkLocationPermissions() {
        if (!EasyPermissions.hasPermissions(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            EasyPermissions.requestPermissions(
                this,
                "need location",
                123,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            configureMap()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        finish()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        configureMap()
    }

    private fun configureMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.activity_map_view_map_container) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        getUserLocation()
        getAllProperties()
    }

    private fun addMarker(lat: Double, lng: Double) {
        val latLng = LatLng(lat, lng)
        val markerOptions = MarkerOptions().position(latLng)
        map.addMarker(markerOptions)
    }

    private fun getAllProperties() {
        propertyViewModel.getAllProperties().observe(this, Observer { properties ->
            for (property in properties) {
                if (property.lat != null && property.lng != null) {
                    addMarker(property.lat!!, property.lng!!)
                }
            }
        })
    }

    private fun getUserLocation() {
        locationTracker.getLocation().observe(this, Observer {
            val userLocation = LatLng(it.latitude, it.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
            map.moveCamera(CameraUpdateFactory.zoomTo(10F))
        })
    }
}