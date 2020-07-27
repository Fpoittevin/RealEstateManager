package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel
import kotlinx.android.synthetic.main.fragment_property_creation.*
import kotlinx.android.synthetic.main.fragment_property_creation.view.*
import java.util.*

class PropertyCreationFragment : Fragment() {

    private val propertyViewModel: PropertyViewModel by activityViewModels {
        Injection.provideViewModelFactory(
            requireContext()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_property_creation, container, false)
        view.save_button.setOnClickListener { saveProperty(view) }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance() = PropertyCreationFragment()
    }

    private fun saveProperty(view: View) {

        val property = Property(
            null,
            view.fragment_property_creation_type_text_input.text.toString(),
            fragment_property_creation_price_text_input.text.toString().toIntOrNull(),
            fragment_property_creation_surface_text_input.text.toString().toFloatOrNull(),
            fragment_property_creation_number_of_rooms_text_input.text.toString().toIntOrNull(),
            fragment_property_creation_number_of_bathrooms_text_input.text.toString().toIntOrNull(),
            fragment_property_creation_number_of_bedrooms_text_input.text.toString().toIntOrNull(),
            fragment_property_creation_description_text_input.text.toString(),
            fragment_property_creation_address_text_input.text.toString(),
            fragment_property_creation_address_complements_text_input.text.toString(),
            fragment_property_creation_district_text_input.text.toString(),
            fragment_property_creation_city_text_input.text.toString(),
            fragment_property_creation_zip_code_text_input.text.toString(),
            fragment_property_creation_state_text_input.text.toString(),
            fragment_property_creation_points_of_interest_text_input.text.toString(),
            Date().time,
            null,
            fragment_property_creation_estate_agent_text_input.text.toString(),
            null,
            null
        )

        propertyViewModel.insertProperty(property)
        activity?.finish()
    }
}