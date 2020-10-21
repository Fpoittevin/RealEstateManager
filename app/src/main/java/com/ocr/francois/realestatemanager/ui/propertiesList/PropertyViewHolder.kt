package com.ocr.francois.realestatemanager.ui.propertiesList

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.utils.Utils

class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val typeTextView: TextView =
        itemView.findViewById(R.id.recycler_view_property_item_type_text_view)
    private val priceTextView: TextView =
        itemView.findViewById(R.id.recycler_view_property_item_price_text_view)
    private val surfaceTextView: TextView =
        itemView.findViewById(R.id.recycler_view_property_item_surface_text_view)
    private val photoImageView: ImageView =
        itemView.findViewById(R.id.recycler_view_property_item_photo_image_view)


    fun updateUi(propertyWithPhotos: PropertyWithPhotos) {
        typeTextView.text = propertyWithPhotos.property.type

        propertyWithPhotos.property.price?.let {
            val price = "$ " + Utils.formatNumber(it)
            priceTextView.text = price
        }
        propertyWithPhotos.property.surface?.let {
            val surface = Utils.formatNumber(it) + " m²"
            surfaceTextView.text = surface
        }

        if(propertyWithPhotos.photosList.isNotEmpty()){
            //binding.photosGalleryItemImageView.setImageURI(Uri.parse(photo.uri))
            photoImageView.setImageURI(Uri.parse((propertyWithPhotos.photosList[0].uri)))
        }
    }
}