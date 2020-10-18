package com.ocr.francois.realestatemanager.ui.propertiesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.PropertyRepository

class PropertiesListViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    var propertySearchLiveData = MutableLiveData<PropertySearch?>().apply {
        postValue(null)
    }

    fun getPropertiesWithPhotos(): LiveData<List<PropertyWithPhotos>> =
        Transformations.switchMap(propertySearchLiveData) {
            propertyRepository.getPropertiesWithPhotos(it)
        }
}