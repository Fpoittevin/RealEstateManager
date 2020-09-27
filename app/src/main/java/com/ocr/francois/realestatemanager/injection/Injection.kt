package com.ocr.francois.realestatemanager.injection

import android.content.Context
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository

class Injection {

    companion object {
        private fun providePropertyRepository(context: Context): PropertyRepository {
            val db = RealEstateManagerDatabase.getInstance(context)
            return PropertyRepository(db.propertyDao())
        }

        private fun providePhotoRepository(context: Context): PhotoRepository {
            val db = RealEstateManagerDatabase.getInstance(context)
            return PhotoRepository(db.photoDao())
        }

        fun provideViewModelFactory(context: Context) =
            ViewModelFactory(providePropertyRepository(context))
    }
}