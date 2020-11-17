package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyDetailsBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment.Companion.PROPERTY_ID_KEY
import com.ocr.francois.realestatemanager.ui.propertyModification.PropertyModificationActivity

class PropertyDetailsActivity : BaseActivity(),
    PropertyDetailsFragment.PropertyModificationFabListener {

    private lateinit var binding: ActivityPropertyDetailsBinding
    private var propertyId: Long? = null

    private val propertyDetailsViewModel: PropertyDetailsViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        propertyId = intent.getLongExtra(PROPERTY_ID_KEY, 1)
        configureToolbar(binding.activityPropertyDetailsToolbar, true)

        displayFragment(
            R.id.activity_details_frame_layout,
            PropertyDetailsFragment.newInstance(propertyId!!, this)
        )

        setToolbarTitle()
    }

    private fun setToolbarTitle() {
        propertyId?.let { id ->
            propertyDetailsViewModel.getPropertyWithPhotos(id).observe(this, { propertyWithPhotos ->
                propertyWithPhotos.property.type?.let {
                    binding.activityPropertyDetailsToolbar.title = it
                }
            })
        }
    }

    override fun onPropertyModificationClick(propertyId: Long) {
        startPropertyModificationActivity(propertyId)
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
}