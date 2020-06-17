package com.ocr.francois.realestatemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import java.util.concurrent.Executor

class PropertyViewModel(
    private val propertyRepository: PropertyRepository,
    private val executor: Executor
) : ViewModel() {

    fun getAllProperties(): LiveData<List<Property>> = propertyRepository.getAllProperties()

    fun getProperty(id: Long): LiveData<Property> = propertyRepository.getProperty(id)

    fun insertProperty(property: Property) = executor.execute { propertyRepository.insertProperty(property) }
}