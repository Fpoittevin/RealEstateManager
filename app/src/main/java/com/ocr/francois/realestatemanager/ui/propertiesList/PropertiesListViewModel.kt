package com.ocr.francois.realestatemanager.ui.propertiesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.base.BaseCurrencyViewModel
import com.ocr.francois.realestatemanager.ui.propertySearch.PropertySearch

class PropertiesListViewModel(
    private val propertyRepository: PropertyRepository,
    private val currencyRepository: CurrencyRepository
) : BaseCurrencyViewModel(currencyRepository) {

    var propertySearchLiveData = MutableLiveData<PropertySearch?>().apply {
        postValue(null)
    }
    var propertyIdSelectedLiveData = MutableLiveData<Long?>()

    fun getPropertiesWithPhotos(): LiveData<List<PropertyWithPhotos>> =

        MediatorLiveData<List<PropertyWithPhotos>>().apply {
            addSource(
                Transformations.switchMap(propertySearchLiveData) { propertySearch ->
                    propertyRepository.getPropertiesWithPhotos(propertySearch)
                }
            ) { propertiesWithPhotos ->
                value = propertiesWithPhotos
            }
            addSource(currencyRepository.getCurrencyLiveData()) {
                currency = it
            }
        }
}