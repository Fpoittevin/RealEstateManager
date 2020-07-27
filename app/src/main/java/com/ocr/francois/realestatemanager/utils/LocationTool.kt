package com.ocr.francois.realestatemanager.utils

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ocr.francois.realestatemanager.models.Property

class LocationTool {

    companion object {

        fun getAndSaveLocationFromAddress(property: Property, context: Context) {

            /*val address = addressConcatenation(property, false)

            val workManager = WorkManager.getInstance(context)

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val req = OneTimeWorkRequestBuilder<LocationWorker>()
                .setConstraints(constraints)
                .addTag("TAG_OUTPUT")
                .build()

            workManager.enqueue(req)*/
        }

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