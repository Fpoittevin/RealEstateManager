package com.ocr.francois.realestatemanager.ui.mapView

import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.repositories.PropertyRepository

class MapViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    fun getPropertiesInBounds(bounds: LatLngBounds) =
        propertyRepository.getPropertiesInBounds(bounds)
}