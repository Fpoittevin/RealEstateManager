package com.ocr.francois.realestatemanager.ui.mapView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.ocr.francois.realestatemanager.databinding.FragmentMapViewBinding
import com.ocr.francois.realestatemanager.injection.Injection
import pub.devrel.easypermissions.EasyPermissions

class MapViewFragment(private val markerClickCallback: MarkerClickCallback) : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapViewBinding
    private lateinit var map: GoogleMap

    private val mapViewModel: MapViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    companion object {
        fun newInstance(markerClickCallback: MarkerClickCallback) =
            MapViewFragment(markerClickCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_view, container, false)
        binding.apply {
            lifecycleOwner = this@MapViewFragment
        }

        //Todo: observeConnexion()
        checkLocationPermissions()

        return binding.root
    }


    private fun checkLocationPermissions() {
        if (!EasyPermissions.hasPermissions(
                requireContext(),
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

    private fun checkLocationIsEnabled() {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            configureMap()
        } else {
            MaterialAlertDialogBuilder(requireContext()).apply {
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
                    //TODO :finish()
                }
                show()
            }
        }
    }

    private fun configureMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.fragment_map_view_map_container) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(map: GoogleMap) {
        this.map = map.apply {
            setOnCameraMoveListener(this@MapViewFragment)
            setOnMarkerClickListener(this@MapViewFragment)
            isMyLocationEnabled = true
            uiSettings.isZoomControlsEnabled = true
        }
        zoomToUserLocation()
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation() {
        LocationServices.getFusedLocationProviderClient(requireActivity())
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

    override fun onCameraMove() {
        getAndShowPropertiesInMapBounds()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val propertyId = marker.tag as Long
        markerClickCallback.onMarkerClickCallback(propertyId)
        return true
    }

    interface MarkerClickCallback {
        fun onMarkerClickCallback(propertyId: Long)
    }
}