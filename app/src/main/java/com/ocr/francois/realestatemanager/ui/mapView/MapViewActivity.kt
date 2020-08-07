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
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel


class MapViewActivity : AppCompatActivity(), OnMapReadyCallback {

    private val propertyViewModel: PropertyViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }
    private lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)

        configureMap()
    }

    private fun configureMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.activity_map_view_map_container) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        this.map = map
        getAllProperties()
    }

    private fun addMarker(lat: Double, lng: Double) {
        val latLng = LatLng(lat, lng)
        val markerOptions = MarkerOptions().position(latLng)
        map.addMarker(markerOptions)
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map.moveCamera(CameraUpdateFactory.zoomTo(15F))

        // TODO: ADD USER LOCATION AND MOVE CAMERA TO
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
}