package com.ocr.francois.realestatemanager.ui.photosGallery

import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.databinding.PhotosGalleryFormItemBinding
import com.ocr.francois.realestatemanager.models.Photo

class PhotosGalleryFormAdapter(
    private val textWatcher: TextWatcher,
    private val deletePhotoClickCallback: PhotosGalleryFormViewHolder.DeletePhotoClickCallback
) :
    RecyclerView.Adapter<PhotosGalleryFormAdapter.PhotosGalleryFormViewHolder>() {

    private val photosList = mutableListOf<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosGalleryFormViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = PhotosGalleryFormItemBinding.inflate(inflater, parent, false)

        return PhotosGalleryFormViewHolder(deletePhotoClickCallback, itemBinding)
    }

    override fun onBindViewHolder(holder: PhotosGalleryFormViewHolder, position: Int) {

        val photo = photosList[position]
        holder.bind(photo)
        holder.binding
        holder.binding.photosGalleryItemDescriptionTextInput.addTextChangedListener(textWatcher)
    }

    override fun getItemCount() = photosList.size

    fun updateList(photos: List<Photo>) {
        photosList.run {
            clear()
            addAll(photos)
        }
        notifyDataSetChanged()
    }

    class PhotosGalleryFormViewHolder(
        private val deletePhotoClickCallback: DeletePhotoClickCallback,
        internal val binding: PhotosGalleryFormItemBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var photo: Photo

        fun bind(photo: Photo) {
            binding.photosGalleryItemDeleteButton.setOnClickListener {
                deletePhotoClickCallback.onDeletePhotoClick(photo)
            }
            this.photo = photo
            binding.apply {
                this.photo = photo
            }
        }

        interface DeletePhotoClickCallback {
            fun onDeletePhotoClick(photo: Photo)
        }
    }
}