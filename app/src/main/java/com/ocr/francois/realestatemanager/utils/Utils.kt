package com.ocr.francois.realestatemanager.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import kotlin.math.roundToInt

class Utils {

    companion object {
        fun convertDollarToEuro(dollar: Int) = (dollar * 0.89).roundToInt()

        fun convertEuroToDollar(euro: Int) = (euro * 1.12).roundToInt()

        fun isInternetAvailable(context: Context): Boolean {
            return true
        }

        fun getTodayDate(): String {
            val today = LocalDate.now()
            val dateFormatter = DateTimeFormat.forPattern("dd/MM/YYYY")

            return dateFormatter.print(today)
        }
    }
}