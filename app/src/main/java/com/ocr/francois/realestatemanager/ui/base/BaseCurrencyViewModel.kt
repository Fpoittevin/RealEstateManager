package com.ocr.francois.realestatemanager.ui.base

import androidx.lifecycle.ViewModel
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.Utils

open class BaseCurrencyViewModel(
    private val currencyRepository: CurrencyRepository
) : ViewModel() {

    protected var currency = Currency.DOLLAR

    fun getCurrencyLiveData() = currencyRepository.getCurrencyLiveData()

    protected fun convertPriceInCurrentCurrency(price: Int) =
        when (currency) {
            Currency.DOLLAR ->
                price
            Currency.EURO ->
                Utils.convertDollarToEuro(price)
        }
}
