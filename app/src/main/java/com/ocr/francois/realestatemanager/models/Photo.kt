package com.ocr.francois.realestatemanager.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = Property::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("propertyId"),
    onDelete = ForeignKey.CASCADE)]
)
data class Photo(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var uri: String? = null,
    var propertyId: Long? = null
)