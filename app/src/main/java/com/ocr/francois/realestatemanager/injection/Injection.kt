package com.ocr.francois.realestatemanager.injection

import android.content.Context
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.repositories.PropertyRepository

class Injection {

    companion object {
        fun providePropertyRepository(context: Context): PropertyRepository {
            val db = RealEstateManagerDatabase.getInstance(context)
            return PropertyRepository(db.propertyDao())
        }

        fun provideViewModelFactory(context: Context): ViewModelFactory {
            val propertyRepository = providePropertyRepository(context)

            return ViewModelFactory(propertyRepository)
        }
    }
}