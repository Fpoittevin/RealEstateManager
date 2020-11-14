package com.ocr.francois.realestatemanager.ui.propertyDetails

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyDetailsBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyModification.PropertyModificationActivity
import kotlinx.android.synthetic.main.activity_property_details.*

class PropertyDetailsActivity : BaseActivity(),
    PropertyDetailsFragment.PropertyModificationFabListener {

    private lateinit var binding: ActivityPropertyDetailsBinding
    private var propertyId: Long? = null

    private val propertyDetailsViewModel: PropertyDetailsViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    companion object {
        const val PROPERTY_ID_KEY = "propertyId"
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