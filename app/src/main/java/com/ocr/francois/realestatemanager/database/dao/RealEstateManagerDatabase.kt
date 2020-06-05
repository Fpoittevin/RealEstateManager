package com.ocr.francois.realestatemanager.database.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ocr.francois.realestatemanager.models.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao

    companion object{

    @Volatile private var INSTANCE: RealEstateManagerDatabase? = null

        fun getInstance(context: Context): RealEstateManagerDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                RealEstateManagerDatabase::class.java, "RealEstateManagerDatabase.db")
                .build()
    }
}