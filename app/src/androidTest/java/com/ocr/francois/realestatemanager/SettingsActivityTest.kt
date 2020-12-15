package com.ocr.francois.realestatemanager

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ocr.francois.realestatemanager.ui.settings.SettingsActivity
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.CurrencyLiveData.Companion.CURRENCY_KEY
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsActivityTest {

    private lateinit var scenario: ActivityScenario<SettingsActivity>
    private lateinit var preferences: SharedPreferences

    @Before
    fun createActivity() {
        scenario = ActivityScenario.launch(SettingsActivity::class.java)
        val context = ApplicationProvider.getApplicationContext<Application>()
        preferences = context.getSharedPreferences(context.getString(R.string.preferences_file_key), Context.MODE_PRIVATE)
    }

    @Test
    fun dollarRadioButtonClick() {
        onView(withId(R.id.activity_settings_currencies_dollar_radio_button)).perform(click())
        val currency = preferences.getString(CURRENCY_KEY, Currency.DOLLAR.name)
        assertEquals(currency, Currency.DOLLAR.name)
    }

    @Test
    fun euroRadioButtonClick() {
        onView(withId(R.id.activity_settings_currencies_euro_radio_button)).perform(click())
        val currency = preferences.getString(CURRENCY_KEY, Currency.DOLLAR.name)
        assertEquals(currency, Currency.EURO.name)
    }
}