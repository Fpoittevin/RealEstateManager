package com.ocr.francois.realestatemanager.ui.settings

import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.ui.base.BaseCurrencyViewModel
import com.ocr.francois.realestatemanager.utils.Currency

class SettingsViewModel(
    private val currencyRepository: CurrencyRepository
) : BaseCurrencyViewModel(currencyRepository) {

    fun saveCurrency(currency: Currency) {
        currencyRepository.saveCurrency(currency)
    }
}