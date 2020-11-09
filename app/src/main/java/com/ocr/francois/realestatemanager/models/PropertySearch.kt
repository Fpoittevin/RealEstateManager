package com.ocr.francois.realestatemanager.models

import android.os.Parcel
import android.os.Parcelable

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
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Int::class.java.classLoader) as? Int
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(minPrice)
        parcel.writeValue(maxPrice)
        parcel.writeValue(minSurface)
        parcel.writeValue(maxSurface)
        parcel.writeValue(minNumberOfRooms)
        parcel.writeValue(maxNumberOfRooms)
        parcel.writeValue(minNumberOfBathrooms)
        parcel.writeValue(maxNumberOfBathrooms)
        parcel.writeValue(minNumberOfBedrooms)
        parcel.writeValue(maxNumberOfBedrooms)
        parcel.writeByte(if (nearSchool) 1 else 0)
        parcel.writeByte(if (nearTransports) 1 else 0)
        parcel.writeByte(if (nearShops) 1 else 0)
        parcel.writeByte(if (nearParks) 1 else 0)
        parcel.writeValue(minCreationTimestamp)
        parcel.writeValue(maxCreationTimestamp)
        parcel.writeValue(isSold)
        parcel.writeValue(minSaleTimestamp)
        parcel.writeValue(maxSaleTimestamp)
        parcel.writeValue(minNumberOfPhotos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PropertySearch> {
        override fun createFromParcel(parcel: Parcel): PropertySearch {
            return PropertySearch(parcel)
        }

        override fun newArray(size: Int): Array<PropertySearch?> {
            return arrayOfNulls(size)
        }
    }
}