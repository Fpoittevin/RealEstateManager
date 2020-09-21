package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.ui.MainActivity
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyModification.PropertyModificationActivity
import kotlinx.android.synthetic.main.activity_property_details.*

class PropertyDetailsActivity : BaseActivity(), PropertyDetailsFragment.PropertyModificationFabListener {

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_details)

        val propertyId = intent.getLongExtra(PROPERTY_ID_KEY, 1)
        configureToolbar()
        displayFragment(
            R.id.activity_details_frame_layout,
            PropertyDetailsFragment.newInstance(propertyId, this)
        )
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_property_details_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPropertyModificationClick(propertyId: Long) {
        val propertyModificationIntent = Intent(this, PropertyModificationActivity::class.java).apply {
            putExtra(MainActivity.PROPERTY_ID_KEY, propertyId)
        }
        startActivity(propertyModificationIntent)

    }
}