package com.ocr.francois.realestatemanager.models

import java.sql.Timestamp

data class Property(
    val id: String,
    var type: String,
    var price: Float,
    var surface: Float,
    var numberOfRooms: Int,
    var description: String,
    var address: String,
    var pointsOfInterest: String,
    var status: Status,
    var creationTimestamp: Timestamp,
    var saleTimestamp: Timestamp,
    var estateAgent: String
) {
    enum class Status {
        SOLD,
        AVAILABLE
    }
}