package com.ocr.francois.realestatemanager.utils

import com.ocr.francois.realestatemanager.models.Property

class LocationTool {

    companion object {

        fun addressConcatenation(property: Property, toDisplay: Boolean): String {

            val separator = if (toDisplay)
                "\n"
            else
                ", "

            val addressStringBuilder = StringBuilder()

            property.addressFirst?.let { addressStringBuilder.append(it) }
            property.addressSecond?.let {
                addressStringBuilder
                    .append(separator)
                    .append(it)
            }
            property.zipCode?.let {
                addressStringBuilder
                    .append(separator)
                    .append(it)
            }
            property.city?.let {
                addressStringBuilder
                    .append(" ")
                    .append(it)
            }

            return addressStringBuilder.toString()
        }
    }
}