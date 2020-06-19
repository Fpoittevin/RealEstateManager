package com.ocr.francois.realestatemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel
import java.util.concurrent.Executor

class ViewModelFactory(private val propertyRepository: PropertyRepository):
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PropertyViewModel(propertyRepository) as T
    }
}