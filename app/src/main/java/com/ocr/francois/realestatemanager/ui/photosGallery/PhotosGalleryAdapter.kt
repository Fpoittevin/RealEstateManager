package com.ocr.francois.realestatemanager.ui.photosGallery

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.PhotosGalleryItemBinding
import com.ocr.francois.realestatemanager.models.Photo

class PhotosGalleryAdapter(private val isEditable: Boolean) :
    RecyclerView.Adapter<PhotosGalleryAdapter.PhotosGalleryViewHolder>() {

    internal val photosList = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosGalleryViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.photos_gallery_item, parent, false)

        return PhotosGalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotosGalleryViewHolder, position: Int) {

        val photo = photosList[position]
        holder.updateUi(photo)

        if (isEditable) {
            holder.binding.photosGalleryItemDeleteButton.setOnClickListener {
                this.removePhotoFromList(photo)
            }
        } else {
            holder.binding.photosGalleryItemDeleteButton.visibility = View.GONE
        }

    }

    override fun getItemCount() = photosList.size

    fun updateList(photos: List<Photo>) {
        photosList.run {
            clear()
            addAll(photos)
        }
        notifyDataSetChanged()
    }

    internal fun addPhotoInList(photo: Photo) {
        photosList.add(photo)
        notifyDataSetChanged()
    }

    private fun removePhotoFromList(photo: Photo) {
        photosList.remove(photo)
        notifyDataSetChanged()
    }

    class PhotosGalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var binding: PhotosGalleryItemBinding = PhotosGalleryItemBinding.bind(itemView)

        fun updateUi(photo: Photo) {
            binding.photosGalleryItemImageView.setImageURI(Uri.parse(photo.uri))
        }
    }
}