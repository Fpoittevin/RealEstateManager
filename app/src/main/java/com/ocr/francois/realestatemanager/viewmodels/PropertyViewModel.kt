package com.ocr.francois.realestatemanager.viewmodels

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    var propertySearchLiveData = MutableLiveData<PropertySearch?>().apply {
        postValue(null)
    }

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

    fun getPropertiesWithPhotos(): LiveData<List<PropertyWithPhotos>> =
        Transformations.switchMap(propertySearchLiveData) {
            propertyRepository.getPropertiesWithPhotos(it)
        }

    fun getPropertyWithPhotos(id: Long) = propertyRepository.getPropertyWithPhotos(id)
}