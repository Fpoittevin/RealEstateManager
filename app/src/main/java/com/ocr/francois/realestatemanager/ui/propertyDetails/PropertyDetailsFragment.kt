package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel
import kotlinx.android.synthetic.main.fragment_property_details.*

private const val PROPERTY_ID_KEY = "propertyId"

class PropertyDetailsFragment : Fragment() {

    private val propertyViewModel: PropertyViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val propertyId = requireArguments().getLong(PROPERTY_ID_KEY)
        propertyViewModel.getProperty(propertyId)
            .observe(viewLifecycleOwner, Observer { property -> updateUi(property) })
        return inflater.inflate(R.layout.fragment_property_details, container, false)
    }

    companion object {
        fun newInstance(propertyId: Long) =
            PropertyDetailsFragment().apply {
                arguments = Bundle().apply {
                    putLong(PROPERTY_ID_KEY, propertyId)
                }
            }
    }

    private fun updateUi(property: Property) {
        property.description?.let { fragment_property_details_description_text_view.text = it }

        property.surface?.let { fragment_property_details_surface_text_view.text = it.toString() }
        property.price?.let { fragment_property_details_price_text_view.text = it.toString() }

        fragment_property_details_number_of_rooms_text_view.text =
            getString(R.string.rooms_title_details_fragment, property.numberOfRooms)
        fragment_property_details_number_of_bathrooms_text_view.text =
            getString(R.string.bathrooms_title_details_fragment, property.numberOfBathrooms)
        fragment_property_details_number_of_bedrooms_text_view.text =
            getString(R.string.bedrooms_title_details_fragment, property.numberOfBedrooms)

        val addressBuilder = StringBuilder()
        property.addressFirst?.let { addressBuilder.append(it) }
        property.addressSecond?.let {
            addressBuilder
                .append("\n")
                .append(it)
        }
        property.district?.let {
            addressBuilder
                .append("\n")
                .append(it)
        }
        property.zipCode?.let {
            addressBuilder
                .append("\n")
                .append(it)
        }
        property.city?.let {
            addressBuilder
                .append(", ")
                .append(it)
        }
        property.state?.let {
            addressBuilder
                .append("\n")
                .append(it)
        }
        fragment_property_details_address_text_view.text = addressBuilder.toString()
    }
}