package com.ocr.francois.realestatemanager.utils

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.joda.time.LocalDate
import org.joda.time.format.DateTimeFormat
import kotlin.math.roundToInt

class Utils {

    companion object {
        fun convertDollarToEuro(dollar: Int) = (dollar * 0.89).roundToInt()

        fun convertEuroToDollar(euro: Int) = (euro * 1.12).roundToInt()

        /*
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun isInternetAvailable(context: Context): LiveData<Boolean> {

            val isInternetAvailable = MutableLiveData<Boolean>()

            val networkCallback = ConnectivityManager.NetworkCallback()
            networkCallback.onAvailable()

            return true
        }
         */

        fun getTodayDate(): String {
            val today = LocalDate.now()
            val dateFormatter = DateTimeFormat.forPattern("dd/MM/YYYY")

            return dateFormatter.print(today)
        }
    }
}