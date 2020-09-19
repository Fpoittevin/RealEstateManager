package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyCreationBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFragment
import com.ocr.francois.realestatemanager.viewmodels.PhotosGalleryViewModel
import com.ocr.francois.realestatemanager.viewmodels.PropertyCreationViewModel

class PropertyCreationFragment : Fragment() {

    private lateinit var binding: FragmentPropertyCreationBinding
    private lateinit var photosList: MutableList<Photo>
    private val property = Property()
    private var errorInForm = false

    // TODO: REMOVE ERROR MESSAGE WHEN TEXT CHANGE
    private val propertyCreationViewModel: PropertyCreationViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    private val photoViewModel: PhotosGalleryViewModel by activityViewModels {
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

        photoViewModel.getPhotosListLiveData(null).observe(viewLifecycleOwner, {
            photosList = it
        })
    }

    private fun checkTextInputValue(textInput: TextInputEditText): String? {
        return if (textInput.text.isNullOrEmpty()) {
            null
        } else {
            textInput.text.toString()
        }
    }

    private fun checkDataAndHydrateProperty() {
        property.apply {
            // TYPE
            checkTextInputValue(binding.fragmentPropertyCreationTypeTextInput)?.let {
                type = it
            } ?: run {
                binding.fragmentPropertyCreationTypeTextInputLayout.error = "this field is required"
                errorInForm = true
            }

            //  PRICE
            checkTextInputValue(binding.fragmentPropertyCreationPriceTextInput)?.let {
                price = it.toInt()
            } ?: run {
                binding.fragmentPropertyCreationPriceTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  SURFACE
            checkTextInputValue(binding.fragmentPropertyCreationSurfaceTextInput)?.let {
                surface = it.toFloat()
            } ?: run {
                binding.fragmentPropertyCreationSurfaceTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  ROOMS / BATHROOMS / BEDROOMS
            numberOfRooms =
                binding.fragmentPropertyCreationNumberOfRoomsTextInput.text.toString()
                    .toIntOrNull()
            numberOfBathrooms =
                binding.fragmentPropertyCreationNumberOfBathroomsTextInput.text.toString()
                    .toIntOrNull()
            numberOfBedrooms =
                binding.fragmentPropertyCreationNumberOfBedroomsTextInput.text.toString()
                    .toIntOrNull()

            //  DESCRIPTION
            description = binding.fragmentPropertyCreationDescriptionTextInput.text.toString()

            //  ADDRESS
            checkTextInputValue(binding.fragmentPropertyCreationAddressTextInput)?.let {
                addressFirst = it
            } ?: run {
                binding.fragmentPropertyCreationAddressTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  ADDRESS SECOND
            addressSecond =
                binding.fragmentPropertyCreationAddressComplementsTextInput.text.toString()

            // CITY
            checkTextInputValue(binding.fragmentPropertyCreationCityTextInput)?.let {
                city = it
            } ?: run {
                binding.fragmentPropertyCreationCityTextInputLayout.error = "this field is required"
                errorInForm = true
            }

            //  ZIP CODE
            checkTextInputValue(binding.fragmentPropertyCreationZipCodeTextInput)?.let {
                zipCode = it
            } ?: run {
                binding.fragmentPropertyCreationZipCodeTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  ESTATE AGENT
            estateAgent = binding.fragmentPropertyCreationEstateAgentTextInput.text.toString()

            //  POINTS OF INTEREST
            nearSchool = binding.fragmentPropertyCreationNearSchoolSwitch.isChecked
            nearTransports = binding.fragmentPropertyCreationNearTransportsSwitch.isChecked
            nearSchool = binding.fragmentPropertyCreationNearShopsSwitch.isChecked
            nearParks = binding.fragmentPropertyCreationNearParksSwitch.isChecked

            //  PHOTOS
            if (photosList.size == 0) {
                errorInForm = true
            }
        }
    }

    private fun saveProperty() {

        checkDataAndHydrateProperty()

        if (!errorInForm) {
            propertyCreationViewModel.saveProperty(property, photosList)
            activity?.finish()
        } else {
            errorInForm = false
        }
    }
}