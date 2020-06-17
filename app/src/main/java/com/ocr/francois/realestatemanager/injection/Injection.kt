package com.ocr.francois.realestatemanager.injection

import android.content.Context
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import java.util.concurrent.Executors

class Injection {

    companion object {
        private fun providePropertyRepository(context: Context): PropertyRepository {
            val db = RealEstateManagerDatabase.getInstance(context)
            return PropertyRepository(db.propertyDao())
        }

        private fun provideExecutor() = Executors.newSingleThreadExecutor()

        fun provideViewModelFactory(context: Context) = ViewModelFactory(providePropertyRepository(context), provideExecutor())
    }
}