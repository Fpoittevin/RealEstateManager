package com.ocr.francois.realestatemanager.injection

import android.content.Context
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class Injection {

    companion object {
        fun providePropertyRepository(context: Context): PropertyRepository {
            val db: RealEstateManagerDatabase = RealEstateManagerDatabase.getInstance(context)
            return PropertyRepository(db.propertyDao())
        }

        fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()
    }
}