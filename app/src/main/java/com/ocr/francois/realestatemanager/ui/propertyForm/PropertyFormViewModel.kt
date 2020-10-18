package com.ocr.francois.realestatemanager.ui.propertyForm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyFormViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

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

    fun getPropertyWithPhotos(id: Long) = propertyRepository.getPropertyWithPhotos(id)
}