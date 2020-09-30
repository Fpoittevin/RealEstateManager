package com.ocr.francois.realestatemanager.ui.propertySearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment

class PropertySearchActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_search)

        displayFragment(
            R.id.activity_property_search_frame_layout,
            PropertySearchFragment.newInstance()
        )
    }
}