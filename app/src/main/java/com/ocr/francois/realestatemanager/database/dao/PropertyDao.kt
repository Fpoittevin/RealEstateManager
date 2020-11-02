package com.ocr.francois.realestatemanager.database.dao

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos

@Dao
interface PropertyDao {

    @Insert
    fun insertProperty(property: Property): Long

    @Insert
    fun insertPhotos(photos: List<Photo>)

    @Query("DELETE FROM Photo WHERE propertyId= :propertyId")
    fun deletePhotosOfProperty(propertyId: Long)

    @Update
    suspend fun updateProperty(property: Property)

    @Query("SELECT * FROM Property")
    fun selectAllProperties(): LiveData<List<Property>>

    @Query("SELECT Property.*, Photo.* FROM Property LEFT JOIN Photo ON Photo.propertyId = Property.id")
    fun selectPropertiesWithPhotos(): LiveData<List<PropertyWithPhotos>>

    @Query("SELECT * FROM Property WHERE (lat BETWEEN :minLat AND :maxLat) AND (lng BETWEEN :minLng AND :maxLng)")
    fun selectPropertiesInBounds(
        minLat: Double,
        maxLat: Double,
        minLng: Double,
        maxLng: Double
    ): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE id = :id")
    fun selectProperty(id: Long): LiveData<Property>

    suspend fun insertPropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) {
        propertyWithPhotos.property.id = insertProperty(propertyWithPhotos.property)
        propertyWithPhotos.photosList.forEach {
            it.propertyId = propertyWithPhotos.property.id
        }
        insertPhotos(propertyWithPhotos.photosList)
    }

    suspend fun updatePropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) {
        updateProperty(propertyWithPhotos.property)

        deletePhotosOfProperty(propertyWithPhotos.property.id!!)
        insertPhotos(propertyWithPhotos.photosList)
    }

    @Transaction
    @Query("SELECT * FROM Property")
    fun getPropertiesWithPhotos(): LiveData<List<PropertyWithPhotos>>

    @Transaction
    @Query("SELECT * FROM Property WHERE id = :id")
    fun getPropertyWithPhotos(id: Long): LiveData<PropertyWithPhotos>

    @Query("SELECT * FROM Property WHERE id = :id")
    fun getProperty(id: Long): LiveData<Property>

    @Transaction
    @RawQuery
    fun getPropertiesBySearch(query: SupportSQLiteQuery): LiveData<List<PropertyWithPhotos>>


    @Query("SELECT * FROM Property WHERE id = :id")
    fun getPropertyWithCursor(id: Long): Cursor
}