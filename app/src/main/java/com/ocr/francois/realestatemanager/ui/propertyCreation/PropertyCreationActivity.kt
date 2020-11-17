package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyCreationBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment

class PropertyCreationActivity : BaseActivity() {

    private lateinit var binding: ActivityPropertyCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar(binding.activityPropertyCreationToolbar, true)

        displayFragment(
            R.id.activity_creation_frame_layout,
            PropertyFormFragment.newInstance()
        )
    }
}