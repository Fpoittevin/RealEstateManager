package com.ocr.francois.realestatemanager.repositories

import androidx.lifecycle.LiveData
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Property

class PropertyRepository(private val propertyDao: PropertyDao) {

    fun getProperty(id: Long): LiveData<Property> = propertyDao.selectProperty(id)

    fun getAllProperties(): LiveData<List<Property>> = propertyDao.selectAllProperties()

    fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    suspend fun insertProperty(property: Property) = propertyDao.insertProperty(property)
}