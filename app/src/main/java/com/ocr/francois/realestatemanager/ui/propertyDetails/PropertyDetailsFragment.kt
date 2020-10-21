package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFragment
import com.ocr.francois.realestatemanager.utils.LocationTool
import kotlinx.android.synthetic.main.fragment_property_details.*

class PropertyDetailsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var property: Property
    private lateinit var propertyWithPhotos: PropertyWithPhotos
    private lateinit var binding: FragmentPropertyDetailsBinding
    private lateinit var propertyModificationFabListener: PropertyModificationFabListener
    private val photosGalleryFragment = PhotosGalleryFragment.newInstance(false)

    private val propertyDetailsViewModel: PropertyDetailsViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //TODO: change findViewById with ViewBinding

        binding = FragmentPropertyDetailsBinding.inflate(inflater, container, false)

        configurePhotosGallery()

        arguments?.let {
            val propertyId = it.getLong(PROPERTY_ID_KEY)

            propertyDetailsViewModel.getPropertyWithPhotos(propertyId)
                .observe(viewLifecycleOwner, { propertyWithPhotos ->
                    this.propertyWithPhotos = propertyWithPhotos
                    Log.e("PROPERTY: ", propertyWithPhotos.toString())
                    updateUi()
                })
            binding.fragmentPropertyDetailsModificationFab.setOnClickListener {
                propertyModificationFabListener.onPropertyModificationClick(propertyId)
            }
        }

        return binding.root
    }

    private fun configurePhotosGallery() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_property_details_gallery_container, photosGalleryFragment)
            .commit()
    }

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"

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

    private fun updateUi() {
        this.property = propertyWithPhotos.property

        property.description?.let { fragment_property_details_description_text_view.text = it }

        property.surface?.let {
            fragment_property_details_surface_text_view.text = StringBuilder()
                .append(it.toString())
                .append(" m²")
                .toString()
        }
        property.price?.let { fragment_property_details_price_text_view.text = it.toString() }

        fragment_property_details_number_of_rooms_text_view.text =

            getString(R.string.rooms_title_details_fragment, property.numberOfRooms)
        fragment_property_details_number_of_bathrooms_text_view.text =
            getString(R.string.bathrooms_title_details_fragment, property.numberOfBathrooms)
        fragment_property_details_number_of_bedrooms_text_view.text =
            getString(R.string.bedrooms_title_details_fragment, property.numberOfBedrooms)

        fragment_property_details_address_text_view.text =
            LocationTool.addressConcatenation(property, true)

        photosGalleryFragment.updateList(propertyWithPhotos.photosList)
        configureMap()
    }

    private fun configureMap() {
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.fragment_property_details_map_container) as SupportMapFragment

        if (property.lat != null && property.lng != null) {
            mapFragment.getMapAsync(this)
        } else {
            mapFragment.view?.visibility = View.INVISIBLE
        }
    }

    override fun onMapReady(map: GoogleMap) {

        //TODO: enlever le fragment map si pas de connexion

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