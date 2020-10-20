package com.ocr.francois.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityMainBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.mapView.MapViewActivity
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesAdapter
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListFragment
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListViewModel
import com.ocr.francois.realestatemanager.ui.propertyCreation.PropertyCreationActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment
import com.ocr.francois.realestatemanager.ui.propertyModification.PropertyModificationActivity
import com.ocr.francois.realestatemanager.ui.propertySearch.PropertySearchActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(),
    PropertiesAdapter.PropertyItemClickCallback,
    PropertyDetailsFragment.PropertyModificationFabListener {

    private lateinit var binding: ActivityMainBinding
    private val propertiesListFragment = PropertiesListFragment.newInstance()

    private val propertiesListViewModel: PropertiesListViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"
        const val REQUEST_SEARCH_CODE = 1234
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar()

        displayFragment(
            R.id.activity_main_frame_layout,
            propertiesListFragment
        )
    }

    override fun onPropertyItemClick(propertyId: Long) {
        showPropertyDetailsFragment(propertyId)
    }

    private fun startPropertySearchActivity() {
        val propertySearchIntent = Intent(
            this,
            PropertySearchActivity::class.java
        )
        startActivityForResult(propertySearchIntent, REQUEST_SEARCH_CODE)
    }

    private fun startMapViewActivity() {
        val mapViewIntent = Intent(
            this,
            MapViewActivity::class.java
        )
        startActivity(mapViewIntent)
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

    private fun startPropertyCreationActivity() {
        val propertyCreationIntent = Intent(
            this,
            PropertyCreationActivity::class.java
        )
        startActivity(propertyCreationIntent)
    }

    private fun startPropertyModificationActivity(propertyId: Long) {
        val propertyModificationIntent =
            Intent(
                this,
                PropertyModificationActivity::class.java
            ).apply {
                putExtra(PROPERTY_ID_KEY, propertyId)
            }
        startActivity(propertyModificationIntent)
    }

    private fun showPropertyDetailsFragment(propertyId: Long) {
        binding.activityMainFrameLayoutSecond?.let {
            displayFragment(
                R.id.activity_main_frame_layout_second,
                PropertyDetailsFragment.newInstance(propertyId, this)
            )
        } ?: run {
            startPropertyDetailsActivity(propertyId)
        }
    }

    private fun showPropertyCreationFragment() {
        binding.activityMainFrameLayoutSecond?.let {
            displayFragment(
                R.id.activity_main_frame_layout_second,
                PropertyFormFragment.newInstance()
            )
        } ?: kotlin.run {
            startPropertyCreationActivity()
        }
    }

    private fun showPropertyModificationFragment(propertyId: Long) {
        binding.activityMainFrameLayoutSecond?.let {
            displayFragment(
                R.id.activity_main_frame_layout_second,
                PropertyFormFragment.newInstance(propertyId)
            )
        } ?: kotlin.run {
            startPropertyModificationActivity(propertyId)
        }
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
            R.id.main_activity_toolbar_menu_creation_button -> showPropertyCreationFragment()
            R.id.main_activity_toolbar_menu_map_view_button -> startMapViewActivity()
            R.id.main_activity_toolbar_menu_filter_button -> startPropertySearchActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPropertyModificationClick(propertyId: Long) {
        showPropertyModificationFragment(propertyId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_SEARCH_CODE && resultCode == RESULT_OK && intent != null) {
            propertiesListViewModel.propertySearchLiveData.value =
                intent.extras?.getParcelable("PROPERTY_SEARCH")

        }
    }
}