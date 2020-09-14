package com.ocr.francois.realestatemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository

class PropertyViewModel(
    private val propertyRepository: PropertyRepository, private val photoRepository: PhotoRepository
) : ViewModel() {

    fun getAllProperties(): LiveData<List<Property>> = propertyRepository.getAllProperties()

    fun getPropertiesInBounds(bounds: LatLngBounds) =
        propertyRepository.getPropertiesInBounds(bounds)

    fun getProperty(id: Long): LiveData<Property> = propertyRepository.getProperty(id)

    fun getPhotosOfProperty(propertyId: Long) = photoRepository.getPhotosOfProperty(propertyId)
}