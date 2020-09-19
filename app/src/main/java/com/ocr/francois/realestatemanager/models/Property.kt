package com.ocr.francois.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Property(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var type: String? = null,
    var price: Int? = null,
    var surface: Float? = null,
    var numberOfRooms: Int? = null,
    var numberOfBathrooms: Int? = null,
    var numberOfBedrooms: Int? = null,
    var description: String? = null,
    var addressFirst: String? = null,
    var addressSecond: String? = null,
    var city: String? = null,
    var zipCode: String? = null,
    var creationTimestamp: Long? = null,
    var saleTimestamp: Long? = null,
    var estateAgent: String? = null,

    //points of interests
    var nearSchool: Boolean = false,
    var nearTransports: Boolean = false,
    var nearShops: Boolean = false,
    var nearParks: Boolean = false,


    var lat: Double? = null,
    var lng: Double? = null
)