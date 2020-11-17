package com.ocr.francois.realestatemanager.ui.propertySearch

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import android.widget.DatePicker
import com.google.android.material.slider.RangeSlider
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.databinding.ActivityPropertySearchBinding
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.ui.base.BaseActivity
import com.ocr.francois.realestatemanager.utils.Utils
import org.joda.time.LocalDate

class PropertySearchActivity : BaseActivity(),
    RangeSlider.OnChangeListener,
    CompoundButton.OnCheckedChangeListener,
    View.OnClickListener,
    DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityPropertySearchBinding
    private lateinit var datePickerDialog: DatePickerDialog
    private lateinit var datePickerType: DatePickerType
    private var propertySearch: PropertySearch? = null

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
        binding = ActivityPropertySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configureToolbar(binding.activityPropertySearchToolbar, true)
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
        configureSoldSwitch()
        configureSaleDateStartButton()
        configureSaleDateStopButton()
        configureLocationTextInput()

        configureFab()
    }

    private fun getPropertySearch(): PropertySearch {
        propertySearch?.let {
            return it
        } ?: run {
            propertySearch = PropertySearch()
            return propertySearch as PropertySearch
        }
    }

    // PRICE

    private fun configurePriceRangeSlider() {
        binding.activityPropertySearchPriceRangeSlider.addOnChangeListener(this)
        displayPriceValues()
    }

    private fun displayPriceValues() {
        binding.activityPropertySearchMinPriceTextView.apply {
            getPropertySearch().minPrice?.let {
                this.text =
                    Utils.formatNumber(it)
            } ?: run {
                this.text =
                    Utils.formatNumber(resources.getIntArray(R.array.initial_price_slider_values)[0])
            }
            binding.activityPropertySearchMaxPriceTextView.apply {
                getPropertySearch().maxPrice?.let {
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
        getPropertySearch().apply {
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
            getPropertySearch().minSurface?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_surface_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxSurfaceTextView.apply {
            getPropertySearch().maxSurface?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_surface_slider_values)[1].toString()
            }
        }
    }

    private fun setSurfaceValues(minValue: Int, maxValue: Int) {
        getPropertySearch().apply {
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
            getPropertySearch().minNumberOfRooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text = resources.getIntArray(R.array.initial_rooms_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxNumberOfRoomsTextView.apply {
            getPropertySearch().maxNumberOfRooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text = resources.getIntArray(R.array.initial_rooms_slider_values)[1].toString()
            }
        }
    }

    private fun setNumberOfRoomsValues(minValue: Int, maxValue: Int) {
        getPropertySearch().apply {
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
            getPropertySearch().minNumberOfBathrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bathrooms_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxNumberOfBathroomsTextView.apply {
            getPropertySearch().maxNumberOfBathrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bathrooms_slider_values)[1].toString()
            }
        }
    }

    private fun setNumberOfBathroomsValues(minValue: Int, maxValue: Int) {
        getPropertySearch().apply {
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
            getPropertySearch().minNumberOfBedrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bedrooms_slider_values)[0].toString()
            }
        }
        binding.activityPropertySearchMaxNumberOfBedroomsTextView.apply {
            getPropertySearch().maxNumberOfBedrooms?.let {
                this.text = it.toString()
            } ?: run {
                this.text =
                    resources.getIntArray(R.array.initial_bedrooms_slider_values)[1].toString()
            }
        }
    }

    private fun setNumberOfBedroomsValues(minValue: Int, maxValue: Int) {
        getPropertySearch().apply {
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
            getPropertySearch().minNumberOfPhotos?.let {
                this.text = it.toString()
            } ?: run {
                this.text = resources.getInteger(R.integer.property_min_number_of_photos).toString()
            }
        }
    }

    private fun setNumberOfPhotosValue(value: Int) {
        getPropertySearch().minNumberOfPhotos =
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
                getPropertySearch().minCreationTimestamp?.let {
                    dateToDisplay = LocalDate(it)
                }
            }
            DatePickerType.CREATION_STOP -> {
                getPropertySearch().maxCreationTimestamp?.let {
                    dateToDisplay = LocalDate(it)
                }
            }
            DatePickerType.SALE_START -> {
                getPropertySearch().minSaleTimestamp?.let {
                    dateToDisplay = LocalDate(it)
                }
            }
            DatePickerType.SALE_STOP -> {
                getPropertySearch().maxSaleTimestamp?.let {
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

    private fun configureSoldSwitch() {
        binding.activityPropertySearchSoldSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                getPropertySearch().isSold = true
                binding.activityPropertySearchSaleDateStartButton.isEnabled = true
                binding.activityPropertySearchSaleDateStopButton.isEnabled = true
            } else {
                getPropertySearch().apply {
                    isSold = null
                    minSaleTimestamp = null
                    maxSaleTimestamp = null
                }
                binding.activityPropertySearchSaleDateStartButton.isEnabled = false
                binding.activityPropertySearchSaleDateStopButton.isEnabled = false
            }
        }
    }

    private fun configureSaleDateStartButton() {
        binding.activityPropertySearchSaleDateStartButton.apply {
            isEnabled = false
            setOnClickListener(this@PropertySearchActivity)
        }
    }

    private fun configureSaleDateStopButton() {
        binding.activityPropertySearchSaleDateStopButton.apply {
            isEnabled = false
            setOnClickListener(this@PropertySearchActivity)
        }
    }

    private fun displayDatesValues() {
        binding.activityPropertySearchCreationDateStartButton.apply {
            getPropertySearch().minCreationTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text = resources.getString(R.string.creation_after)
            }
        }
        binding.activityPropertySearchCreationDateStopButton.apply {
            getPropertySearch().maxCreationTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text =
                    resources.getString(R.string.creation_before)
            }
        }
        binding.activityPropertySearchSaleDateStartButton.apply {
            getPropertySearch().minSaleTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text = resources.getString(R.string.sold_after)
            }
        }
        binding.activityPropertySearchSaleDateStopButton.apply {
            getPropertySearch().maxSaleTimestamp?.let {
                this.text = Utils.formatDate(LocalDate(it))
            } ?: run {
                this.text = resources.getString(R.string.sold_before)
            }
        }
    }

    private fun configureLocationTextInput() {
        binding.activityPropertySearchCityTextInput.addTextChangedListener(object :
            TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(value: Editable?) {
                getPropertySearch().city = value.toString()
            }

        })
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
                getPropertySearch().nearSchool = isChecked
            }
            R.id.activity_property_search_near_transports_chip -> {
                getPropertySearch().nearTransports = isChecked
            }
            R.id.activity_property_search_near_shops_chip -> {
                getPropertySearch().nearShops = isChecked
            }
            R.id.activity_property_search_near_parks_chip -> {
                getPropertySearch().nearParks = isChecked
            }
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        when (datePickerType) {
            DatePickerType.CREATION_START -> {
                getPropertySearch().minCreationTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                getPropertySearch().maxCreationTimestamp?.let {
                    if (LocalDate(getPropertySearch().minCreationTimestamp).isAfter(LocalDate(it))) {
                        getPropertySearch().maxCreationTimestamp = null
                    }
                }
            }
            DatePickerType.CREATION_STOP -> {
                getPropertySearch().maxCreationTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                getPropertySearch().minCreationTimestamp?.let {
                    if (LocalDate(getPropertySearch().maxCreationTimestamp).isBefore(LocalDate(it))) {
                        getPropertySearch().minCreationTimestamp = null
                    }
                }
            }
            DatePickerType.SALE_START -> {
                getPropertySearch().minSaleTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                getPropertySearch().maxSaleTimestamp?.let {
                    if (LocalDate(getPropertySearch().minSaleTimestamp).isAfter(LocalDate(it))) {
                        getPropertySearch().maxSaleTimestamp = null
                    }
                }
            }
            DatePickerType.SALE_STOP -> {
                getPropertySearch().maxSaleTimestamp =
                    Utils.getTimestampFromDatePicker(year, month, dayOfMonth)
                getPropertySearch().minSaleTimestamp?.let {
                    if (LocalDate(getPropertySearch().maxSaleTimestamp).isBefore(LocalDate(it))) {
                        getPropertySearch().minSaleTimestamp = null
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

                propertySearch?.let {
                    intent.putExtra(PROPERTY_SEARCH_KEY, propertySearch)
                    setResult(RESULT_OK, intent)
                } ?: run {
                    setResult(RESULT_CANCELED, intent)
                }
                finish()
            }
        }
    }
}