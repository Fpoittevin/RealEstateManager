package com.ocr.francois.realestatemanager.ui.mapView

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityMapViewBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment.Companion.PROPERTY_ID_KEY

class MapViewActivity : BaseActivity(),
    MapViewFragment.MarkerClickCallback {

    private lateinit var binding: ActivityMapViewBinding
    private val mapViewFragment = MapViewFragment.newInstance(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_map_view)
        configureToolbar(binding.activityMapViewToolbar, true)

        displayFragment(
            R.id.activity_map_view_frame_layout,
            mapViewFragment
        )
    }

    override fun onMarkerClickCallback(propertyId: Long) {
        startPropertyDetailsActivity(propertyId)
    }

    private fun startPropertyDetailsActivity(propertyId: Long) {
        val propertyDetailsIntent = Intent(
            this,
            PropertyDetailsActivity::class.java
        ).apply {
            putExtra(PROPERTY_ID_KEY, propertyId)
        }
        startActivity(propertyDetailsIntent)
    }
}