package com.ocr.francois.realestatemanager.ui.mapView

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
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
import com.ocr.francois.realestatemanager.utils.IsInternetAvailableLiveData
import pub.devrel.easypermissions.EasyPermissions

class MapViewFragment(
    private val mapCallback: MapCallback
) : Fragment(),
    OnMapReadyCallback,
    GoogleMap.OnCameraMoveListener,
    GoogleMap.OnMarkerClickListener {

    private lateinit var binding: FragmentMapViewBinding
    private lateinit var map: GoogleMap
    private lateinit var isLocationAvailableLiveData: MutableLiveData<Boolean?>
    private lateinit var isInternetAvailableLiveData: IsInternetAvailableLiveData
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var canDisplayMap: MediatorLiveData<Boolean>

    private val mapViewModel: MapViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    companion object {

        const val LOCATION_REQUEST_INTERVAL = 1000

        fun newInstance(mapCallback: MapCallback) =
            MapViewFragment(mapCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map_view, container, false)
        binding.apply {
            lifecycleOwner = this@MapViewFragment
        }

        canDisplayMap.observe(viewLifecycleOwner, {
            if (it) {
                configureMap()
                zoomToUserLocation()
            }
        })
        checkLocationPermissions()

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
        isLocationAvailableLiveData = MutableLiveData<Boolean?>(null)
        isInternetAvailableLiveData = IsInternetAvailableLiveData(context)
        canDisplayMap = MediatorLiveData<Boolean>().apply {
            value = false
            addSource(isLocationAvailableLiveData) {
                it?.let { isLocationAvailable ->
                    isInternetAvailableLiveData.value?.let { isInternetAvailable ->
                        value = checkLocationAndInternet(isLocationAvailable, isInternetAvailable)
                    }
                }
            }
            addSource(isInternetAvailableLiveData) {
                it?.let { isInternetAvailable ->
                    isLocationAvailableLiveData.value?.let { isLocationAvailable ->
                        value = checkLocationAndInternet(isLocationAvailable, isInternetAvailable)
                    }
                }
            }
        }
    }

    private fun checkLocationAndInternet(
        isLocationAvailable: Boolean,
        isInternetAvailable: Boolean
    ): Boolean {

        if (isInternetAvailable) {
            if (isLocationAvailable) {
                return true
            }
        } else {
            MaterialAlertDialogBuilder(requireContext()).apply {
                setTitle(R.string.network_dialog_title)
                setMessage(R.string.network_required)

                setNegativeButton(
                    resources.getString(R.string.ok)
                ) { _, _ ->
                    mapCallback.onCancelMapCallback()
                }
                show()
            }
        }

        return false
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

    fun checkLocationIsEnabled() {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isLocationAvailableLiveData.value = true
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
                    mapCallback.onCancelMapCallback()
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
    }

    @SuppressLint("MissingPermission")
    private fun zoomToUserLocation() {

        fusedLocationProviderClient
            .lastLocation.addOnSuccessListener { locationResult ->

                locationResult?.let {

                    val userLocation = LatLng(it.latitude, it.longitude)
                    val cameraPosition = CameraPosition.Builder()
                        .target(userLocation).zoom(15F).build()
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
                } ?: run {
                    val locationRequest = LocationRequest()
                        .setPriority(PRIORITY_HIGH_ACCURACY)
                        .setInterval(LOCATION_REQUEST_INTERVAL.toLong())

                    fusedLocationProviderClient.requestLocationUpdates(
                        locationRequest,
                        object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult) {
                                super.onLocationResult(locationResult)
                                zoomToUserLocation()
                                fusedLocationProviderClient.removeLocationUpdates(this)
                            }
                        },
                        Looper.getMainLooper()
                    )
                }
            }
    }

    private fun getAndShowPropertiesInMapBounds() {
        map.projection.visibleRegion.latLngBounds?.let {
            mapViewModel.getPropertiesInBounds(it)
                .observe(this, { properties ->
                    for (property in properties) {
                        addMarker(property.id!!, property.lat!!, property.lng!!)
                    }
                })
        }
    }

    private fun addMarker(id: Long, lat: Double, lng: Double) {
        val latLng = LatLng(lat, lng)
        val markerOptions = MarkerOptions().position(latLng)
        val marker = map.addMarker(markerOptions)
        marker?.tag = id
    }

    override fun onCameraMove() {
        getAndShowPropertiesInMapBounds()
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val propertyId = marker.tag as Long
        mapCallback.onMarkerClickCallback(propertyId)
        return true
    }

    interface MapCallback {
        fun onMarkerClickCallback(propertyId: Long)
        fun onCancelMapCallback()
    }
}