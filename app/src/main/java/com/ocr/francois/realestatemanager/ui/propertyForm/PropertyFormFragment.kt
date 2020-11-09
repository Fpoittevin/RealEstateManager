package com.ocr.francois.realestatemanager.ui.propertyForm

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyFormBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.notification.NotificationSender
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFragment
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.CurrencyLiveData
import com.ocr.francois.realestatemanager.utils.ImageUtil
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate

class PropertyFormFragment : Fragment() {

    private lateinit var binding: FragmentPropertyFormBinding
    private lateinit var propertyWithPhotos: PropertyWithPhotos
    private val photosGalleryFragment = PhotosGalleryFragment.newInstance(true)
    private lateinit var formTarget: FormTarget
    private var errorInForm = false

    private val propertyFormViewModel: PropertyFormViewModel by activityViewModels {
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

        binding = FragmentPropertyFormBinding.inflate(inflater, container, false).apply {
            fragmentPropertyFormSaveFab.setOnClickListener { saveProperty() }
        }

        configurePhotosGallery()
        configureSoldSwitch()

        arguments?.let {
            it.getLong(PROPERTY_ID_KEY).let { propertyId ->
                formTarget = FormTarget.MODIFICATION
                Log.e("PROP ID", propertyId.toString())
                propertyFormViewModel.getPropertyWithPhotos(propertyId)
                    .observe(viewLifecycleOwner, { propertyWithPhotos ->
                        this.propertyWithPhotos = propertyWithPhotos

                        updateUi()
                    })
            }
        } ?: run {
            this.propertyWithPhotos = PropertyWithPhotos(
                Property(),
                mutableListOf()
            )
            formTarget = FormTarget.CREATION
        }

        return binding.root
    }

    private fun configureSoldSwitch() {

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year: Int, month: Int, dayOfMonth: Int ->
                val saleTimeStamp = Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                propertyWithPhotos.property.saleTimestamp = saleTimeStamp
                binding.fragmentPropertyFormSoldTextView.text =
                    Utils.formatDate(LocalDate(saleTimeStamp))
            },
            LocalDate.now().year,
            (LocalDate.now().monthOfYear - 1),
            LocalDate.now().dayOfMonth
        ).also {
            it.datePicker.maxDate = Utils.getTodayTimestamp()
        }

        binding.fragmentPropertyFormSoldSwitch.setOnCheckedChangeListener { _, value: Boolean ->
            if (value && propertyWithPhotos.property.saleTimestamp == null) {
                datePickerDialog.show()
            } else {
                propertyWithPhotos.property.saleTimestamp = null
                binding.fragmentPropertyFormSoldTextView.text = ""
            }
        }
    }


    private fun updateUi() {

        binding.run {
            propertyWithPhotos.property.run {
                saleTimestamp?.let {
                    fragmentPropertyFormSoldSwitch.isChecked = true
                    fragmentPropertyFormSoldTextView.text = Utils.formatDate(LocalDate(it))
                }
                type?.let {
                    fragmentPropertyFormTypeTextInput.setText(it)
                }
                formattedPrice?.let {
                    fragmentPropertyFormPriceTextInput.setText(it)
                }
                surface?.let {
                    fragmentPropertyFormSurfaceTextInput.setText(it.toString())
                }
                estateAgent?.let {
                    fragmentPropertyFormEstateAgentTextInput.setText(estateAgent)
                }
                description?.let {
                    fragmentPropertyFormDescriptionTextInput.setText(description)
                }

                numberOfRooms?.let {
                    fragmentPropertyFormNumberOfRoomsTextInput.setText(it.toString())
                }
                numberOfBathrooms?.let {
                    fragmentPropertyFormNumberOfBathroomsTextInput.setText(it.toString())
                }
                numberOfBedrooms?.let {
                    fragmentPropertyFormNumberOfBedroomsTextInput.setText(it.toString())
                }

                addressFirst?.let {
                    fragmentPropertyFormAddressFirstTextInput.setText(it)
                }
                addressSecond?.let {
                    fragmentPropertyFormAddressSecondTextInput.setText(it)
                }
                city?.let {
                    fragmentPropertyFormCityTextInput.setText(it)
                }
                zipCode?.let {
                    fragmentPropertyFormZipCodeTextInput.setText(it)
                }
                fragmentPropertyFormNearSchoolSwitch.isChecked = nearSchool
                fragmentPropertyFormNearTransportsSwitch.isChecked = nearTransports
                fragmentPropertyFormNearShopsSwitch.isChecked = nearShops
                fragmentPropertyFormNearParksSwitch.isChecked = nearParks
            }
        }

        photosGalleryFragment.updateList(propertyWithPhotos.photosList)
    }

    private fun configurePhotosGallery() {

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
        propertyWithPhotos.property.apply {
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
                surface = it.toInt()
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
            val photosList = photosGalleryFragment.getPhotosList()
            if (photosList.isEmpty()) {
                errorInForm = true
            } else {
                for (photo in photosList) {
                    if (photo.description.isNullOrEmpty()) {
                        errorInForm = true
                        Toast.makeText(requireContext(), "need desc", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    private fun saveProperty() {

        checkDataAndHydrateProperty()

        if (!errorInForm) {
            propertyWithPhotos.apply {
                photosList.apply {
                    forEach {
                        if (!photosGalleryFragment.getPhotosList().contains(it)) {
                            ImageUtil.deleteFileFromUri(Uri.parse(it.uri))
                        }
                    }
                    clear()
                    addAll(photosGalleryFragment.getPhotosList())
                }
            }

            val sharedPreferences = requireContext().getSharedPreferences(
                getString(R.string.preference_file_key),
                Context.MODE_PRIVATE
            )

            sharedPreferences.getString(CurrencyLiveData.CURRENCY_KEY, Currency.DOLLAR.name)?.let {
                Currency.valueOf(
                    it
                )
            }?.let {
                if (it == Currency.EURO) {
                    propertyWithPhotos.property.price?.let { price ->
                        price.apply {
                            Utils.convertEuroToDollar(price)
                        }
                    }
                }
            }

            when (formTarget) {
                FormTarget.CREATION -> {
                    propertyWithPhotos.property.creationTimestamp = Utils.getTodayTimestamp()
                    propertyFormViewModel.createPropertyWithPhotos(
                        propertyWithPhotos
                    )
                    NotificationSender().sendNotification(requireContext())
                }
                FormTarget.MODIFICATION -> propertyFormViewModel.updatePropertyWithPhotos(
                    propertyWithPhotos
                )
            }
            activity?.finish()
        } else {
            errorInForm = false
        }
    }
}