package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.util.Log
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
    val currencyLiveData = currencyRepository.getCurrencyLiveData()

    fun getPropertyWithPhotos(id: Long) {
        if (propertyWithPhotos.value == null) {

            propertyWithPhotos.apply {
                addSource(propertyRepository.getPropertyWithPhotos(id)) { propertyWithPhotos ->
                    value = propertyWithPhotos
                    Log.e("From details:", propertyWithPhotos.property.price.toString())
                }
                addSource(currencyRepository.getCurrencyLiveData()) { newCurrency ->
                    currency = newCurrency
                }
            }
        }
    }
}