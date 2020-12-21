package com.ocr.francois.realestatemanager.ui.settings

import androidx.lifecycle.ViewModel
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.utils.Currency

class SettingsViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    val currencyLiveData = currencyRepository.getCurrencyLiveData()

    fun saveCurrency(currency: Currency) {
        currencyRepository.saveCurrency(currency)
    }
}