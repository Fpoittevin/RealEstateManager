package com.ocr.francois.realestatemanager.utils

class LocationTool {

    companion object {

        fun addressConcatenation(
            addressFirst: String?,
            addressSecond: String?,
            city: String?,
            zipCode: String?,
            toDisplay: Boolean
        ): String {

            val separator = if (toDisplay)
                "\n"
            else
                ", "

            val addressStringBuilder = StringBuilder()

            addressFirst?.let { addressStringBuilder.append(it) }
            addressSecond?.let {
                addressStringBuilder
                    .append(separator)
                    .append(it)
            }
            zipCode?.let {
                addressStringBuilder
                    .append(separator)
                    .append(it)
            }
            city?.let {
                addressStringBuilder
                    .append(" ")
                    .append(it)
            }

            return addressStringBuilder.toString()
        }
    }
}