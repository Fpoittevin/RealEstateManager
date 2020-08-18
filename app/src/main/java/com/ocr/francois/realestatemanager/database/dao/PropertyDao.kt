package com.ocr.francois.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.ocr.francois.realestatemanager.models.Property

@Dao
interface PropertyDao {

    @Insert
    fun insertProperty(property: Property): Long

    @Update
    fun updateProperty(property: Property)

    @Query("SELECT * FROM Property")
    fun selectAllProperties(): LiveData<List<Property>>

    @Query("SELECT id, lat, lng FROM Property WHERE (lat BETWEEN :minLat AND :maxLat) AND (lng BETWEEN :minLng AND :maxLng)")
    fun selectPropertiesInBounds(
        minLat: Double,
        maxLat: Double,
        minLng: Double,
        maxLng: Double
    ): LiveData<List<Property>>

    @Query("SELECT * FROM Property WHERE id = :id")
    fun selectProperty(id: Long): LiveData<Property>
}