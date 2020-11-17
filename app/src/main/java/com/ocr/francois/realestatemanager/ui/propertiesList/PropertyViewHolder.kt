package com.ocr.francois.realestatemanager.ui.propertiesList

import android.content.res.Resources
import android.net.Uri
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.RecyclerViewPropertyItemBinding
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.utils.Utils

class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding = RecyclerViewPropertyItemBinding.bind(itemView)

    fun setIsSelected() {
        binding.recyclerViewPropertyItemCardView.cardElevation = 2.0F
    }

    fun updateUi(propertyWithPhotos: PropertyWithPhotos) {

        binding.apply {

            recyclerViewPropertyItemCardView.cardElevation = 5.0F
            recyclerViewPropertyItemTypeTextView.text = propertyWithPhotos.property.type

            propertyWithPhotos.property.saleTimestamp?.let {
                recyclerViewPropertyItemSoldTextView.visibility = View.VISIBLE
                recyclerViewPropertyItemSoldFilterView.visibility = View.VISIBLE
            } ?: run {
                recyclerViewPropertyItemSoldTextView.visibility = View.INVISIBLE
                recyclerViewPropertyItemSoldFilterView.visibility = View.INVISIBLE
            }

            propertyWithPhotos.property.formattedPrice?.let {
                recyclerViewPropertyItemPriceTextView.text = it
            }

            propertyWithPhotos.property.surface?.let {
                val surface = Utils.formatNumber(it) + "m²"
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