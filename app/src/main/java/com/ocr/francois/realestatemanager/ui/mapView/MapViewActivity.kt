package com.ocr.francois.realestatemanager.ui.mapView

import android.content.Intent
import android.os.Bundle
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityMapViewBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import kotlinx.android.synthetic.main.activity_map_view_ex.*

class MapViewActivity : BaseActivity(),
    MapViewFragment.MarkerClickCallback {

    private lateinit var binding: ActivityMapViewBinding
    private val mapViewFragment = MapViewFragment.newInstance(this)

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_map_view_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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