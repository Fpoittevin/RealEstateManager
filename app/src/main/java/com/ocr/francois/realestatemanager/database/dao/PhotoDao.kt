package com.ocr.francois.realestatemanager.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ocr.francois.realestatemanager.models.Photo

@Dao
interface PhotoDao {

    @Insert
    suspend fun insertPhoto(photo: Photo): Long

    @Query("SELECT * FROM Photo WHERE propertyId = :propertyId")
    fun selectPhotosOfProperty(propertyId: Long): LiveData<List<Photo>>

    @Query("DELETE FROM Photo WHERE propertyId = :propertyId")
    suspend fun deletePhotosOfProperty(propertyId: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotosOfProperty(photos: List<Photo>)
}