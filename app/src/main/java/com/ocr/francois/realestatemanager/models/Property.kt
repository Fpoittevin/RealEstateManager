package com.ocr.francois.realestatemanager.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity
data class Property(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val type: String,
    val price: Int,
    val surface: Float,
    val numberOfRooms: Int,
    val description: String,
    val address: String,
    val district: String,
    val pointsOfInterest: String,
    //val status: Status,
    //val creationTimestamp: Timestamp,
    //val saleTimestamp: Timestamp,
    val estateAgent: String
) {
    enum class Status {
        SOLD,
        AVAILABLE
    }
}