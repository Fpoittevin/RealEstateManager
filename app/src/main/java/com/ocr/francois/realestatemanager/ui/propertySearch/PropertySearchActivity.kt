package com.ocr.francois.realestatemanager.ui.propertySearch

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.DatePicker
import com.google.android.material.slider.RangeSlider
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertySearchBinding
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.utils.Utils
import kotlinx.android.synthetic.main.activity_property_search.*
import org.joda.time.LocalDate

class PropertySearchActivity : BaseActivity(),
    RangeSlider.OnChangeListener,
    CompoundButton.OnCheckedChangeListener,
    View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityPropertySearchBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var datePickerType: DatePickerType
    private val propertySearch = PropertySearch()

    enum class DatePickerType {
        CREATION_START,
        CREATION_STOP,
        SALE_START,
        SALE_STOP
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolbar()
        configurePriceRangeSlider()
        configureSurfaceRangeSlider()
        configureRoomsRangeSlider()
        configureBathroomsRangeSlider()
        configureBedroomsRangeSlider()
        configureMinPhotosSlider()
        configurePointsOfInterestChips()
        configureDatePicker()
        configureCreationDateStartButton()
        configureCreationDateStopButton()
        configureSaleDateStartButton()
        configureSaleDateStopButton()
        configureFab()
    }

    private fun configureToolbar() {
        setSupportActionBar(activity_property_search_toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setTitle(R.string.search_title)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    // PRICE

    private fun configurePriceRangeSlider() {
        binding.activityPropertySearchPriceRangeSlider.addOnChangeListener(this)
        displayPriceValues()
    }

    private fun displayPriceValues() {
        binding.activityPropertySearchMinPriceTextView.apply {
            propertySearch.minPrice?.let {
                this.text =
                    Utils.formatNumber(it)
            } ?: run {
                this.text =
                    Utils.formatNumber(resources.getIntArray(R.array.initial_price_slider_values)[0])
            }
            binding.activityPropertySearchMaxPriceTextView.apply {
                propertySearch.maxPrice?.let {
                    this.text =
                        Utils.formatNumber(it)
                } ?: run {
                    this.text =
                        Utils.formatNumber(resources.getIntArray(R.array.initial_price_slider_values)[1])
                }
            }
        }
    }

    private fun setPriceValues(minValue: Int, maxValue: Int) {
        propertySearch.apply {
            minPrice = if (minValue == resources.getInteger(R.integer.property_min_price)) {
                null
            } else {
                minValue
            }
            maxPrice = if (maxValue == resources.getInteger(R.integer.property_max_price)) {
                null
            } else {
                maxValue
            }
        }
        displayPriceValues()
    }

    // SURFACE

    private fun configureSurfaceRangeSlider() {
        binding.activityPropertySearchSurfaceRangeSlider.addOnChangeListener(this)
        displaySurfaceValues()
    }

    private fun displaySurfaceValues() {
        binding.activityPropertySearchMinSurfaceTextView.apply {
            propertySearch.minSurface?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_surface_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxSurfaceTextView.apply {
            propertySearch.maxSurface?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_surface_slider_values)[1].toString()
            }
        }
    }

    private fun setSurfaceValues(minValue: Int, maxValue: Int) {
        propertySearch.apply {
            minSurface =
                if (minValue == resources.getInteger(R.integer.property_min_surface))
                    null else minValue
            maxSurface =
                if (maxValue == resources.getInteger(R.integer.property_max_surface))
                    null else maxValue
        }
        displaySurfaceValues()
    }

    // ROOMS

    private fun configureRoomsRangeSlider() {
        binding.activityPropertySearchRoomsRangeSlider.addOnChangeListener(this)
        displayNumberOfRoomsValues()
    }

    private fun displayNumberOfRoomsValues() {
        binding.activityPropertySearchMinNumberOfRoomsTextView.apply {
            propertySearch.minNumberOfRooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text = resources.getIntArray(R.array.initial_rooms_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxNumberOfRoomsTextView.apply {
            propertySearch.maxNumberOfRooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text = resources.getIntArray(R.array.initial_rooms_slider_values)[1].toString()
            }
        }
    }

    private fun setNumberOfRoomsValues(minValue: Int, maxValue: Int) {
        propertySearch.apply {
            minNumberOfRooms =
                if (minValue == resources.getInteger(R.integer.property_min_number_of_rooms))
                    null else minValue
            maxNumberOfRooms =
                if (maxValue == resources.getInteger(R.integer.property_max_number_of_rooms))
                    null else maxValue
        }
        displayNumberOfRoomsValues()
    }

    // BATHROOMS

    private fun configureBathroomsRangeSlider() {
        binding.activityPropertySearchBathroomsRangeSlider.addOnChangeListener(this)
        displayNumberOfBathroomsValues()
    }

    private fun displayNumberOfBathroomsValues() {
        binding.activityPropertySearchMinNumberOfBathroomsTextView.apply {
            propertySearch.minNumberOfBathrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bathrooms_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxNumberOfBathroomsTextView.apply {
            propertySearch.maxNumberOfBathrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bathrooms_slider_values)[1].toString()
            }
        }
    }

    private fun setNumberOfBathroomsValues(minValue: Int, maxValue: Int) {
        propertySearch.apply {
            minNumberOfBathrooms =
                if (minValue == resources.getInteger(R.integer.property_min_number_of_bathrooms))
                    null else minValue
            maxNumberOfBathrooms =
                if (maxValue == resources.getInteger(R.integer.property_max_number_of_bathrooms))
                    null else maxValue
        }
        displayNumberOfBathroomsValues()
    }

    //  BEDROOMS

    private fun configureBedroomsRangeSlider() {
        binding.activityPropertySearchBedroomsRangeSlider.addOnChangeListener(this)
        displayNumberOfBedroomsValues()
    }

    private fun displayNumberOfBedroomsValues() {
        binding.activityPropertySearchMinNumberOfBedroomsTextView.apply {
            propertySearch.minNumberOfBedrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bedrooms_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxNumberOfBedroomsTextView.apply {
            propertySearch.maxNumberOfBedrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bedrooms_slider_values)[1].toString()
            }
        }
    }

    private fun setNumberOfBedroomsValues(minValue: Int, maxValue: Int) {
        propertySearch.apply {
            minNumberOfBedrooms =
                if (minValue == resources.getInteger(R.integer.property_min_number_of_bedrooms))
                    null else minValue
            maxNumberOfBedrooms =
                if (maxValue == resources.getInteger(R.integer.property_max_number_of_bedrooms))
                    null else maxValue
        }
        displayNumberOfBedroomsValues()
    }

    //  MIN PHOTOS

    private fun configureMinPhotosSlider() {
        binding.activityPropertySearchMinPhotosSlider.addOnChangeListener { _, value, _ ->
            setNumberOfPhotosValue(value.toInt())
        }
        displayMinNumberOfPhotosValue()
    }

    private fun displayMinNumberOfPhotosValue() {
        binding.activityPropertySearchMinNumberOfPhotosTextView.apply {
            propertySearch.minNumberOfPhotos?.let {
                this.text = it.toString()
            } ?: run {
                this.text = resources.getInteger(R.integer.property_min_number_of_photos).toString()
            }
        }
    }

    private fun setNumberOfPhotosValue(value: Int) {
        propertySearch.minNumberOfPhotos =
            if (value == resources.getInteger(R.integer.property_min_number_of_photos))
                null else value
        displayMinNumberOfPhotosValue()
    }

    //  POINTS OF INTEREST

    private fun configurePointsOfInterestChips() {
        binding.apply {
            activityPropertySearchNearSchoolChip.setOnCheckedChangeListener(this@PropertySearchActivity)
            activityPropertySearchNearTransportsChip.setOnCheckedChangeListener(this@PropertySearchActivity)
            activityPropertySearchNearShopsChip.setOnCheckedChangeListener(this@PropertySearchActivity)
            activityPropertySearchNearParksChip.setOnCheckedChangeListener(this@PropertySearchActivity)
        }
    }

    //  DATE PICKERS

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

    private fun configureAndDisplayDatePicker() {
        var dateToDisplay = LocalDate.now()

        when (datePickerType) {
            DatePickerType.CREATION_START -> {
                propertySearch.minCreationTimestamp?.let {
                    dateToDisplay = LocalDate(it)
                }
            }
            DatePickerType.CREATION_STOP -> {
                propertySearch.maxCreationTimestamp?.let {
                    dateToDisplay = LocalDate(it)
                }
            }
            DatePickerType.SALE_START -> {
                propertySearch.minSaleTimestamp?.let {
                    dateToDisplay = LocalDate(it)
                }
            }
            DatePickerType.SALE_STOP -> {
                propertySearch.maxSaleTimestamp?.let {
                    dateToDisplay = LocalDate(it)
                }
            }
        }

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

    private fun configureCreationDateStartButton() {
        binding.activityPropertySearchCreationDateStartButton.setOnClickListener(this)
    }

    private fun configureCreationDateStopButton() {
        binding.activityPropertySearchCreationDateStopButton.setOnClickListener(this)
    }

    private fun configureSaleDateStartButton() {
        binding.activityPropertySearchSaleDateStartButton.setOnClickListener(this)
    }

    private fun configureSaleDateStopButton() {
        binding.activityPropertySearchSaleDateStopButton.setOnClickListener(this)
    }

    private fun displayDatesValues() {
        binding.activityPropertySearchCreationDateStartButton.apply {
            propertySearch.minCreationTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text = resources.getString(R.string.creation_after_text_button_search_activity)
            }
        }
        binding.activityPropertySearchCreationDateStopButton.apply {
            propertySearch.maxCreationTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text =
                    resources.getString(R.string.creation_before_text_button_search_activity)
            }
        }
        binding.activityPropertySearchSaleDateStartButton.apply {
            propertySearch.minSaleTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text = resources.getString(R.string.sold_after_text_button_search_activity)
            }
        }
        binding.activityPropertySearchSaleDateStopButton.apply {
            propertySearch.maxSaleTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text = resources.getString(R.string.sold_before_text_button_search_activity)
            }
        }
    }

    private fun configureFab() {
        binding.activityPropertySearchFab.setOnClickListener(this)
    }

    override fun onValueChange(slider: RangeSlider, value: Float, fromUser: Boolean) {
        val minValue = slider.values[0].toInt()
        val maxValue = slider.values[1].toInt()

        when (slider.id) {
            R.id.activity_property_search_price_range_slider -> {
                setPriceValues(minValue, maxValue)
            }
            R.id.activity_property_search_surface_range_slider -> {
                setSurfaceValues(minValue, maxValue)
            }
            R.id.activity_property_search_rooms_range_slider -> {
                setNumberOfRoomsValues(minValue, maxValue)
            }
            R.id.activity_property_search_bathrooms_range_slider -> {
                setNumberOfBathroomsValues(minValue, maxValue)
            }
            R.id.activity_property_search_bedrooms_range_slider -> {
                setNumberOfBedroomsValues(minValue, maxValue)
            }
        }
    }

    override fun onCheckedChanged(button: CompoundButton, isChecked: Boolean) {
        when (button.id) {
            R.id.activity_property_search_near_school_chip -> {
                propertySearch.nearSchool = isChecked
            }
            R.id.activity_property_search_near_transports_chip -> {
                propertySearch.nearTransports = isChecked
            }
            R.id.activity_property_search_near_shops_chip -> {
                propertySearch.nearShops = isChecked
            }
            R.id.activity_property_search_near_parks_chip -> {
                propertySearch.nearParks = isChecked
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        when (datePickerType) {
            DatePickerType.CREATION_START -> {
                propertySearch.minCreationTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                propertySearch.maxCreationTimestamp?.let {
                    if (LocalDate(propertySearch.minCreationTimestamp).isAfter(LocalDate(it))) {
                        propertySearch.maxCreationTimestamp = null
                    }
                }
            }
            DatePickerType.CREATION_STOP -> {
                propertySearch.maxCreationTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                propertySearch.minCreationTimestamp?.let {
                    if (LocalDate(propertySearch.maxCreationTimestamp).isBefore(LocalDate(it))) {
                        propertySearch.minCreationTimestamp = null
                    }
                }
            }
            DatePickerType.SALE_START -> {
                propertySearch.minSaleTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                propertySearch.maxSaleTimestamp?.let {
                    if (LocalDate(propertySearch.minSaleTimestamp).isAfter(LocalDate(it))) {
                        propertySearch.maxSaleTimestamp = null
                    }
                }
            }
            DatePickerType.SALE_STOP -> {
                propertySearch.maxSaleTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                propertySearch.minSaleTimestamp?.let {
                    if (LocalDate(propertySearch.maxSaleTimestamp).isBefore(LocalDate(it))) {
                        propertySearch.minSaleTimestamp = null
                    }
                }
            }
        }
        displayDatesValues()
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

                Log.e("search intent !! : ", propertySearch.toString())

                intent.putExtra("PROPERTY_SEARCH", propertySearch)

                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }
}