package com.ocr.francois.realestatemanager.repositories

import android.content.Context
import android.content.SharedPreferences
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.CurrencyLiveData

class CurrencyRepository(
    private val context: Context) {

    private var preferences: SharedPreferences =
        this.context.getSharedPreferences(
            this.context.getString(R.string.preferences_file_key),
            Context.MODE_PRIVATE
        )

    fun saveCurrency(currency: Currency) {
        this.preferences.edit()
            .putString(CurrencyLiveData.CURRENCY_KEY, currency.name)
            .apply()
    }

    fun getCurrencyLiveData() = CurrencyLiveData(preferences)
}