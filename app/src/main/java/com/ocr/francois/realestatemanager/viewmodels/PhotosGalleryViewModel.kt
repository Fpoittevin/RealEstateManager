package com.ocr.francois.realestatemanager.viewmodels

import android.app.Activity
import android.os.Environment
import android.util.Log
import androidx.lifecycle.*
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.repositories.PhotoRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class PhotosGalleryViewModel(
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val photosListLiveData = MediatorLiveData<MutableList<Photo>>()
    private val photosList = mutableListOf<Photo>()

    fun getPhotosListLiveData(propertyId: Long?): MutableLiveData<MutableList<Photo>> {

        propertyId?.let {
            Log.e("PROPERTY ID: ", propertyId.toString())
            photosListLiveData.addSource(photoRepository.getPhotosOfProperty(it)) { photosOfProperty ->
                photosOfProperty.forEach { photo ->
                    addPhoto(photo)
                }
            }
        }

        photosListLiveData.value = photosList
        return photosListLiveData
    }

    fun addPhoto(photo: Photo) {
        if (!photosList.contains(photo)) {
            photosList.add(photo)
            photosListLiveData.value = photosList
        }
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
}