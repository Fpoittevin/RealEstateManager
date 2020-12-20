package com.ocr.francois.realestatemanager.ui.propertyForm

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.switchmaterial.SwitchMaterial
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.FragmentPropertyFormBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.notification.NotificationSender
import com.ocr.francois.realestatemanager.ui.photosGallery.PhotosGalleryFormFragment
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate


class PropertyFormFragment : Fragment() {

    private lateinit var binding: FragmentPropertyFormBinding
    private lateinit var formTarget: FormTarget
    private val photosGalleryFragment =
        PhotosGalleryFormFragment.newInstance()
    val propertyFormViewModel: PropertyFormViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    enum class FormTarget {
        CREATION, MODIFICATION
    }

    companion object {
        const val PROPERTY_ID_KEY = "PROPERTY_ID"

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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            it.getLong(PROPERTY_ID_KEY).let { id ->
                propertyFormViewModel.getPropertyWithPhotos(id)
                formTarget = FormTarget.MODIFICATION
            }
        } ?: run {
            formTarget = FormTarget.CREATION
        }

        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_property_form,
                container,
                false
            )
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = propertyFormViewModel
        }

        configurePhotosGallery()
        configureSoldSwitch()
        configureSaveButton()

        return binding.root
    }

    private fun configurePhotosGallery() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_property_form_gallery_container, photosGalleryFragment)
            .commit()
    }

    private fun configureSoldSwitch() {

        if (formTarget == FormTarget.MODIFICATION) {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year: Int, month: Int, dayOfMonth: Int ->
                    val saleTimeStamp = Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                    propertyFormViewModel.onSaleTimeStampChange(saleTimeStamp)
                },
                LocalDate.now().year,
                (LocalDate.now().monthOfYear - 1),
                LocalDate.now().dayOfMonth
            ).also {
                it.datePicker.maxDate = Utils.getTodayTimestamp()
            }.also {
                it.setOnCancelListener {
                    binding.fragmentPropertyFormSoldSwitch.isChecked = false
                }
            }

            binding.fragmentPropertyFormSoldSwitch.setOnClickListener {
                val switch = it as SwitchMaterial
                if (switch.isChecked) {
                    datePickerDialog.show()
                } else {
                    propertyFormViewModel.onSaleTimeStampChange(null)
                }
            }
        } else {
            binding.fragmentPropertyFormSoldDateContainer.visibility = View.GONE
        }
    }

    private fun configureSaveButton() {
        binding.fragmentPropertyFormSaveFab.setOnClickListener {
            Log.e("price : ", propertyFormViewModel.propertyWithPhotosLiveData.value?.property?.price.toString())
            when (formTarget) {
                FormTarget.CREATION -> {
                    propertyFormViewModel.createPropertyWithPhotos()
                    NotificationSender().sendNotification(requireContext())
                }
                FormTarget.MODIFICATION ->
                    propertyFormViewModel.updatePropertyWithPhotos()
            }
            activity?.finish()
        }
    }
}