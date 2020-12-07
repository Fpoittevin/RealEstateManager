package com.ocr.francois.realestatemanager.ui.propertySearch

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertySearchBinding
import com.ocr.francois.realestatemanager.injection.Injection
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate

class PropertySearchActivity : BaseActivity(),
    View.OnClickListener,
    DatePickerDialog.OnDateSetListener,
    RadioGroup.OnCheckedChangeListener {

    private lateinit var binding: ActivityPropertySearchBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var datePickerType: DatePickerType

    private val propertySearchViewModel: PropertySearchViewModel by viewModels {
        Injection.provideViewModelFactory(this)
    }

    enum class DatePickerType {
        CREATION_START,
        CREATION_STOP,
        SALE_START,
        SALE_STOP
    }

    companion object {
        const val PROPERTY_SEARCH_KEY = "PROPERTY_SEARCH_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_property_search)
        binding.apply {
            lifecycleOwner = this@PropertySearchActivity
            viewModel = propertySearchViewModel
        }

        configureToolbar(binding.activityPropertySearchToolbar, true)

        configureDatePickerButtons()
        configureDatePicker()
        configureIsSoldRadioGroup()

        configureFab()
    }

    private fun configureIsSoldRadioGroup() {
        binding.activityPropertySearchIsSoldRadioGroup.setOnCheckedChangeListener(this)
    }

    private fun configureDatePickerButtons() {
        binding.apply {
            activityPropertySearchCreationDateStartButton.setOnClickListener(this@PropertySearchActivity)
            activityPropertySearchCreationDateStopButton.setOnClickListener(this@PropertySearchActivity)
            activityPropertySearchSaleDateStartButton.setOnClickListener(this@PropertySearchActivity)
            activityPropertySearchSaleDateStopButton.setOnClickListener(this@PropertySearchActivity)
        }
    }

    private fun configureFab() {
        binding.activityPropertySearchFab.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.activity_property_search_creation_date_start_button -> {
                datePickerType = DatePickerType.CREATION_START
                configureAndDisplayDatePicker()
            }
            R.id.activity_property_search_creation_date_stop_button -> {
                datePickerType = DatePickerType.CREATION_STOP
                configureAndDisplayDatePicker()
            }
            R.id.activity_property_search_sale_date_start_button -> {
                datePickerType = DatePickerType.SALE_START
                configureAndDisplayDatePicker()
            }
            R.id.activity_property_search_sale_date_stop_button -> {
                datePickerType = DatePickerType.SALE_STOP
                configureAndDisplayDatePicker()
            }
            R.id.activity_property_search_fab -> {
                intent.putExtra(PROPERTY_SEARCH_KEY, propertySearchViewModel.propertySearch)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    private fun configureAndDisplayDatePicker() {
        val dateToDisplay = LocalDate.now()

        datePickerDialog.run {
            datePicker.apply {
                updateDate(
                    LocalDate(dateToDisplay).year,
                    (LocalDate(dateToDisplay).monthOfYear - 1),
                    LocalDate(dateToDisplay).dayOfMonth
                )
            }
            show()
        }
    }

    private fun configureDatePicker() {
        datePickerDialog = DatePickerDialog(
            this,
            this,
            LocalDate.now().year,
            (LocalDate.now().monthOfYear - 1),
            LocalDate.now().dayOfMonth
        ).also {
            it.datePicker.maxDate = Utils.getTodayTimestamp()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        when (datePickerType) {
            DatePickerType.CREATION_START -> {
                propertySearchViewModel.propertySearch.apply {
                    minCreationTimestamp = Utils.getTimestampFromDatePicker(year, month, dayOfMonth)

                    maxCreationTimestamp?.let {
                        if (LocalDate(minCreationTimestamp).isAfter(LocalDate(it))) {
                            maxCreationTimestamp = null
                        }
                    }
                }
            }
            DatePickerType.CREATION_STOP -> {
                propertySearchViewModel.propertySearch.apply {
                    maxCreationTimestamp = Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                    minCreationTimestamp?.let {
                        if (LocalDate(maxCreationTimestamp).isBefore(LocalDate(it))) {
                            minCreationTimestamp = null
                        }
                    }
                }
            }
            DatePickerType.SALE_START -> {
                propertySearchViewModel.propertySearch.apply {
                    minSaleTimestamp = Utils.getTimestampFromDatePicker(year, month, dayOfMonth)

                    maxSaleTimestamp?.let {
                        if (LocalDate(minSaleTimestamp).isAfter(LocalDate(it))) {
                            maxSaleTimestamp = null
                        }
                    }
                }
            }
            DatePickerType.SALE_STOP -> {
                propertySearchViewModel.propertySearch.apply {
                    maxSaleTimestamp = Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                    minSaleTimestamp?.let {
                        if (LocalDate(maxSaleTimestamp).isBefore(LocalDate(it))) {
                            minSaleTimestamp = null
                        }
                    }
                }
            }
        }

    }

    override fun onCheckedChanged(radioGroup: RadioGroup, radioButtonId: Int) {
        when (radioButtonId) {
            R.id.activity_property_search_sold_radio_button -> {
                propertySearchViewModel.isSoldChange(true)
            }
            R.id.activity_property_search_for_sale_radio_button -> {
                propertySearchViewModel.isSoldChange(false)
            }
            else -> propertySearchViewModel.isSoldChange(null)
        }
    }
}