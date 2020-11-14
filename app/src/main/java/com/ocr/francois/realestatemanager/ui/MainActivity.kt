package com.ocr.francois.realestatemanager.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityMainBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.loanSimulator.LoanSimulatorActivity
import com.ocr.francois.realestatemanager.ui.mapView.MapViewActivity
import com.ocr.francois.realestatemanager.ui.mapView.MapViewFragment
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesAdapter
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListFragment
import com.ocr.francois.realestatemanager.ui.propertiesList.PropertiesListViewModel
import com.ocr.francois.realestatemanager.ui.propertyCreation.PropertyCreationActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment
import com.ocr.francois.realestatemanager.ui.propertyModification.PropertyModificationActivity
import com.ocr.francois.realestatemanager.ui.propertySearch.PropertySearchActivity
import com.ocr.francois.realestatemanager.ui.settings.SettingsActivity


class MainActivity : BaseActivity(),
    NavigationView.OnNavigationItemSelectedListener,
    PropertiesAdapter.PropertyItemClickCallback,
    PropertyDetailsFragment.PropertyModificationFabListener,
    MapViewFragment.MarkerClickCallback {

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

        configureToolbar(binding.activityMainToolbar, false)
        configureDrawerLayout()
        configureNavigationView()

        displayFragment(
            R.id.activity_main_frame_layout,
            propertiesListFragment
        )
    }

    private fun configureDrawerLayout() {
        ActionBarDrawerToggle(
            this,
            binding.activityMainDrawerLayout,
            binding.activityMainToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        ).apply {
            binding.activityMainDrawerLayout.addDrawerListener(this)
            syncState()
        }
    }

    private fun configureNavigationView() {
        binding.activityMainNavigationView.setNavigationItemSelectedListener(this)
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

    private fun startLoanSimulatorActivity() {
        val loanSimulatorIntent = Intent(
            this,
            LoanSimulatorActivity::class.java
        )
        startActivity(loanSimulatorIntent)
    }

    private fun startSettingsActivity() {
        val settingsIntent = Intent(
            this,
            SettingsActivity::class.java
        )
        startActivity(settingsIntent)
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

    private fun showPropertyDetails(propertyId: Long) {
        binding.activityMainFrameLayoutSecond?.let {
            displayFragment(
                R.id.activity_main_frame_layout_second,
                PropertyDetailsFragment.newInstance(propertyId, this)
            )
        } ?: run {
            startPropertyDetailsActivity(propertyId)
        }
    }

    private fun showMapView() {
        binding.activityMainFrameLayoutSecond?.let {
            displayFragment(
                R.id.activity_main_frame_layout_second,
                MapViewFragment.newInstance(this)
            )
        } ?: run {
            startMapViewActivity()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main_toolbar_menu, menu)
        return true
    }

    override fun onPropertyModificationClick(propertyId: Long) {
        startPropertyModificationActivity(propertyId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)

        if (requestCode == REQUEST_SEARCH_CODE && resultCode == RESULT_OK && intent != null) {
            intent.extras?.let {
                if (it.containsKey("PROPERTY_SEARCH")) {
                    propertiesListViewModel.propertySearchLiveData.value =
                        it.getParcelable("PROPERTY_SEARCH")
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_activity_toolbar_menu_creation_button -> startPropertyCreationActivity()
            R.id.main_activity_toolbar_menu_filter_button -> startPropertySearchActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.activity_main_drawer_map -> showMapView()
            R.id.activity_main_drawer_loan -> startLoanSimulatorActivity()
            R.id.activity_main_drawer_settings -> startSettingsActivity()
        }
        return true
    }

    override fun onBackPressed() {
        if (binding.activityMainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.activityMainDrawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPropertyItemClick(propertyId: Long) {
        showPropertyDetails(propertyId)
    }

    override fun onMarkerClickCallback(propertyId: Long) {
        showPropertyDetails(propertyId)
    }
}