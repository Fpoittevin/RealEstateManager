package com.ocr.francois.realestatemanager.ui.photosGallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.databinding.PhotosGalleryItemBinding
import com.ocr.francois.realestatemanager.models.Photo

class PhotosGalleryAdapter :
    RecyclerView.Adapter<PhotosGalleryAdapter.PhotosGalleryViewHolder>() {

    private val photosList = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosGalleryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = PhotosGalleryItemBinding.inflate(inflater, parent, false)

        return PhotosGalleryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PhotosGalleryViewHolder, position: Int) {

        val photo = photosList[position]
        holder.bind(photo)
    }

    override fun getItemCount() = photosList.size

    fun updateList(photos: List<Photo>) {
        photosList.run {
            clear()
            addAll(photos)
        }
        notifyDataSetChanged()
    }

    class PhotosGalleryViewHolder(private val binding: PhotosGalleryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            binding.apply {
                this.photo = photo
            }
        }
    }
}