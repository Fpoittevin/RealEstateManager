package com.ocr.francois.realestatemanager.ui.propertyModification

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyModificationBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment.Companion.PROPERTY_ID_KEY

class PropertyModificationActivity : BaseActivity() {

    private lateinit var binding: ActivityPropertyModificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_modification)

        configureToolbar(binding.activityPropertyModificationToolbar, true)

        val propertyId = intent.getLongExtra(PROPERTY_ID_KEY, 1)

        displayFragment(
            R.id.activity_property_modification_frame_layout,
            PropertyFormFragment.newInstance(propertyId)
        )
    }
}