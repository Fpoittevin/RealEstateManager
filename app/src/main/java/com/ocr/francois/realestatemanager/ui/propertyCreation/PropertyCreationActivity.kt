package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import android.view.MenuItem
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_property_creation.*

class PropertyCreationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_creation)

        configureToolbar()
        displayFragment(R.id.activity_creation_frame_layout, PropertyCreationFragment.newInstance())
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_property_creation_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}