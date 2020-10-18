package com.ocr.francois.realestatemanager.ui.propertyDetails

import androidx.lifecycle.ViewModel
import com.ocr.francois.realestatemanager.repositories.PropertyRepository

class PropertyDetailsViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    fun getPropertyWithPhotos(id: Long) = propertyRepository.getPropertyWithPhotos(id)
}