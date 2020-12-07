package com.ocr.francois.realestatemanager.repositories

import android.net.Uri
import com.ocr.francois.realestatemanager.models.Photo
import com.ocr.francois.realestatemanager.utils.ImageUtil
import com.ocr.francois.realestatemanager.utils.Utils

class PhotoRepository {

    suspend fun deletePhotoFile(photo: Photo) {
        ImageUtil.deleteFileFromUri(Uri.parse(photo.uri))
    }
}