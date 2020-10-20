package com.ocr.francois.realestatemanager.ui.propertyCreation

import android.os.Bundle
import android.view.MenuItem
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertyCreationBinding
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.ui.propertyForm.PropertyFormFragment
import kotlinx.android.synthetic.main.activity_property_creation.*

class PropertyCreationActivity : BaseActivity() {

    private lateinit var binding: ActivityPropertyCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPropertyCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar()

        displayFragment(
            R.id.activity_creation_frame_layout,
            PropertyFormFragment.newInstance()
        )
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_property_creation_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}