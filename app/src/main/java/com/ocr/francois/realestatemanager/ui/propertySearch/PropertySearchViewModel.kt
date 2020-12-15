package com.ocr.francois.realestatemanager.ui.propertySearch

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.utils.Currency

class PropertySearchViewModel(
    currencyRepository: CurrencyRepository
) : ViewModel() {

    val currency = MediatorLiveData<Currency>().apply {
        addSource(currencyRepository.getCurrencyLiveData()) {
            value = it
        }
    }

    val propertySearch = PropertySearch()

    fun isSoldChange(newValue: Boolean?) {
        propertySearch.apply {
            isSold = newValue
            when (newValue) {
                false -> {
                    minSaleTimestamp = null
                    maxSaleTimestamp = null
                }
                null -> {
                    minSaleTimestamp = null
                    maxSaleTimestamp = null
                }
            }
            notifyChange()
        }
    }
}