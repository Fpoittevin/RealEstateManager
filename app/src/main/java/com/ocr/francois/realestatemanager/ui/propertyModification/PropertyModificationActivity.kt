package com.ocr.francois.realestatemanager.ui.propertyModification

import android.os.Bundle
import android.view.MenuItem
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyModificationBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment
import kotlinx.android.synthetic.main.activity_property_modification.*

class PropertyModificationActivity : BaseActivity() {

    private lateinit var binding: ActivityPropertyModificationBinding

    companion object {
        private const val PROPERTY_ID_KEY = "propertyId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyModificationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar(binding.activityPropertyModificationToolbar, true)

        val propertyId = intent.getLongExtra(PROPERTY_ID_KEY, 1)

        displayFragment(
            R.id.activity_property_modification_frame_layout,
            PropertyFormFragment.newInstance(propertyId)
        )
    }
}