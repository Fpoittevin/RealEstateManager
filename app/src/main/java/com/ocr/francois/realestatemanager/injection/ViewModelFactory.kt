package com.ocr.francois.realestatemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.mapView.MapViewModel
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListViewModel
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsViewModel
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormViewModel

class ViewModelFactory(
    private val propertyRepository: PropertyRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertiesListViewModel::class.java)) {
            return PropertiesListViewModel(propertyRepository) as T
        } else if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
            return MapViewModel(propertyRepository) as T
        } else if (modelClass.isAssignableFrom(PropertyDetailsViewModel::class.java)) {
            return PropertyDetailsViewModel(propertyRepository) as T
        } else if (modelClass.isAssignableFrom(PropertyFormViewModel::class.java)) {
            return PropertyFormViewModel(propertyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}