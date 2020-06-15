package com.ocr.francois.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityMainBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesAdapter
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListFragment
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity

class MainActivity : BaseActivity(), PropertiesAdapter.PropertyItemClickCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        displayFragment(R.id.activity_main_frame_layout, PropertiesListFragment.newInstance())
    }

    override fun onPropertyItemClick(id: Long) {
        showPropertyDetails(id)
    }

    private fun showPropertyDetails(id: Long) {
        val propertyDetailsIntent = Intent(this, PropertyDetailsActivity::class.java).apply {
            putExtra("propertyId", id)
        }
        startActivity(propertyDetailsIntent)
    }
}