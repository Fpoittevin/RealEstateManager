package com.ocr.francois.realestatemanager.injection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import com.ocr.francois.realestatemanager.viewmodels.PhotosGalleryViewModel
import com.ocr.francois.realestatemanager.viewmodels.PropertyViewModel

class ViewModelFactory(
    private val propertyRepository: PropertyRepository,
    private val photoRepository: PhotoRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PropertyViewModel::class.java)) {
            return PropertyViewModel(propertyRepository) as T
        } else if (modelClass.isAssignableFrom(PhotosGalleryViewModel::class.java)) {
            return PhotosGalleryViewModel(photoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}