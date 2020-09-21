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

        val propertyId = intent.getLongExtra(PROPERTY_ID_KEY, 1)

        configureToolbar()

        displayFragment(
            R.id.activity_property_modification_frame_layout,
            PropertyFormFragment.newInstance(propertyId)
        )
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_property_modification_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}