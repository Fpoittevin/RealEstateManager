package com.ocr.francois.realestatemanager.ui.propertiesList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.base.BaseCurrencyViewModel

class PropertiesListViewModel(
    private val propertyRepository: PropertyRepository,
    private val currencyRepository: CurrencyRepository
) : BaseCurrencyViewModel(currencyRepository) {

    var propertySearchLiveData = MutableLiveData<PropertySearch?>().apply {
        postValue(null)
    }

    fun getPropertiesWithPhotos(): LiveData<List<PropertyWithPhotos>> =

        MediatorLiveData<List<PropertyWithPhotos>>().apply {
            addSource(
                Transformations.switchMap(propertySearchLiveData) { propertySearch ->
                    propertyRepository.getPropertiesWithPhotos(propertySearch)
                }
            ) { propertiesWithPhotos ->
                for (propertyWithPhotos in propertiesWithPhotos) {
                    convertAndFormatPrice(propertyWithPhotos.property)
                }
                value = propertiesWithPhotos
            }
            addSource(currencyRepository.getCurrencyLiveData()) {
                currency = it

                value?.let { propertiesWithPhotos ->
                    for (propertyWithPhotos in propertiesWithPhotos) {
                        convertAndFormatPrice(propertyWithPhotos.property)
                    }
                    value = propertiesWithPhotos
                }
            }
        }


}