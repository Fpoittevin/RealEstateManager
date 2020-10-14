package com.ocr.francois.realestatemanager.models

data class PropertySearch(

    //  Price
    var minPrice: Int? = null,
    var maxPrice: Int? = null,

    //  Surface
    var minSurface: Int? = null,
    var maxSurface: Int? = null,

    //  Rooms
    var minNumberOfRooms: Int? = null,
    var maxNumberOfRooms: Int? = null,
    var minNumberOfBathrooms: Int? = null,
    var maxNumberOfBathrooms: Int? = null,
    var minNumberOfBedrooms: Int? = null,
    var maxNumberOfBedrooms: Int? = null,

    //  Points of interest
    var nearSchool: Boolean = false,
    var nearTransports: Boolean = false,
    var nearShops: Boolean = false,
    var nearParks: Boolean = false,

    //  Creation date
    var minCreationTimestamp: Long? = null,
    var maxCreationTimestamp: Long? = null,

    //  Sale date
    var isSold: Boolean? = null,
    var minSaleTimestamp: Long? = null,
    var maxSaleTimestamp: Long? = null,

    //  Min Number of Photo
    var minNumberOfPhotos: Int? = null
)