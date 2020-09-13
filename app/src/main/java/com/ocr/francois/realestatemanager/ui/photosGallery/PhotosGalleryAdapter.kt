package com.ocr.francois.realestatemanager.ui.photosGallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R

class PhotosGalleryAdapter : RecyclerView.Adapter<PhotosGalleryViewHolder>() {

    private var photosUriList = mutableListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosGalleryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.photos_gallery_item, parent, false)

        return PhotosGalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosGalleryViewHolder, position: Int) {
        holder.updateUi(photosUriList[position])
    }

    override fun getItemCount() = photosUriList.size

    fun updateList(uriList: MutableList<Uri>) {
        photosUriList = uriList
        notifyDataSetChanged()
    }
}