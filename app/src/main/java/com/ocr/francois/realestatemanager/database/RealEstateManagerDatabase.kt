package com.ocr.francois.realestatemanager.database

import android.content.ContentValues
import android.content.Context
import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ocr.francois.realestatemanager.database.dao.PhotoDao
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import java.util.*
import kotlin.collections.ArrayList

@Database(entities = [Property::class, Photo::class], version = 1, exportSchema = false)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun propertyDao(): PropertyDao
    abstract fun photoDao(): PhotoDao

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
                .addCallback(CALLBACK)
                .build()

        private val CALLBACK = object : Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                val propertyA = Property(
                    1,
                    "loft",
                    3000003,
                    350.4f,
                    4,
                    3,
                    2,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    "5th avenue",
                    "Apt 6/7A",
                    "Manhattan",
                    "NYC",
                    "10021",
                    "NY",
                    Date().time,
                    null,
                    "Bob",
                    false,
                    false,
                    false,
                    false,
                    40.7267391,
                    -73.9860395
                )
                val propertyB = Property(
                    2,
                    "appart",
                    300000,
                    350.4f,
                    4,
                    3,
                    2,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    "5th avenue",
                    "Apt 6/7A",
                    "Manhattan",
                    "NYC",
                    "10021",
                    "NY",
                    Date().time,
                    null,
                    "Bob",
                    false,
                    false,
                    false,
                    false,
                    40.7300416,
                    -73.9867517
                )
                val propertyC = Property(
                    3,
                    "home",
                    300000,
                    350.4f,
                    4,
                    3,
                    2,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    "5th avenue",
                    "Apt 6/7A",
                    "Manhattan",
                    "NYC",
                    "10021",
                    "NY",
                    Date().time,
                    null,
                    "Bob",
                    false,
                    false,
                    false,
                    false,
                    40.7327449,
                    -73.9851557
                )
                val propertyD = Property(
                    4,
                    "caravane",
                    300000,
                    350.4f,
                    4,
                    3,
                    2,
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                    "5th avenue",
                    "Apt 6/7A",
                    "Manhattan",
                    "NYC",
                    "10021",
                    "NY",
                    Date().time,
                    null,
                    "Bob",
                    false,
                    false,
                    false,
                    false,
                    null,
                    null
                )

                val properties = ArrayList<Property>(0)
                properties.add(propertyA)
                properties.add(propertyB)
                properties.add(propertyC)
                properties.add(propertyD)

                for (property in properties) {
                    val contentValues = ContentValues()
                    contentValues.put("type", property.type)
                    contentValues.put("price", property.price)
                    contentValues.put("surface", property.surface)
                    contentValues.put("numberOfRooms", property.numberOfRooms)
                    contentValues.put("numberOfBathrooms", property.numberOfBathrooms)
                    contentValues.put("numberOfBedrooms", property.numberOfBedrooms)
                    contentValues.put("description", property.description)
                    contentValues.put("addressFirst", property.addressFirst)
                    contentValues.put("addressSecond", property.addressSecond)
                    contentValues.put("district", property.district)
                    contentValues.put("city", property.city)
                    contentValues.put("zipCode", property.zipCode)
                    contentValues.put("state", property.state)
                    contentValues.put("creationTimestamp", property.creationTimestamp)
                    //contentValues.put("saleTimestamp", property.saleTimestamp)
                    contentValues.put("estateAgent", property.estateAgent)

                    contentValues.put("nearSchool", property.nearSchool)
                    contentValues.put("nearTransports", property.nearTransports)
                    contentValues.put("nearShops", property.nearShops)
                    contentValues.put("nearParks", property.nearParks)

                    contentValues.put("lat", property.lat)
                    contentValues.put("lng", property.lng)

                    db.insert("Property", OnConflictStrategy.REPLACE, contentValues)
                }
            }
        }
    }
}