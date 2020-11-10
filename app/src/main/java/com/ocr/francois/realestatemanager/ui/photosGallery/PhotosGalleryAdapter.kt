package com.ocr.francois.realestatemanager.ui.photosGallery

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
            holder.binding.photosGalleryItemDescriptionTextInputLayout.error = "ERROR"
            holder.binding.photosGalleryItemDescriptionTextView.visibility = View.GONE
            holder.binding.photosGalleryItemDescriptionTextInput.addTextChangedListener(object :
                TextWatcher {
                override fun beforeTextChanged(
                    description: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(description: Editable?) {
                    if(description.isNullOrEmpty()){
                        holder.binding.photosGalleryItemDescriptionTextInputLayout.error =
                            "all photo need a description"
                    } else {
                        holder.binding.photosGalleryItemDescriptionTextInputLayout.isErrorEnabled = false
                        photo.description = description.toString()
                    }
                }

            }

            )
        } else {
            holder.binding.photosGalleryItemDeleteButton.visibility = View.GONE
            holder.binding.photosGalleryItemDescriptionTextInputLayout.visibility = View.GONE
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

        internal var binding = PhotosGalleryItemBinding.bind(itemView)

        fun updateUi(photo: Photo) {
            binding.apply {
                Glide
                    .with(itemView)
                    .load(photo.uri)
                    .centerCrop()
                    .into(photosGalleryItemImageView)

                photo.description?.let {
                    photosGalleryItemDescriptionTextInput.setText(it)
                    photosGalleryItemDescriptionTextView.text = it
                }
            }
        }
    }
}