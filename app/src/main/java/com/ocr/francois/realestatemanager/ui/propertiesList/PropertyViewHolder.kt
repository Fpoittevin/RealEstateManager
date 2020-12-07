package com.ocr.francois.realestatemanager.ui.propertiesList

import androidx.recyclerview.widget.RecyclerView
import com.ocr.francois.realestatemanager.databinding.RecyclerViewPropertyItemBinding
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos

class PropertyViewHolder(private val binding: RecyclerViewPropertyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setIsSelected() {
        binding.recyclerViewPropertyItemCardView.cardElevation = 2.0F
    }

    fun bind(
        propertyWithPhotos: PropertyWithPhotos,
        propertiesListViewModel: PropertiesListViewModel
    ) {

        binding.apply {
            this.propertyWithPhotos = propertyWithPhotos
            this.viewModel = propertiesListViewModel
            recyclerViewPropertyItemCardView.cardElevation = 5.0F
            recyclerViewPropertyItemTypeTextView.text = propertyWithPhotos.property.type
        }
    }
}