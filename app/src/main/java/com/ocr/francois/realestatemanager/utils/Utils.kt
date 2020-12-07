package com.ocr.francois.realestatemanager.utils

import android.content.Context
import android.content.res.Resources
import com.ocr.francois.realestatemanager.R
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import java.text.NumberFormat
import java.util.*
import kotlin.math.roundToInt

class Utils {

    companion object {
        fun convertDollarToEuro(dollar: Int) = (dollar * 0.89).roundToInt()

        fun convertEuroToDollar(euro: Int) = (euro * 1.12).roundToInt()

        fun isInternetAvailable(context: Context) = IsInternetAvailableLiveData(context)

        fun getTodayDateStringFormat(): String {
            val today = LocalDate.now()
            return formatDate(today)
        }

        fun formatNumber(number: Int): String =
            NumberFormat.getNumberInstance(
                Locale.US
            ).format(number)


        fun formatDate(date: LocalDate): String {
            val dateFormatter = DateTimeFormat.forPattern("dd/MM/YYYY")
            return dateFormatter.print(date)
        }
        fun formatDate(timestamp: Long): String {
            val date = LocalDate(timestamp)
            val dateFormatter = DateTimeFormat.forPattern("dd/MM/YYYY")
            return dateFormatter.print(date)
        }

        fun getTodayTimestamp(): Long {
            return Date().time
        }

        fun getTimestampFromDatePicker(year: Int, month: Int, day: Int): Long {
            val date: Calendar = Calendar.getInstance()
            date[Calendar.DAY_OF_MONTH] = day
            date[Calendar.MONTH] = month
            date[Calendar.YEAR] = year
            date.set(Calendar.HOUR_OF_DAY, 23)
            date.set(Calendar.MINUTE, 59)
            date.set(Calendar.SECOND, 59)
            return date.timeInMillis
        }

        fun getFormattedPriceWithCurrency(currency: Currency, price: Int) =
            when (currency) {
                Currency.DOLLAR ->
                    "$" + formatNumber(price)
                Currency.EURO ->
                    formatNumber(price) + "€"
        }
    }
}