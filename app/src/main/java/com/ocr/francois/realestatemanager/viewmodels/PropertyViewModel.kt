package com.ocr.francois.realestatemanager.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    fun getPropertiesInBounds(bounds: LatLngBounds) =
        propertyRepository.getPropertiesInBounds(bounds)

    fun createPropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) {
        viewModelScope.launch(Dispatchers.IO) {
            propertyRepository.insertPropertyWithPhotos(propertyWithPhotos)
        }
    }

    fun updatePropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) {
        viewModelScope.launch(Dispatchers.IO) {
            propertyWithPhotos.photosList.forEach {
                it.propertyId = propertyWithPhotos.property.id
            }
            propertyRepository.updatePropertyWithPhotos(propertyWithPhotos)
        }
    }

    fun getPropertiesWithPhotos(propertySearch: PropertySearch?) = propertyRepository.getPropertiesWithPhotos(propertySearch)

    fun getPropertyWithPhotos(id: Long) = propertyRepository.getPropertyWithPhotos(id)
}