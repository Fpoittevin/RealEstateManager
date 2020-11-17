package com.ocr.francois.realestatemanager.ui.propertyForm

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.viewModelScope
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.ui.base.BaseCurrencyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyFormViewModel(
    private val propertyRepository: PropertyRepository,
    private val currencyRepository: CurrencyRepository
) : BaseCurrencyViewModel(currencyRepository) {

    fun createPropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) {
        viewModelScope.launch(Dispatchers.IO) {
            propertyRepository.insertPropertyWithPhotos(propertyWithPhotos)
        }
    }

    fun updatePropertyWithPhotos(
        propertyWithPhotos: PropertyWithPhotos,
        isAddressChanged: Boolean
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            propertyWithPhotos.photosList.forEach {
                it.propertyId = propertyWithPhotos.property.id
            }
            propertyRepository.updatePropertyWithPhotos(propertyWithPhotos, isAddressChanged)
        }
    }

    fun getPropertyWithPhotos(id: Long) = MediatorLiveData<PropertyWithPhotos>().apply {
        addSource(propertyRepository.getPropertyWithPhotos(id)) { propertyWithPhotos ->
            propertyWithPhotos.property.apply {
                price?.let {
                    formattedPrice =
                        convertPriceInCurrentCurrency(it).toString()
                }
            }
            value = propertyWithPhotos
        }
        addSource(currencyRepository.getCurrencyLiveData()) {
            currency = it
            value?.let { propertyWithPhotos ->
                propertyWithPhotos.property.apply {
                    price?.let { price ->
                        formattedPrice =
                            convertPriceInCurrentCurrency(price).toString()
                    }
                }
            }
        }
    }
}