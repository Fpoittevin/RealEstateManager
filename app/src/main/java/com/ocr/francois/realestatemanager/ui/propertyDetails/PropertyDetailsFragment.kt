package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.os.Bundle
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
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate

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

        binding = FragmentPropertyDetailsBinding.inflate(inflater, container, false)

        configurePhotosGallery()

        arguments?.let {
            val propertyId = it.getLong(PROPERTY_ID_KEY)

            propertyDetailsViewModel.getPropertyWithPhotos(propertyId)
                .observe(viewLifecycleOwner, { propertyWithPhotos ->
                    this.propertyWithPhotos = propertyWithPhotos
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

    private fun updateUi() {
        this.property = propertyWithPhotos.property


        binding.apply {
            property.saleTimestamp?.let {
                fragmentPropertyFormDetailsIsSoldTextView.text = getString(R.string.sold)
                fragmentPropertyFormDetailsSoldDateTextView.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                binding.apply {
                    fragmentPropertyFormDetailsIsSoldTextView.visibility = View.GONE
                    fragmentPropertyFormDetailsSoldDateTextView.visibility = View.GONE
                }
            }
        }

        property.description?.let {
            binding.fragmentPropertyDetailsDescriptionTextView.text = it
        }

        property.surface?.let {
            binding.fragmentPropertyDetailsSurfaceTextView.text = StringBuilder()
                .append(it.toString())
                .append(" m²")
                .toString()
        }
        property.formattedPrice?.let {
            binding.fragmentPropertyDetailsPriceTextView.text =
                it
        }
        property.estateAgent?.let {
            binding.fragmentPropertyDetailsEstateAgentTextView.text = it
        }

        binding.fragmentPropertyDetailsNumberOfRoomsTextView.text =
            getString(R.string.rooms_with_number, property.numberOfRooms)
        binding.fragmentPropertyDetailsNumberOfBathroomsTextView.text =
            getString(R.string.bathrooms_with_number, property.numberOfBathrooms)
        binding.fragmentPropertyDetailsNumberOfBedroomsTextView.text =
            getString(R.string.bedrooms_with_number, property.numberOfBedrooms)

        binding.fragmentPropertyDetailsAddressTextView.text =
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
            val viewGroup = binding.fragmentPropertyDetailsMapContainer.parent as ViewGroup
            viewGroup.removeView(binding.fragmentPropertyDetailsMapContainer)
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