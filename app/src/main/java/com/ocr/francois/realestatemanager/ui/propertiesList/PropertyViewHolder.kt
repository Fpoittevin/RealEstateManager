package com.ocr.francois.realestatemanager.ui.propertiesList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.models.Property

class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val typeTextView: TextView =
        itemView.findViewById(R.id.recycler_view_property_item_type_text_view)
    private val priceTextView: TextView =
        itemView.findViewById(R.id.recycler_view_property_item_price_text_view)


    fun updateUi(property: Property) {
        typeTextView.text = property.type
        priceTextView.text = "$" + property.price
    }
}