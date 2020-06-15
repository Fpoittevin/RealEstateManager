package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.os.Bundle
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListFragment

class PropertyDetailsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_details)

        displayFragment(R.id.activity_details_frame_layout, PropertyDetailsFragment.newInstance())
    }
}