package com.ocr.francois.realestatemanager.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PropertyCreationViewModel(
    private val propertyRepository: PropertyRepository

) : ViewModel() {

    private val photosURIList = mutableListOf<Uri>()


    fun saveProperty(property: Property, photosList: MutableList<Photo>) {

        viewModelScope.launch(Dispatchers.IO) {
            propertyRepository.insertProperty(property, photosList)
        }
    }
}