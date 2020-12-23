package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyDetailsBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment.Companion.PROPERTY_ID_KEY
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment
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

        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_details)
        binding.apply {
            viewModel = propertyDetailsViewModel
            lifecycleOwner = this@PropertyDetailsActivity
        }

        configureToolbar(binding.activityPropertyDetailsToolbar, true)

        propertyId = intent.getLongExtra(PropertyFormFragment.PROPERTY_ID_KEY, 1)
        displayFragment(
            R.id.activity_details_frame_layout,
            PropertyDetailsFragment.newInstance(propertyId!!, this)
        )
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