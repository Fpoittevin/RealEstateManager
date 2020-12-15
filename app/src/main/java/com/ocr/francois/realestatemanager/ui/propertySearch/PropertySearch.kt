package com.ocr.francois.realestatemanager.ui.propertySearch

import android.os.Parcelable
import android.util.Log
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import kotlinx.parcelize.Parcelize

@Parcelize
class PropertySearch(
    private var _minMaxPrice: Array<Int?> = arrayOf(null, null),
    private var _minMaxSurface: Array<Int?> = arrayOf(null, null),
    private var _minMaxRooms: Array<Int?> = arrayOf(null, null),
    private var _minMaxBathrooms: Array<Int?> = arrayOf(null, null),
    private var _minMaxBedrooms: Array<Int?> = arrayOf(null, null),
    private var _minNumberOfPhotos: Int? = null,
    private var _nearSchool: Boolean = false,
    private var _nearTransports: Boolean = false,
    private var _nearShops: Boolean = false,
    private var _nearParks: Boolean = false,
    private var _isSold: Boolean? = null,
    private var _minCreationTimestamp: Long? = null,
    private var _maxCreationTimestamp: Long? = null,
    private var _minSaleTimestamp: Long? = null,
    private var _maxSaleTimestamp: Long? = null,
    private var _city: String? = null

) : BaseObservable(), Parcelable {

    var minMaxPrice: Array<Int?>
        @Bindable
        get() = _minMaxPrice
        set(value) {
            _minMaxPrice = value
            notifyPropertyChanged(BR.minMaxPrice)
        }

    var minMaxSurface: Array<Int?>
        @Bindable
        get() = _minMaxSurface
        set(value) {
            _minMaxSurface = value
            notifyPropertyChanged(BR.minMaxSurface)
        }

    var minMaxRooms: Array<Int?>
        @Bindable
        get() = _minMaxRooms
        set(value) {
            _minMaxRooms = value
            notifyPropertyChanged(BR.minMaxRooms)
        }

    var minMaxBathrooms: Array<Int?>
        @Bindable
        get() = _minMaxBathrooms
        set(value) {
            _minMaxBathrooms = value
            notifyPropertyChanged(BR.minMaxBathrooms)
        }

    var minMaxBedrooms: Array<Int?>
        @Bindable
        get() = _minMaxBedrooms
        set(value) {
            Log.e("bedrooms", "change")
            _minMaxBedrooms = value
            notifyPropertyChanged(BR.minMaxBedrooms)
        }

    var minNumberOfPhotos: Int?
        @Bindable
        get() = _minNumberOfPhotos
        set(value) {
            _minNumberOfPhotos = value
            notifyPropertyChanged(BR.minNumberOfPhotos)
        }

    var nearSchool: Boolean
        @Bindable
        get() = _nearSchool
        set(value) {
            _nearSchool = value
            notifyPropertyChanged(BR.nearSchool)
        }

    var nearTransports: Boolean
        @Bindable
        get() = _nearTransports
        set(value) {
            _nearTransports = value
            notifyPropertyChanged(BR.nearTransports)
        }

    var nearShops: Boolean
        @Bindable
        get() = _nearShops
        set(value) {
            _nearShops = value
            notifyPropertyChanged(BR.nearShops)
        }

    var nearParks: Boolean
        @Bindable
        get() = _nearParks
        set(value) {
            _nearParks = value
            notifyPropertyChanged(BR.nearParks)
        }

    var isSold: Boolean?
        get() = _isSold
        set(value) {
            _isSold = value
        }

    var minCreationTimestamp: Long?
        @Bindable
        get() = _minCreationTimestamp
        set(value) {
            _minCreationTimestamp = value
            notifyPropertyChanged(BR.minCreationTimestamp)
        }
    var maxCreationTimestamp: Long?
        @Bindable
        get() = _maxCreationTimestamp
        set(value) {
            _maxCreationTimestamp = value
            notifyPropertyChanged(BR.maxCreationTimestamp)
        }

    var minSaleTimestamp: Long?
        @Bindable
        get() = _minSaleTimestamp
        set(value) {
            _minSaleTimestamp = value
            notifyPropertyChanged(BR.minSaleTimestamp)
        }
    var maxSaleTimestamp: Long?
        @Bindable
        get() = _maxSaleTimestamp
        set(value) {
            _maxSaleTimestamp = value
            notifyPropertyChanged(BR.maxSaleTimestamp)
        }

    var city: String?
        @Bindable
        get() = _city
        set(value) {
            _city = value
            notifyPropertyChanged(BR.city)
        }


    private fun getIntStringValue(intValue: Int?): String {
        with(StringBuilder()) {
            intValue?.let {
                append(it.toString())
            } ?: run {
                append("null")
            }
            append(", ")
            return toString()
        }
    }

    private fun getBooleanStringValue(booleanValue: Boolean?): String {
        with(StringBuilder()) {
            booleanValue?.let {
                append(it.toString())
            } ?: run {
                append("null")
            }
            append(", ")
            return toString()
        }
    }

    private fun getTimestampStringValue(timestampValue: Long?): String {
        with(StringBuilder()) {
            timestampValue?.let {
                append(it.toString())
            } ?: run {
                append("null")
            }
            append(", ")
            return toString()
        }
    }

    override fun toString(): String {

        with(StringBuilder()) {
            append("minPrice: ")
            append(getIntStringValue(minMaxPrice[0]))
            append("maxPrice: ")
            append(getIntStringValue(minMaxPrice[1]))
            append("minSurface: ")
            append(getIntStringValue(minMaxSurface[0]))
            append("maxSurface: ")
            append(getIntStringValue(minMaxSurface[1]))

            append("minRooms: ")
            append(getIntStringValue(minMaxRooms[0]))
            append("maxRooms: ")
            append(getIntStringValue(minMaxRooms[1]))

            append("minBathrooms: ")
            append(getIntStringValue(minMaxBathrooms[0]))
            append("maxBathrooms: ")
            append(getIntStringValue(minMaxBathrooms[1]))

            append("minBedrooms: ")
            append(getIntStringValue(minMaxBedrooms[0]))
            append("maxBedrooms: ")
            append(getIntStringValue(minMaxBedrooms[1]))

            append("nearSchool: ")
            append(getBooleanStringValue(nearSchool))

            append("nearTransports: ")
            append(getBooleanStringValue(nearTransports))

            append("nearShops: ")
            append(getBooleanStringValue(nearShops))

            append("nearParks: ")
            append(getBooleanStringValue(nearParks))

            append("creationStart: ")
            append(getTimestampStringValue(minCreationTimestamp))

            append("creationStop: ")
            append(getTimestampStringValue(maxCreationTimestamp))

            append("isSold: ")
            append(
                getBooleanStringValue(isSold)
            )

            append("soldStart: ")
            append(getTimestampStringValue(minSaleTimestamp))

            append("soldStop: ")
            append(getTimestampStringValue(maxSaleTimestamp))

            append("minNumberOfPhotos: ")
            append(getIntStringValue(minNumberOfPhotos))

            append("city: ")
            append(city)

            return toString()
        }
    }
}