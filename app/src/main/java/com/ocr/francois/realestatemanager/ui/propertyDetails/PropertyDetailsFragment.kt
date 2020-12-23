package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyDetailsBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryDetailsFragment
import com.ocr.francois.realestatemanager.utils.IsInternetAvailableLiveData

class PropertyDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var property: Property
    private lateinit var binding: FragmentPropertyDetailsBinding
    private lateinit var propertyModificationFabListener: PropertyModificationFabListener
    private val photosGalleryFragment = PhotosGalleryDetailsFragment.newInstance()

    private val propertyDetailsViewModel: PropertyDetailsViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    companion object {
        const val PROPERTY_ID_KEY = "PROPERTY_ID"

        fun newInstance(
            propertyId: Long,
            propertyModificationFabListener: PropertyModificationFabListener
        ) =
            PropertyDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(PROPERTY_ID_KEY, propertyId)
                }
                this.propertyModificationFabListener = propertyModificationFabListener
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_property_details,
                container,
                false
            )
        binding.apply {
            lifecycleOwner = this@PropertyDetailsFragment
            viewModel = propertyDetailsViewModel
        }

        arguments?.let {
            val propertyId = it.getLong(PROPERTY_ID_KEY)
            Log.e("id", it.getLong(PROPERTY_ID_KEY).toString())

            propertyDetailsViewModel.getPropertyWithPhotos(propertyId)
            binding.fragmentPropertyDetailsModificationFab.setOnClickListener {
                propertyModificationFabListener.onPropertyModificationClick(propertyId)
            }
        }
        propertyDetailsViewModel.propertyWithPhotos.observe(viewLifecycleOwner, {
            this.property = it.property
            configureMap()
        })
        configurePhotosGallery()

        return binding.root
    }

    private fun configurePhotosGallery() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_property_details_gallery_container, photosGalleryFragment)
            .commit()
    }

    private fun configureMap() {

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.fragment_property_details_map_container) as SupportMapFragment

        IsInternetAvailableLiveData(requireContext()).observe(viewLifecycleOwner, { isInternetAvailable ->
            isInternetAvailable?.let {
                if (isInternetAvailable) {
                    if (property.lat != null && property.lng != null) {
                        mapFragment.view?.visibility = View.VISIBLE
                        mapFragment.getMapAsync(this)
                    } else {
                        mapFragment.view?.visibility = View.GONE
                    }
                } else {
                    mapFragment.view?.visibility = View.GONE
                }
            }
        })

        if (property.lat != null && property.lng != null) {
            mapFragment.getMapAsync(this)
        } else {
            mapFragment.view?.visibility = View.GONE
        }
    }

    override fun onMapReady(map: GoogleMap) {

        val latLng = LatLng(property.lat!!, property.lng!!)
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng))
        map.moveCamera(CameraUpdateFactory.zoomTo(15F))

        val markerOptions = MarkerOptions().position(latLng)
        map.addMarker(markerOptions)
    }

    interface PropertyModificationFabListener {
        fun onPropertyModificationClick(propertyId: Long)
    }
}