package com.ocr.francois.realestatemanager.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivitySettingsBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.utils.Currency

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var preferences: SharedPreferences

    private val settingsViewModel: SettingsViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        preferences = getPreferences(MODE_PRIVATE)

        setContentView(binding.root)

        configureCurrenciesRadioGroup()
    }

    private fun configureCurrenciesRadioGroup() {

        settingsViewModel.getCurrencyLiveData().observe(this, { currency ->
            currency?.let {
                when (it) {
                    Currency.DOLLAR ->
                        binding.activitySettingsCurrenciesDollarRadioButton.isChecked = true
                    Currency.EURO ->
                        binding.activitySettingsCurrenciesEuroRadioButton.isChecked = true
                }
            }
        })

        binding.activitySettingsCurrenciesRadioGroup
            .setOnCheckedChangeListener { _, checkedId ->

                var currencyChoice = Currency.DOLLAR

                when (checkedId) {
                    R.id.activity_settings_currencies_dollar_radio_button
                    -> currencyChoice = Currency.DOLLAR
                    R.id.activity_settings_currencies_euro_radio_button
                    -> currencyChoice = Currency.EURO
                }

                settingsViewModel.saveCurrency(currencyChoice)
            }
    }
}