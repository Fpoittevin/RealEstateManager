package com.ocr.francois.realestatemanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PropertyViewModel(
    private val propertyRepository: PropertyRepository
) : ViewModel() {

    fun getAllProperties(): LiveData<List<Property>> = propertyRepository.getAllProperties()

    fun getProperty(id: Long): LiveData<Property> = propertyRepository.getProperty(id)

    fun insertProperty(property: Property) =
        viewModelScope.launch(Dispatchers.IO) { propertyRepository.insertProperty(property) }
    //fun insertProperty(property: Property) = executor.execute { propertyRepository.insertProperty(property) }

}