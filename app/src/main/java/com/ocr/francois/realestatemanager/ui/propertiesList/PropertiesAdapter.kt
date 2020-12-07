package com.ocr.francois.realestatemanager.ui.propertiesList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.databinding.RecyclerViewPropertyItemBinding
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos

class PropertiesAdapter(private val propertiesListViewModel: PropertiesListViewModel) :
    RecyclerView.Adapter<PropertyViewHolder>() {

    private lateinit var propertyItemClickCallback: PropertyItemClickCallback
    private var propertiesWithPhotos: List<PropertyWithPhotos> = ArrayList()

    var itemSelectedPosition: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemBinding = RecyclerViewPropertyItemBinding.inflate(inflater, parent, false)

        return PropertyViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return propertiesWithPhotos.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val propertyWithPhotos = propertiesWithPhotos[position]

        holder.bind(propertyWithPhotos, propertiesListViewModel)

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

    fun updatePropertySelected(propertyId: Long?) {
        for(propertyWithPhoto in propertiesWithPhotos) {
            if(propertyWithPhoto.property.id == propertyId) {
                itemSelectedPosition = propertiesWithPhotos.indexOf(propertyWithPhoto)
            }
        }
        notifyDataSetChanged()
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