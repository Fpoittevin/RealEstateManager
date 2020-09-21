package com.ocr.francois.realestatemanager.ui.propertyForm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyFormBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFragment
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel

class PropertyFormFragment : Fragment() {

    private lateinit var binding: FragmentPropertyFormBinding
    private lateinit var property: Property
    private lateinit var photosList: MutableList<Photo>
    private lateinit var formTarget: FormTarget
    private var errorInForm = false

    private val propertyViewModel: PropertyViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    enum class FormTarget {
        CREATION, MODIFICATION
    }

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"

        fun newInstance(propertyId: Long? = null) =
            PropertyFormFragment().apply {
                propertyId?.let {
                    arguments = Bundle().apply {
                        putLong(PROPERTY_ID_KEY, propertyId)
                    }
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPropertyFormBinding.inflate(inflater, container, false).also {
            it.fragmentPropertyFormSaveFab.setOnClickListener { saveProperty() }
        }

        arguments?.let {
            it.getLong(PROPERTY_ID_KEY).let { propertyId ->
                propertyViewModel.getProperty(propertyId)
                    .observe(viewLifecycleOwner, { property ->
                        this.property = property
                        updateUi()
                    })
            }
            val propertyId = it.getLong(PROPERTY_ID_KEY)
            propertyViewModel.getProperty(propertyId)
                .observe(viewLifecycleOwner, { property ->
                    this.property = property
                    formTarget = FormTarget.MODIFICATION
                    updateUi()
                })
        } ?: run {
            property = Property()
            formTarget = FormTarget.CREATION
        }

        return binding.root
    }

    private fun updateUi() {

        binding.run {
            property.type?.let {
                fragmentPropertyFormTypeTextInput.setText(it)
            }
            property.price?.let {
                fragmentPropertyFormPriceTextInput.setText(it.toString())
            }
            property.surface?.let {
                fragmentPropertyFormSurfaceTextInput.setText(it.toString())
            }
            property.estateAgent?.let {
                fragmentPropertyFormEstateAgentTextInput.setText(property.estateAgent)
            }
            property.description?.let {
                fragmentPropertyFormDescriptionTextInput.setText(property.description)
            }

            property.numberOfRooms?.let {
                fragmentPropertyFormNumberOfRoomsTextInput.setText(it.toString())
            }
            property.numberOfBathrooms?.let {
                fragmentPropertyFormNumberOfBathroomsTextInput.setText(it.toString())
            }
            property.numberOfBedrooms?.let {
                fragmentPropertyFormNumberOfBedroomsTextInput.setText(it.toString())
            }

            property.addressFirst?.let {
                fragmentPropertyFormAddressFirstTextInput.setText(it)
            }
            property.addressSecond?.let {
                fragmentPropertyFormAddressSecondTextInput.setText(it)
            }
            property.city?.let {
                fragmentPropertyFormCityTextInput.setText(it)
            }
            property.zipCode?.let {
                fragmentPropertyFormZipCodeTextInput.setText(it)
            }
            fragmentPropertyFormNearSchoolSwitch.isChecked = property.nearSchool
            fragmentPropertyFormNearTransportsSwitch.isChecked = property.nearTransports
            fragmentPropertyFormNearShopsSwitch.isChecked = property.nearShops
            fragmentPropertyFormNearParksSwitch.isChecked = property.nearParks
        }

        configurePhotosGallery()
    }

    private fun configurePhotosGallery() {
        val photosGalleryFragment = PhotosGalleryFragment.newInstance(true, property.id)
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_property_form_gallery_container, photosGalleryFragment)
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
            checkTextInputValue(binding.fragmentPropertyFormTypeTextInput)?.let {
                type = it
            } ?: run {
                binding.fragmentPropertyFormTypeTextInputLayout.error =
                    getString(R.string.required_field)
                errorInForm = true
            }

            //  PRICE
            checkTextInputValue(binding.fragmentPropertyFormPriceTextInput)?.let {
                price = it.toInt()
            } ?: run {
                binding.fragmentPropertyFormPriceTextInputLayout.error =
                    getString(R.string.required_field)
                errorInForm = true
            }

            //  SURFACE
            checkTextInputValue(binding.fragmentPropertyFormSurfaceTextInput)?.let {
                surface = it.toFloat()
            } ?: run {
                binding.fragmentPropertyFormSurfaceTextInputLayout.error =
                    getString(R.string.required_field)
                errorInForm = true
            }

            //  ROOMS / BATHROOMS / BEDROOMS
            numberOfRooms =
                binding.fragmentPropertyFormNumberOfRoomsTextInput.text.toString()
                    .toIntOrNull()
            numberOfBathrooms =
                binding.fragmentPropertyFormNumberOfBathroomsTextInput.text.toString()
                    .toIntOrNull()
            numberOfBedrooms =
                binding.fragmentPropertyFormNumberOfBedroomsTextInput.text.toString()
                    .toIntOrNull()

            //  DESCRIPTION
            description = binding.fragmentPropertyFormDescriptionTextInput.text.toString()

            //  ADDRESS
            checkTextInputValue(binding.fragmentPropertyFormAddressFirstTextInput)?.let {
                addressFirst = it
            } ?: run {
                binding.fragmentPropertyFormAddressFirstTextInputLayout.error =
                    getString(R.string.required_field)
                errorInForm = true
            }

            //  ADDRESS SECOND
            addressSecond =
                binding.fragmentPropertyFormAddressSecondTextInput.text.toString()

            // CITY
            checkTextInputValue(binding.fragmentPropertyFormCityTextInput)?.let {
                city = it
            } ?: run {
                binding.fragmentPropertyFormCityTextInputLayout.error =
                    getString(R.string.required_field)
                errorInForm = true
            }

            //  ZIP CODE
            checkTextInputValue(binding.fragmentPropertyFormZipCodeTextInput)?.let {
                zipCode = it
            } ?: run {
                binding.fragmentPropertyFormZipCodeTextInputLayout.error =
                    getString(R.string.required_field)
                errorInForm = true
            }

            //  ESTATE AGENT
            estateAgent = binding.fragmentPropertyFormEstateAgentTextInput.text.toString()

            //  POINTS OF INTEREST
            nearSchool = binding.fragmentPropertyFormNearSchoolSwitch.isChecked
            nearTransports = binding.fragmentPropertyFormNearTransportsSwitch.isChecked
            nearSchool = binding.fragmentPropertyFormNearShopsSwitch.isChecked
            nearParks = binding.fragmentPropertyFormNearParksSwitch.isChecked

            //  PHOTOS
            if (photosList.size == 0) {
                errorInForm = true
            }
        }
    }

    private fun saveProperty() {

        checkDataAndHydrateProperty()

        if (!errorInForm) {
            when (formTarget) {
                FormTarget.CREATION -> propertyViewModel.createProperty(property, photosList)
                FormTarget.MODIFICATION -> propertyViewModel.updateProperty(property)
            }
            activity?.finish()
        } else {
            errorInForm = false
        }
    }
}