package com.ocr.francois.realestatemanager.ui.dataBindingUtils

import androidx.databinding.InverseMethod
import com.ocr.francois.realestatemanager.utils.Currency
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate

object Converter {

    @InverseMethod("intToString")
    @JvmStatic
    fun stringToIntOrNull(value: String): Int? {
        return if (value.isNotEmpty()) {
            value.toInt()
        } else {
            null
        }
    }

    @JvmStatic
    fun intToString(value: Int?): String {
        return value?.toString() ?: ""
    }

    @JvmStatic
    fun formatTimestamp(value: Long): String {
        return Utils.formatDate(LocalDate(value))
    }

    @JvmStatic
    fun floatToInt(value: Float): Int {
        return value.toInt()
    }

    @InverseMethod("stringOrNullToString")
    @JvmStatic
    fun stringToStringOrNull(value: String): String? {
        return if (value.isNotEmpty()) {
            return value
        } else {
            null
        }
    }

    @JvmStatic
    fun stringOrNullToString(value: String?): String {
        return value ?: ""
    }
}