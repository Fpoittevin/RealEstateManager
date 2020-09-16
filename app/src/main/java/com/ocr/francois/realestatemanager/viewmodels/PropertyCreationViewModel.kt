package com.ocr.francois.realestatemanager.viewmodels

import android.app.Activity
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import com.ocr.francois.realestatemanager.repositories.PropertyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PropertyCreationViewModel(
    private val propertyRepository: PropertyRepository,
    private val photoRepository: PhotoRepository

) : ViewModel() {

    private val photosList = mutableListOf<Photo>()
    val photosListLiveData = MutableLiveData<MutableList<Photo>>().apply {
        value = photosList
    }

    private val photosURIList = mutableListOf<Uri>()
    val photosURIListLiveData = MutableLiveData<MutableList<Uri>>().apply {
        value = photosURIList
    }

    fun createImageFile(activity: Activity): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRENCH).format(Date())
        val storageDir: File? =
            activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    fun removeImageURIFromList(imageURI: Uri) {
        photosURIList.remove(imageURI)
        photosURIListLiveData.value = photosURIList
    }

    fun addPhotoInList(photo: Photo) {
        photosList.add(0, photo)
        photosListLiveData.value = photosList
    }

    fun addImageURIInList(imageURI: Uri) {
        photosURIList.add(0, imageURI)
        photosURIListLiveData.value = photosURIList
    }

    fun deleteImageFile(imageURI: Uri) {
        val imageFile = File(imageURI.path!!)
        if (imageFile.exists()) {
            imageFile.delete()
        }
    }

    fun saveProperty(property: Property) {
/*
        val photos = ArrayList<Photo>()

        photosURIList.forEach {
            val photo = Photo(null, it.toString(), null)
            photos.add(photo)
        }

 */
        viewModelScope.launch(Dispatchers.IO) {
            propertyRepository.insertProperty(property, photosList)
        }
    }

    fun getPhotosListLiveData(propertyId: Long?) {
        propertyId.let {

        }
    }
}