package com.ocr.francois.realestatemanager.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Property

@Database(entities = [Property::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao

    companion object {

        @Volatile
        private var instance: RealEstateManagerDatabase? = null

        fun getInstance(context: Context): RealEstateManagerDatabase =
            instance
                ?: synchronized(this) {
                    instance
                        ?: buildDatabase(
                            context
                        )
                            .also { instance = it }
                }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                RealEstateManagerDatabase::class.java, "RealEstateManagerDatabase.db"
            )
                .build()
    }
}