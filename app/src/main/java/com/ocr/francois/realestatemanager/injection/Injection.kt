package com.ocr.francois.realestatemanager.injection

import android.app.Application
import android.content.Context
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.repositories.CurrencyRepository
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository

class Injection {

    companion object {
        fun providePropertyRepository(context: Context): PropertyRepository {
            val db = RealEstateManagerDatabase.getInstance(context)
            return PropertyRepository(db.propertyDao(), context)
        }

        private fun provideCurrencyRepository(context: Context) =
            CurrencyRepository(context)

        private fun providePhotoRepository() =
            PhotoRepository()

        fun provideViewModelFactory(context: Context) =
            ViewModelFactory(
                providePropertyRepository(context),
                provideCurrencyRepository(context),
                providePhotoRepository()
            )
    }
}