package com.ocr.francois.realestatemanager.ui.propertyModification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyModificationBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFragment
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel

class PropertyModificationFragment : Fragment() {

    private lateinit var binding: FragmentPropertyModificationBinding
    private lateinit var property: Property
    private var errorInForm = false

    private val propertyViewModel: PropertyViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPropertyModificationBinding.inflate(inflater, container, false).also {
            it.fragmentPropertyModificationSaveFab.setOnClickListener { saveProperty() }
        }

        arguments?.let {
            val propertyId = it.getLong(PROPERTY_ID_KEY)
            propertyViewModel.getProperty(propertyId)
                .observe(viewLifecycleOwner, { property ->
                    this.property = property
                    updateUi()
                })
        }

        return binding.root
    }

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"

        fun newInstance(propertyId: Long) =
            PropertyModificationFragment().apply {
                arguments = Bundle().apply {
                    putLong(PROPERTY_ID_KEY, propertyId)
                }
            }
    }

    private fun updateUi() {

        binding.run {
            property.type?.let {
                fragmentPropertyModificationTypeTextInput.setText(it)
            }
            property.price?.let {
                fragmentPropertyModificationPriceTextInput.setText(it.toString())
            }
            property.surface?.let {
                fragmentPropertyModificationSurfaceTextInput.setText(it.toString())
            }
            property.estateAgent?.let {
                fragmentPropertyModificationEstateAgentTextInput.setText(property.estateAgent)
            }
            property.description?.let {
                fragmentPropertyModificationDescriptionTextInput.setText(property.description)
            }

            property.numberOfRooms?.let {
                fragmentPropertyModificationNumberOfRoomsTextInput.setText(it.toString())
            }
            property.numberOfBathrooms?.let {
                fragmentPropertyModificationNumberOfBathroomsTextInput.setText(it.toString())
            }
            property.numberOfBedrooms?.let {
                fragmentPropertyModificationNumberOfBedroomsTextInput.setText(it.toString())
            }

            property.addressFirst?.let {
                fragmentPropertyModificationAddressTextInput.setText(it)
            }
            property.addressSecond?.let {
                fragmentPropertyModificationAddressComplementsTextInput.setText(it)
            }
            property.city?.let {
                fragmentPropertyModificationCityTextInput.setText(it)
            }
            property.zipCode?.let {
                fragmentPropertyModificationZipCodeTextInput.setText(it)
            }
            fragmentPropertyModificationNearSchoolSwitch.isChecked = property.nearSchool
            fragmentPropertyModificationNearTransportsSwitch.isChecked = property.nearTransports
            fragmentPropertyModificationNearShopsSwitch.isChecked = property.nearShops
            fragmentPropertyModificationNearParksSwitch.isChecked = property.nearParks
        }

        configurePhotosGallery()
    }

    private fun configurePhotosGallery() {
        val photosGalleryFragment = PhotosGalleryFragment.newInstance(true, property.id)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_property_modification_gallery_container, photosGalleryFragment)
            .commit()
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
            checkTextInputValue(binding.fragmentPropertyModificationTypeTextInput)?.let {
                type = it
            } ?: run {
                binding.fragmentPropertyModificationTypeTextInputLayout.error = "this field is required"
                errorInForm = true
            }

            //  PRICE
            checkTextInputValue(binding.fragmentPropertyModificationPriceTextInput)?.let {
                price = it.toInt()
            } ?: run {
                binding.fragmentPropertyModificationPriceTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  SURFACE
            checkTextInputValue(binding.fragmentPropertyModificationSurfaceTextInput)?.let {
                surface = it.toFloat()
            } ?: run {
                binding.fragmentPropertyModificationSurfaceTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  ROOMS / BATHROOMS / BEDROOMS
            numberOfRooms =
                binding.fragmentPropertyModificationNumberOfRoomsTextInput.text.toString()
                    .toIntOrNull()
            numberOfBathrooms =
                binding.fragmentPropertyModificationNumberOfBathroomsTextInput.text.toString()
                    .toIntOrNull()
            numberOfBedrooms =
                binding.fragmentPropertyModificationNumberOfBedroomsTextInput.text.toString()
                    .toIntOrNull()

            //  DESCRIPTION
            description = binding.fragmentPropertyModificationDescriptionTextInput.text.toString()

            //  ADDRESS
            checkTextInputValue(binding.fragmentPropertyModificationAddressTextInput)?.let {
                addressFirst = it
            } ?: run {
                binding.fragmentPropertyModificationAddressTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  ADDRESS SECOND
            addressSecond =
                binding.fragmentPropertyModificationAddressComplementsTextInput.text.toString()

            // CITY
            checkTextInputValue(binding.fragmentPropertyModificationCityTextInput)?.let {
                city = it
            } ?: run {
                binding.fragmentPropertyModificationCityTextInputLayout.error = "this field is required"
                errorInForm = true
            }

            //  ZIP CODE
            checkTextInputValue(binding.fragmentPropertyModificationZipCodeTextInput)?.let {
                zipCode = it
            } ?: run {
                binding.fragmentPropertyModificationZipCodeTextInputLayout.error =
                    "this field is required"
                errorInForm = true
            }

            //  ESTATE AGENT
            estateAgent = binding.fragmentPropertyModificationEstateAgentTextInput.text.toString()

            //  POINTS OF INTEREST
            nearSchool = binding.fragmentPropertyModificationNearSchoolSwitch.isChecked
            nearTransports = binding.fragmentPropertyModificationNearTransportsSwitch.isChecked
            nearSchool = binding.fragmentPropertyModificationNearShopsSwitch.isChecked
            nearParks = binding.fragmentPropertyModificationNearParksSwitch.isChecked


        }
    }

    private fun saveProperty() {

        checkDataAndHydrateProperty()

        if (!errorInForm) {
            propertyViewModel.updateProperty(property)
            activity?.finish()
        } else {
            errorInForm = false
        }
    }
}