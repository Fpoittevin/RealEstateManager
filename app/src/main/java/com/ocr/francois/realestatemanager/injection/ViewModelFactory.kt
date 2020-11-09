package com.ocr.francois.realestatemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.mapView.MapViewModel
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListViewModel
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsViewModel
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormViewModel
import com.ocr.francois.realestatemanager.ui.settings.SettingsViewModel

class ViewModelFactory(
    private val propertyRepository: PropertyRepository,
    private val currencyRepository: CurrencyRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(PropertiesListViewModel::class.java) -> {
                PropertiesListViewModel(propertyRepository, currencyRepository) as T
            }
            modelClass.isAssignableFrom(MapViewModel::class.java) -> {
                MapViewModel(propertyRepository) as T
            }
            modelClass.isAssignableFrom(PropertyDetailsViewModel::class.java) -> {
                PropertyDetailsViewModel(propertyRepository, currencyRepository) as T
            }
            modelClass.isAssignableFrom(PropertyFormViewModel::class.java) -> {
                PropertyFormViewModel(propertyRepository, currencyRepository) as T
            }
            modelClass.isAssignableFrom(SettingsViewModel::class.java) -> {
                SettingsViewModel(currencyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}