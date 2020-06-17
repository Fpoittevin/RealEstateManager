package com.ocr.francois.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityMainBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesAdapter
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListFragment
import com.ocr.francois.realestatemanager.ui.propertyCreation.PropertyCreationActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val PROPERTY_ID_KEY = "propertyId"

class MainActivity : BaseActivity(), PropertiesAdapter.PropertyItemClickCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        configureToolbar()
        displayFragment(R.id.activity_main_frame_layout, PropertiesListFragment.newInstance())
    }

    override fun onPropertyItemClick(id: Long) {
        showPropertyDetails(id)
    }

    private fun showPropertyDetails(id: Long) {
        startPropertyDetailsActivity(id)
    }

    private fun startPropertyDetailsActivity(id: Long) {
        val propertyDetailsIntent = Intent(this, PropertyDetailsActivity::class.java).apply {
            putExtra(PROPERTY_ID_KEY, id)
        }
        startActivity(propertyDetailsIntent)
    }

    private fun startPropertyCreationActivity() {
        val propertyCreationIntent = Intent(this, PropertyCreationActivity::class.java)
        startActivity(propertyCreationIntent)
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_main_toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_activity_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_activity_toolbar_menu_creation_button -> startPropertyCreationActivity()
        }
        return super.onOptionsItemSelected(item)
    }
}