package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyCreationBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFragment
import com.ocr.francois.realestatemanager.viewmodels.PropertyCreationViewModel
import java.util.*

class PropertyCreationFragment : Fragment() {

    private lateinit var binding: FragmentPropertyCreationBinding

    private val propertyCreationViewModel: PropertyCreationViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    companion object {
        @JvmStatic
        fun newInstance() = PropertyCreationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPropertyCreationBinding.inflate(inflater, container, false)
            .also {
                it.saveButton.setOnClickListener { saveProperty() }
            }
        configurePhotosGallery()

        return binding.root
    }

    private fun configurePhotosGallery() {
        val photosGalleryFragment = PhotosGalleryFragment.newInstance(true, null)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_property_creation_gallery_container, photosGalleryFragment)
            .commit()
    }

    private fun saveProperty() {

        val property = Property(
            null,
            binding.fragmentPropertyCreationTypeTextInput.text.toString(),
            binding.fragmentPropertyCreationPriceTextInput.text.toString().toIntOrNull(),
            binding.fragmentPropertyCreationSurfaceTextInput.text.toString().toFloatOrNull(),
            binding.fragmentPropertyCreationNumberOfRoomsTextInput.text.toString().toIntOrNull(),
            binding.fragmentPropertyCreationNumberOfBathroomsTextInput.text.toString()
                .toIntOrNull(),
            binding.fragmentPropertyCreationNumberOfBedroomsTextInput.text.toString().toIntOrNull(),
            binding.fragmentPropertyCreationDescriptionTextInput.text.toString(),
            binding.fragmentPropertyCreationAddressTextInput.text.toString(),
            binding.fragmentPropertyCreationAddressComplementsTextInput.text.toString(),
            binding.fragmentPropertyCreationDistrictTextInput.text.toString(),
            binding.fragmentPropertyCreationCityTextInput.text.toString(),
            binding.fragmentPropertyCreationZipCodeTextInput.text.toString(),
            binding.fragmentPropertyCreationStateTextInput.text.toString(),
            binding.fragmentPropertyCreationPointsOfInterestTextInput.text.toString(),
            Date().time,
            null,
            binding.fragmentPropertyCreationEstateAgentTextInput.text.toString(),
            null,
            null
        )

        propertyCreationViewModel.saveProperty(property)
        activity?.finish()
    }
}