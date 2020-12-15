package com.ocr.francois.realestatemanager

import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun convertDollarToEuroTest() {
        assertEquals(89, Utils.convertDollarToEuro(100))
    }

    @Test
    fun convertEuroToDollarTest() {
        assertEquals(112, Utils.convertEuroToDollar(100))
    }

    @Test
    fun getTodayStringFormat() {
        val today = LocalDate()
        val stringBuilder = StringBuilder()

        with(stringBuilder) {
            if (today.dayOfMonth < 10) {
                append("0")
            }
            append(today.dayOfMonth.toString())
            append("/")
            if (today.monthOfYear < 10) {
                append("0")
            }
            append(today.monthOfYear)
            append("/")
            append(today.year.toString())
        }

        assertEquals(stringBuilder.toString(), Utils.getTodayDateStringFormat())
    }

    @Test
    fun formatNumberTest() {
        val number = 1000000
        assertEquals("1,000,000", Utils.formatNumber(number))
    }

    @Test
    fun getFormattedPriceInDollar() {
        val price = 1000000
        assertEquals("$1,000,000", Utils.getFormattedPriceWithCurrency(Currency.DOLLAR, price))
    }

    @Test
    fun getFormattedPriceInEuro() {
        val price = 1000000
        assertEquals("1,000,000€", Utils.getFormattedPriceWithCurrency(Currency.EURO, price))
    }

    @Test
    fun getTimestampFromDatePicker() {
        val date = LocalDate.now()
        val timestampFromDatePicker =
            Utils.getTimestampFromDatePicker(date.year, date.monthOfYear - 1, date.dayOfMonth)
        val dateFromDatePicker = LocalDate(timestampFromDatePicker)

        assertEquals(date.year, dateFromDatePicker.year)
        assertEquals(date.monthOfYear, dateFromDatePicker.monthOfYear)
        assertEquals(date.dayOfMonth, dateFromDatePicker.dayOfMonth)
    }
}