package com.ocr.francois.realestatemanager.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class GeocodeResult {
    @SerializedName("geometry")
    @Expose
    var geometry: Geometry? = null
}

class GeocodeResponse {
    @SerializedName("results")
    @Expose
    var results: List<GeocodeResult>? = null
}

class Geometry {
    @SerializedName("location")
    @Expose
    var location: Location? = null
}

class Location {
    @SerializedName("lat")
    @Expose
    var lat: Double? = null

    @SerializedName("lng")
    @Expose
    var lng: Double? = null
}