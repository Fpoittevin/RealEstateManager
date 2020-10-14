package com.ocr.francois.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityMainBinding
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.mapView.MapViewActivity
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesAdapter
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListFragment
import com.ocr.francois.realestatemanager.ui.propertyCreation.PropertyCreationActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment
import com.ocr.francois.realestatemanager.ui.propertySearch.PropertySearchActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), PropertiesAdapter.PropertyItemClickCallback,
    PropertyDetailsFragment.PropertyModificationFabListener {

    private val propertiesListFragment = PropertiesListFragment.newInstance()

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"
        const val REQUEST_SEARCH_CODE = 1234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar()
        displayFragment(R.id.activity_main_frame_layout, propertiesListFragment)
    }

    override fun onPropertyItemClick(id: Long) {
        showPropertyDetails(id)
    }

    private fun startPropertySearch() {
        val propertySearchIntent = Intent(this, PropertySearchActivity::class.java)
        startActivityForResult(propertySearchIntent, REQUEST_SEARCH_CODE)
    }

    private fun showPropertyDetails(id: Long) {
        startPropertyDetailsActivity(id)
    }

    private fun startPropertyDetailsActivity(id: Long) {
        activity_main_frame_layout_second?.let {
            displayFragment(
                R.id.activity_main_frame_layout_second,
                PropertyDetailsFragment.newInstance(id, this)
            )
        } ?: kotlin.run {
            val propertyDetailsIntent = Intent(this, PropertyDetailsActivity::class.java).apply {
                putExtra(PROPERTY_ID_KEY, id)
            }
            startActivity(propertyDetailsIntent)
        }
    }

    private fun startPropertyCreationActivity() {
        activity_main_frame_layout_second?.let {
            displayFragment(
                R.id.activity_main_frame_layout_second,
                PropertyFormFragment.newInstance()
            )
        } ?: kotlin.run {
            val propertyCreationIntent = Intent(this, PropertyCreationActivity::class.java)
            startActivity(propertyCreationIntent)
        }
    }

    private fun startMapViewActivity() {
        val mapViewIntent = Intent(this, MapViewActivity::class.java)
        startActivity(mapViewIntent)
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
            R.id.main_activity_toolbar_menu_map_view_button -> startMapViewActivity()
            R.id.main_activity_toolbar_menu_filter_button -> startPropertySearch()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPropertyModificationClick(propertyId: Long) {
        // TODO: launch modification fragment
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_SEARCH_CODE && resultCode == RESULT_OK && data != null) {

            Log.e("search intent !! : ", data.toString())

            propertiesListFragment.propertySearch = PropertySearch().apply {
                with(data) {
                    getIntExtra(PropertySearchFragment.MIN_PRICE_KEY, 0).also {
                        if (it != 0) {
                            minPrice = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MAX_PRICE_KEY, 0).also {
                        if (it != 0) {
                            maxPrice = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MIN_SURFACE_KEY, 0).also {
                        if (it != 0) {
                            minSurface = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MAX_SURFACE_KEY, 0).also {
                        if (it != 0) {
                            maxSurface = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MIN_NUMBER_OF_ROOMS_KEY, 0).also {
                        if (it != 0) {
                            minNumberOfRooms = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MAX_NUMBER_OF_ROOMS_KEY, 0).also {
                        if (it != 0) {
                            maxNumberOfRooms = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MIN_NUMBER_OF_BATHROOMS_KEY, 0).also {
                        if (it != 0) {
                            minNumberOfBathrooms = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MAX_NUMBER_OF_BATHROOMS_KEY, 0).also {
                        if (it != 0) {
                            maxNumberOfBathrooms = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MIN_NUMBER_OF_BEDROOMS_KEY, 0).also {
                        if (it != 0) {
                            minNumberOfBedrooms = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MAX_NUMBER_OF_BEDROOMS_KEY, 0).also {
                        if (it != 0) {
                            maxNumberOfBedrooms = it
                        }
                    }
                    nearSchool = getBooleanExtra(PropertySearchFragment.NEAR_SCHOOL_KEY, false)
                    nearTransports =
                        getBooleanExtra(PropertySearchFragment.NEAR_TRANSPORTS_KEY, false)
                    nearShops = getBooleanExtra(PropertySearchFragment.NEAR_SHOPS_KEY, false)
                    nearParks = getBooleanExtra(PropertySearchFragment.NEAR_PARKS_KEY, false)

                    getLongExtra(PropertySearchFragment.MIN_CREATION_TIMESTAMP_KEY, 0).also {
                        if (it != 0.toLong()) {
                            minCreationTimestamp = it
                        }
                    }
                    getLongExtra(PropertySearchFragment.MAX_CREATION_TIMESTAMP_KEY, 0).also {
                        if (it != 0.toLong()) {
                            maxCreationTimestamp = it
                        }
                    }
                    getLongExtra(PropertySearchFragment.MIN_SALE_TIMESTAMP_KEY, 0).also {
                        if (it != 0.toLong()) {
                            minSaleTimestamp = it
                        }
                    }
                    getLongExtra(PropertySearchFragment.MAX_SALE_TIMESTAMP_KEY, 0).also {
                        if (it != 0.toLong()) {
                            maxSaleTimestamp = it
                        }
                    }
                    getIntExtra(PropertySearchFragment.MIN_NUMBER_OF_PHOTOS_KEY, 0).also {
                        if (it != 0) {
                            minNumberOfPhotos = it
                        }
                    }
                }
            }
        }
    }
}