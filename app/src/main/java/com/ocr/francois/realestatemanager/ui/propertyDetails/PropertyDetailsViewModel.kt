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


    fun getProperty(id: Long) = propertyRepository.getProperty(id)

    fun getPropertyWithPhotos(id: Long) = MediatorLiveData<PropertyWithPhotos>().apply {
        addSource(propertyRepository.getPropertyWithPhotos(id)) { propertyWithPhotos ->

            convertAndFormatPrice(propertyWithPhotos.property)

            value = propertyWithPhotos
        }
        addSource(currencyRepository.getCurrencyLiveData()) { newCurrency ->
            currency = newCurrency

            value?.let { propertyWithPhotos ->
                convertAndFormatPrice(propertyWithPhotos.property)
            }
        }
    }
}