package com.ocr.francois.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Property

class PropertyRepository(private val propertyDao: PropertyDao) {

    fun getProperty(id: Long): LiveData<Property> = propertyDao.selectProperty(id)

    //fun getAllProperties(): LiveData<List<Property>> = propertyDao.selectAllProperties()

    fun getAllProperties(): LiveData<List<Property>> {
        val propertiesLiveData = MutableLiveData<List<Property>>()

        val propertyA = Property(1, "loft", 3000003, 350.4f, 4, "Lorem ipsum", "5th avenue, NYC", "Manhattan", "school, cinema", "Bob")
        val propertyB = Property(2, "appart", 300000, 350.4f, 4, "Lorem ipsum", "5th avenue, NYC", "Montauk", "school, cinema", "Bob")
        val propertyC = Property(3, "home", 300000, 350.4f, 4, "Lorem ipsum", "5th avenue, NYC", "Brooklin", "school, cinema", "Bob")
        val propertyD = Property(4, "caravane", 300000, 350.4f, 4, "Lorem ipsum", "5th avenue, NYC", "Southamption", "school, cinema", "Bob")

        val properties = ArrayList<Property>(0)
        properties.add(propertyA)
        properties.add(propertyB)
        properties.add(propertyC)
        properties.add(propertyD)

        propertiesLiveData.postValue(properties)

        return propertiesLiveData
    }

    suspend fun updateProperty(property: Property) = propertyDao.updateProperty(property)

    suspend fun insertProperty(property: Property) = propertyDao.insertProperty(property)
}