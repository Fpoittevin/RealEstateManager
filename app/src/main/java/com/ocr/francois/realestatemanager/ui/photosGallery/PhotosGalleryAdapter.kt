package com.ocr.francois.realestatemanager.ui.photosGallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.models.Photo

class PhotosGalleryAdapter : RecyclerView.Adapter<PhotosGalleryViewHolder>() {

    private val photosList = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosGalleryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.photos_gallery_item, parent, false)

        return PhotosGalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosGalleryViewHolder, position: Int) {
        holder.updateUi(photosList[position])
    }

    override fun getItemCount() = photosList.size

    fun updateList(photos: List<Photo>) {
        photosList.clear()
        photosList.addAll(photos)
        notifyDataSetChanged()
    }
}