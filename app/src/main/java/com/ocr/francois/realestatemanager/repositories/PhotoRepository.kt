package com.ocr.francois.realestatemanager.repositories

import android.net.Uri
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.utils.ImageUtil

class PhotoRepository {

    fun deletePhotoFile(photo: Photo) {
        ImageUtil.deleteFileFromUri(Uri.parse(photo.uri))
    }
}