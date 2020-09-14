package com.ocr.francois.realestatemanager.repositories

import com.ocr.francois.realestatemanager.database.dao.PhotoDao
import com.ocr.francois.realestatemanager.models.Photo

class PhotoRepository(private val photoDao: PhotoDao) {

    fun insertPhoto(photo: Photo) = photoDao.insertPhoto(photo)

    fun getPhotosOfProperty(propertyId: Long) = photoDao.selectPhotosOfProperty(propertyId)
}