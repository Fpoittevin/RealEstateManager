package com.ocr.francois.realestatemanager.ui.mapView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import androidx.activity.viewModels
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import kotlinx.android.synthetic.main.activity_map_view.*
import pub.devrel.easypermissions.EasyPermissions

class MapViewActivity : BaseActivity(),
    OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener,
    EasyPermissions.PermissionCallbacks {

    private val mapViewModel: MapViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }
    private lateinit var map: GoogleMap

    companion object {
        const val PROPERTY_ID_KEY = "propertyId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_map_view)
        configureToolbar()


        observeConnexion()
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
                resources.getString(R.string.location_required),
                123,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            checkLocationIsEnabled()
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        finish()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        checkLocationIsEnabled()
    }

    private fun checkLocationIsEnabled() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            configureMap()
        } else {
            MaterialAlertDialogBuilder(this).apply {
                setTitle(R.string.location_dialog_title)
                setMessage(R.string.location_required)
                setPositiveButton(
                    resources.getString(R.string.ok)
                ) { _, _ ->
                    val locationIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivityForResult(locationIntent, 1256)
                }
                setNegativeButton(
                    resources.getString(R.string.cancel)
                ) { _, _ ->
                    finish()
                }
                show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        checkLocationIsEnabled()
    }

    private fun configureMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.activity_map_view_map_container) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        this.map = map.apply {
            setOnCameraMoveListener(this@MapViewActivity)
            setOnMarkerClickListener(this@MapViewActivity)
            isMyLocationEnabled = true
        }
        zoomToUserLocation()
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation() {
        LocationServices.getFusedLocationProviderClient(this)
            .lastLocation.addOnSuccessListener { locationResult ->

                locationResult?.let {
                    val userLocation = LatLng(it.latitude, it.longitude)
                    val cameraPosition = CameraPosition.Builder()
                        .target(userLocation).zoom(15F).build()
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                }
            }
    }

    private fun getAndShowPropertiesInMapBounds() {
        mapViewModel.getPropertiesInBounds(map.projection.visibleRegion.latLngBounds)
            .observe(this, { properties ->
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

    private fun configureToolbar() {
        setSupportActionBar(activity_map_view_toolbar)
        supportActionBar?.let{
            it.setDisplayHomeAsUpEnabled(true)
            it.setTitle(R.string.map_title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCameraMove() {
        getAndShowPropertiesInMapBounds()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val id = marker.tag
        val propertyDetailsIntent = Intent(this, PropertyDetailsActivity::class.java).apply {
            putExtra(PROPERTY_ID_KEY, id as Long)
        }
        startActivity(propertyDetailsIntent)

        return true
    }
}