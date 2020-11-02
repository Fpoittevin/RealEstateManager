package com.ocr.francois.realestatemanager.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class Photo(

    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var uri: String? = null,
    var description: String? = null,
    var propertyId: Long? = null
)