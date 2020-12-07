package com.ocr.francois.realestatemanager.ui.propertyDetails

import androidx.lifecycle.MediatorLiveData
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.base.BaseCurrencyViewModel

class PropertyDetailsViewModel(
    private val propertyRepository: PropertyRepository,
    private val currencyRepository: CurrencyRepository
) : BaseCurrencyViewModel(currencyRepository) {

    val propertyWithPhotos = MediatorLiveData<PropertyWithPhotos>()

    fun getPropertyWithPhotos(id: Long) {
        if (propertyWithPhotos.value == null) {
            propertyWithPhotos.apply {
                addSource(propertyRepository.getPropertyWithPhotos(id)) { propertyWithPhotos ->
                    value = propertyWithPhotos
                }
                addSource(currencyRepository.getCurrencyLiveData()) { newCurrency ->
                    currency = newCurrency
                }
            }
        }
    }
}