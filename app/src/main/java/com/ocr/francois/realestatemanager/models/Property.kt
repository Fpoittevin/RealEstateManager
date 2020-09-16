package com.ocr.francois.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Property(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var type: String?,
    var price: Int?,
    var surface: Float?,
    var numberOfRooms: Int?,
    var numberOfBathrooms: Int?,
    var numberOfBedrooms: Int?,
    var description: String?,
    var addressFirst: String?,
    var addressSecond: String?,
    var district: String?,
    var city: String?,
    var zipCode: String?,
    var state: String?,
    var creationTimestamp: Long?,
    var saleTimestamp: Long?,
    var estateAgent: String?,

    //points of interests
    var nearSchool: Boolean = false,
    var nearTransports: Boolean = false,
    var nearShops: Boolean = false,
    var nearParks: Boolean = false,


    var lat: Double?,
    var lng: Double?
)