package com.ocr.francois.realestatemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(
    private val propertyRepository: PropertyRepository, private val photoRepository: PhotoRepository
) : ViewModel() {

    fun getAllProperties(): LiveData<List<Property>> = propertyRepository.getAllProperties()

    fun getPropertiesInBounds(bounds: LatLngBounds) =
        propertyRepository.getPropertiesInBounds(bounds)

    fun getProperty(id: Long): LiveData<Property> = propertyRepository.getProperty(id)

    fun getPhotosOfProperty(propertyId: Long) = photoRepository.getPhotosOfProperty(propertyId)

    fun updateProperty(property: Property) {
        viewModelScope.launch(Dispatchers.IO) { propertyRepository.updateProperty(property) }
    }

    fun createProperty(property: Property, photosList: MutableList<Photo>) {
        viewModelScope.launch(Dispatchers.IO) {
            propertyRepository.insertProperty(property, photosList)
        }
    }
}