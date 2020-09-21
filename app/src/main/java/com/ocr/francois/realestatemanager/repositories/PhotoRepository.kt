package com.ocr.francois.realestatemanager.repositories

import com.ocr.francois.realestatemanager.database.dao.PhotoDao
import com.ocr.francois.realestatemanager.models.Photo

class PhotoRepository(private val photoDao: PhotoDao) {

    suspend fun insertPhoto(photo: Photo) = photoDao.insertPhoto(photo)

    suspend fun insertPhotosOfProperty(photos: List<Photo>, propertyId: Long) {
        photoDao.deletePhotosOfProperty(propertyId)
        photoDao.insertPhotosOfProperty(photos)
    }

    fun getPhotosOfProperty(propertyId: Long) = photoDao.selectPhotosOfProperty(propertyId)
}