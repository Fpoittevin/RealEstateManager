package com.ocr.francois.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class Property(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
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