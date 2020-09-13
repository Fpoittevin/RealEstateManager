package com.ocr.francois.realestatemanager.ui.mapView

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import com.ocr.francois.realestatemanager.utils.LocationTracker
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel
import kotlinx.android.synthetic.main.activity_map_view.*
import pub.devrel.easypermissions.EasyPermissions

class MapViewActivity : AppCompatActivity(),
    OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener,
    EasyPermissions.PermissionCallbacks {

    private val propertyViewModel: PropertyViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }
    private lateinit var map: GoogleMap

    private val locationTracker = LocationTracker(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_view)
        configureToolbar()

        //TODO: check network
        checkLocationPermissions()
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_map_view_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
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
        map.setOnCameraMoveListener(this)
        map.setOnMarkerClickListener(this)
        getUserLocation()
    }

    private fun getAndShowPropertiesInMapBounds() {
        propertyViewModel.getPropertiesInBounds(map.projection.visibleRegion.latLngBounds)
            .observe(this, Observer { properties ->
                for (property in properties) {
                    addMarker(property.id!!, property.lat!!, property.lng!!)
                }
            })
    }

    private fun addMarker(id: Long, lat: Double, lng: Double) {
        val latLng = LatLng(lat, lng)
        val markerOptions = MarkerOptions().position(latLng)
        val marker = map.addMarker(markerOptions)
        marker.tag = id
    }

    private fun getUserLocation() {
        locationTracker.getLocation().observe(this, Observer {
            val userLocation = LatLng(it.latitude, it.longitude)
            map.moveCamera(CameraUpdateFactory.newLatLng(userLocation))
            map.moveCamera(CameraUpdateFactory.zoomTo(15F))
        })
    }

    override fun onCameraMove() {
        getAndShowPropertiesInMapBounds()
    }

    override fun onMarkerClick(marker: Marker): Boolean {

        //todo: gérer le click sur tablette

        val id = marker.tag
        val propertyDetailsIntent = Intent(this, PropertyDetailsActivity::class.java).apply {
            putExtra(PROPERTY_ID_KEY, id as Long)
        }
        startActivity(propertyDetailsIntent)

        return true
    }

    companion object {
        const val PROPERTY_ID_KEY = "propertyId"
    }
}