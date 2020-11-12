package com.ocr.francois.realestatemanager.ui.propertiesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos

class PropertiesAdapter :
    RecyclerView.Adapter<PropertyViewHolder>() {

    private lateinit var propertyItemClickCallback: PropertyItemClickCallback
    private var propertiesWithPhotos: List<PropertyWithPhotos> = ArrayList()

    private var itemSelectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_property_item, parent, false)

        return PropertyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return propertiesWithPhotos.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val propertyWithPhotos = propertiesWithPhotos[position]

        holder.updateUi(propertyWithPhotos)

        if (position == itemSelectedPosition) {
            holder.setIsSelected()
        }

        holder.itemView.setOnClickListener {
            propertyItemClickCallback.onPropertyItemClick(
                propertyWithPhotos.property.id as Long
            )
            itemSelectedPosition = position
            notifyDataSetChanged()
        }
    }

    fun updateProperties(propertiesWithPhotosList: List<PropertyWithPhotos>) {
        propertiesWithPhotos = propertiesWithPhotosList
        notifyDataSetChanged()
    }

    fun setPropertyItemClickCallback(propertyItemClickCallback: PropertyItemClickCallback) {
        this.propertyItemClickCallback = propertyItemClickCallback
    }

    interface PropertyItemClickCallback {
        fun onPropertyItemClick(propertyId: Long)
    }
}