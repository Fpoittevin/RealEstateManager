package com.ocr.francois.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos

class PropertyRepository(private val propertyDao: PropertyDao) {

    fun getProperty(id: Long): LiveData<Property> = propertyDao.selectProperty(id)

    fun getAllProperties(): LiveData<List<Property>> = propertyDao.selectAllProperties()

    fun getPropertiesInBounds(bounds: LatLngBounds): LiveData<List<Property>> =
        propertyDao.selectPropertiesInBounds(
            bounds.southwest.latitude,
            bounds.northeast.latitude,
            bounds.southwest.longitude,
            bounds.northeast.longitude
        )

    fun getPropertiesWithPhotos() : LiveData<List<PropertyWithPhotos>> = propertyDao.getPropertiesWithPhotos()

    fun getPropertyWithPhotos(id: Long): LiveData<PropertyWithPhotos> = propertyDao.getPropertyWithPhotos(id)

    suspend fun insertPropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) =
        propertyDao.insertPropertyWithPhotos(propertyWithPhotos)

    suspend fun updatePropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) =
        propertyDao.updatePropertyWithPhotos(propertyWithPhotos)
}