package com.ocr.francois.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property

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

    fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    fun insertProperty(property: Property, photos: List<Photo>) = propertyDao.insertPropertyWithPhotos(property, photos)
}