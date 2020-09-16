package com.ocr.francois.realestatemanager.ui.photosGallery

import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.models.Photo

class PhotosGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.photos_gallery_item_image_view)
    //private val deleteButton = itemView.findViewById<Button>(R.id.photos_gallery_item_delete_button)

    fun updateUi(photo: Photo) {
        imageView.setImageURI(Uri.parse(photo.uri))
    }
}