package com.ocr.francois.realestatemanager.models

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Property(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var type: String? = null,
    var price: Int? = null,
    var surface: Int? = null,
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
    var lng: Double? = null,

    @Ignore
    var formattedPrice: String? = null
) {

    companion object {
        fun fromContentValues(values: ContentValues): Property {
            val property = Property()
            values.run {
                if (containsKey("type")) property.type =
                    getAsString("type")
                if (containsKey("price")) property.price =
                    getAsInteger("price")
                if (containsKey("surface")) property.surface =
                    getAsInteger("surface")
                if (containsKey("numberOfRooms")) property.numberOfRooms =
                    getAsInteger("numberOfRooms")
                if (containsKey("numberOfBathrooms")) property.numberOfBathrooms =
                    getAsInteger("numberOfBathrooms")
                if (containsKey("numberOfBedrooms")) property.numberOfBedrooms =
                    getAsInteger("numberOfBedrooms")
                if (containsKey("description")) property.description =
                    getAsString("description")
                if (containsKey("addressFirst")) property.addressFirst =
                    getAsString("addressFirst")
                if (containsKey("addressSecond")) property.addressSecond =
                    getAsString("addressSecond")
                if (containsKey("city")) property.city =
                    getAsString("city")
                if (containsKey("zipCode")) property.zipCode =
                    getAsString("zipCode")
                if (containsKey("creationTimestamp")) property.creationTimestamp =
                    getAsLong("creationTimestamp")
                if (containsKey("saleTimestamp")) property.saleTimestamp =
                    getAsLong("saleTimestamp")
                if (containsKey("estateAgent")) property.estateAgent =
                    getAsString("estateAgent")
                if (containsKey("nearSchool")) property.nearSchool =
                    getAsBoolean("nearSchool")
                if (containsKey("nearTransport")) property.nearTransports =
                    getAsBoolean("nearTransport")
                if (containsKey("nearShops")) property.nearShops =
                    getAsBoolean("nearShops")
                if (containsKey("nearParks")) property.nearParks =
                    getAsBoolean("nearParks")

                if (containsKey("lat")) property.lat =
                    getAsDouble("lat")
                if (containsKey("lng")) property.lng =
                    getAsDouble("lng")
            }
            return property
        }
    }
}