package com.ocr.francois.realestatemanager.ui.propertiesList

import androidx.lifecycle.*
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.propertySearch.PropertySearch
import com.ocr.francois.realestatemanager.utils.Currency

class PropertiesListViewModel(
    private val propertyRepository: PropertyRepository,
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    private lateinit var currency: Currency
    val currencyLiveData = currencyRepository.getCurrencyLiveData()
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