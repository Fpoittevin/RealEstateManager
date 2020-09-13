package com.ocr.francois.realestatemanager.ui.photosGallery

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R

class PhotosGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val imageView = itemView.findViewById<ImageView>(R.id.photos_gallery_item_image_view)

    fun updateUi(uri: Uri) {
        imageView.setImageURI(uri)
    }
}