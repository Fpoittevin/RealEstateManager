package com.ocr.francois.realestatemanager.ui.propertiesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.models.Property

class PropertiesAdapter() :
    RecyclerView.Adapter<PropertyViewHolder>() {

    private lateinit var propertyItemClickCallback: PropertyItemClickCallback
    private var properties: List<Property> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recycler_view_property_item, parent, false)

        return PropertyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val property = properties[position]
        holder.updateUi(property)
        holder.itemView.setOnClickListener {
            propertyItemClickCallback.onPropertyItemClick(
                property.id as Long
            )
        }
    }

    fun updateProperties(propertiesList: List<Property>) {
        properties = propertiesList
        notifyDataSetChanged()
    }

    fun setPropertyItemClickCallback(propertyItemClickCallback: PropertyItemClickCallback) {
        this.propertyItemClickCallback = propertyItemClickCallback
    }

    interface PropertyItemClickCallback {
        fun onPropertyItemClick(id: Long)
    }
}