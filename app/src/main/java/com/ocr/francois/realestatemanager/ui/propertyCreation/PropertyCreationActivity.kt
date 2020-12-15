package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyCreationBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment

class PropertyCreationActivity : BaseActivity() {

    private lateinit var binding: ActivityPropertyCreationBinding
    val propertyFormFragment = PropertyFormFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_creation)

        configureToolbar(binding.activityPropertyCreationToolbar, true)

        displayFragment(
            R.id.activity_creation_frame_layout,
            propertyFormFragment
        )
    }
}