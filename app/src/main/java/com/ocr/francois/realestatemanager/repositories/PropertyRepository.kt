package com.ocr.francois.realestatemanager.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos

class PropertyRepository(private val propertyDao: PropertyDao) {

    fun getPropertiesInBounds(bounds: LatLngBounds): LiveData<List<Property>> =
        propertyDao.selectPropertiesInBounds(
            bounds.southwest.latitude,
            bounds.northeast.latitude,
            bounds.southwest.longitude,
            bounds.northeast.longitude
        )

    fun getPropertiesWithPhotos(propertySearch: PropertySearch?): LiveData<List<PropertyWithPhotos>> {
        propertySearch?.let {
            return propertyDao.getPropertiesBySearch(generateSqlLiteQuerySearch(propertySearch))
        } ?: run {
            return propertyDao.getPropertiesWithPhotos()
        }
    }

    fun getPropertyWithPhotos(id: Long): LiveData<PropertyWithPhotos> =
        propertyDao.getPropertyWithPhotos(id)

    suspend fun insertPropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) =
        propertyDao.insertPropertyWithPhotos(propertyWithPhotos)

    suspend fun updatePropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) =
        propertyDao.updatePropertyWithPhotos(propertyWithPhotos)

    private fun generateSqlLiteQuerySearch(propertySearch: PropertySearch): SupportSQLiteQuery {

        val stringBuilder = StringBuilder()
        val args = ArrayList<Any>()

        stringBuilder.append("SELECT * FROM Property WHERE ").apply {

            //  Price
            propertySearch.minPrice?.let {

                append("price >= ? AND ")
                args.add(it)
            }
            propertySearch.maxPrice?.let {
                append("price <= ? AND ")
                args.add(it)
            }

            //  Surface
            propertySearch.minSurface?.let {
                append("surface >= ? AND ")
                args.add(it)
            }
            propertySearch.maxSurface?.let {
                append("surface <= ? AND ")
                args.add(it)
            }

            //  Rooms
            propertySearch.minNumberOfRooms?.let {
                append("numberOfRooms >= ? AND ")
                args.add(it)
            }
            propertySearch.maxNumberOfRooms?.let {
                append("numberOfRooms <= ? AND ")
                args.add(it)
            }
            propertySearch.minNumberOfBathrooms?.let {
                append("numberOfBathrooms >= ? AND ")
                args.add(it)
            }
            propertySearch.maxNumberOfBathrooms?.let {
                append("numberOfBathrooms <= ? AND ")
                args.add(it)
            }
            propertySearch.minNumberOfBedrooms?.let {
                append("numberOfBedrooms >= ? AND ")
                args.add(it)
            }
            propertySearch.maxNumberOfBedrooms?.let {
                append("numberOfBedrooms <= ? AND ")
                args.add(it)
            }

            //  Points of interest
            if (propertySearch.nearSchool)
                append("nearSchool = 1 AND ")
            if (propertySearch.nearTransports)
                append("nearTransports = 1 AND ")
            if (propertySearch.nearShops)
                append("nearShops = 1 AND ")
            if (propertySearch.nearParks)
                append("nearParks = 1 AND ")

            //  Creation
            propertySearch.minCreationTimestamp?.let {
                append("creationTimestamp >= ? AND ")
                args.add(it)
            }
            propertySearch.maxCreationTimestamp?.let {
                append("creationTimestamp <= ? AND ")
                args.add(it)
            }

            //  Sale
            propertySearch.isSold?.let {
                if (it) append(" ") // ??
            }
            propertySearch.minSaleTimestamp?.let {
                append("saleTimestamp >= ? AND ")
                args.add(it)
            }
            propertySearch.maxSaleTimestamp?.let {
                append("saleTimestamp <= ? AND ")
                args.add(it)
            }

            //  Photos
            propertySearch.minNumberOfPhotos?.let {
                append("(SELECT COUNT() FROM Photo WHERE propertyId = id) > ? AND ")
                args.add(it)
            }
            setLength(length - 5)
            Log.e("request", SimpleSQLiteQuery(stringBuilder.toString(), args.toArray()).toString())
        }
        return SimpleSQLiteQuery(stringBuilder.toString(), args.toArray())
    }
}