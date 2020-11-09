package com.ocr.francois.realestatemanager.ui.propertiesList

import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ocr.francois.realestatemanager.databinding.RecyclerViewPropertyItemBinding
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.utils.Utils

class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = RecyclerViewPropertyItemBinding.bind(itemView)

    fun updateUi(propertyWithPhotos: PropertyWithPhotos) {

        binding.apply {
            recyclerViewPropertyItemTypeTextView.text = propertyWithPhotos.property.type

            propertyWithPhotos.property.saleTimestamp?.let {
                recyclerViewPropertyItemSoldTextView.text = "sold"
            } ?: run {
                recyclerViewPropertyItemSoldTextView.visibility = View.GONE
                recyclerViewPropertyItemSoldFilterView.visibility = View.GONE
            }

            propertyWithPhotos.property.formattedPrice?.let {
                recyclerViewPropertyItemPriceTextView.text = it
            }

            propertyWithPhotos.property.surface?.let {
                val surface = Utils.formatNumber(it) + " m²"
                recyclerViewPropertyItemSurfaceTextView.text = surface
            }

            propertyWithPhotos.property.city?.let {
                recyclerViewPropertyItemCityTextView.text = it
            }

            if (propertyWithPhotos.photosList.isNotEmpty()) {

                val uri = Uri.parse(propertyWithPhotos.photosList[0].uri)

                Glide
                    .with(itemView)
                    .load(uri.toString())
                    .centerCrop()
                    .into(recyclerViewPropertyItemPhotoImageView)
            }
        }
    }
}