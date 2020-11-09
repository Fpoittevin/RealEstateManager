package com.ocr.francois.realestatemanager.utils

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

class CurrencyLiveData(private val sharedPreferences: SharedPreferences) : LiveData<Currency>() {

    companion object {
        const val CURRENCY_KEY = "currency"
    }

    init {
        value = sharedPreferences.getString(CURRENCY_KEY, Currency.DOLLAR.name)?.let {
            Currency.valueOf(
                it
            )
        }
    }

    private val currencySharedPreferenceListener =
        SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == CURRENCY_KEY) {
                value = sharedPreferences.getString(key, Currency.DOLLAR.name)?.let {
                    Currency.valueOf(
                        it
                    )
                }
            }
        }

    override fun onActive() {
        super.onActive()
        sharedPreferences.registerOnSharedPreferenceChangeListener(currencySharedPreferenceListener)
    }

    override fun onInactive() {
        super.onInactive()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(
            currencySharedPreferenceListener
        )
    }
}