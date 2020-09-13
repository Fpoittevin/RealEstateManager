package com.ocr.francois.realestatemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.viewmodels.PropertyCreationViewModel
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel

class ViewModelFactory(private val propertyRepository: PropertyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            return PropertyViewModel(propertyRepository) as T
        } else if (modelClass.isAssignableFrom(PropertyCreationViewModel::class.java)) {
            return PropertyCreationViewModel(propertyRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}