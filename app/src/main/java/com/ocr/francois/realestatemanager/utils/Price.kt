package com.ocr.francois.realestatemanager.utils

import androidx.lifecycle.MediatorLiveData
import com.ocr.francois.realestatemanager.utils.Currency

class Price(priceValue: Int, currencyLiveData: CurrencyLiveData): MediatorLiveData<String>() {

/*
    init {
        value = Utils.formatNumber(this.value)
        addSource(currencyLiveData) {
            this.currency = it
        }
    }

    override fun toString() =
        when(currency) {
            Currency.DOLLAR ->
                "$" + Utils.formatNumber(this.value)
            Currency.EURO ->
                Utils.formatNumber(value) + "€"
        }
    }
    */

}