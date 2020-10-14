package com.ocr.francois.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos

class PropertyRepository(private val propertyDao: PropertyDao) {

    fun getAllProperties(): LiveData<List<Property>> = propertyDao.selectAllProperties()

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

        //TODO("delete first AND")

        stringBuilder.append("SELECT * FROM Property WHERE ").apply {

            //  Price
            propertySearch.minPrice?.let {

                append(" AND price >= ?")
                args.add(it)
            }
            propertySearch.maxPrice?.let {
                append(" AND price <= ?")
                args.add(it)
            }

            //  Surface
            propertySearch.minSurface?.let {
                append(" AND surface >= ?")
                args.add(it)
            }
            propertySearch.maxSurface?.let {
                append(" AND surface <= ?")
                args.add(it)
            }

            //  Rooms
            propertySearch.minNumberOfRooms?.let {
                append(" AND numberOfRooms >= ?")
                args.add(it)
            }
            propertySearch.maxNumberOfRooms?.let {
                append(" AND numberOfRooms <= ?")
                args.add(it)
            }
            propertySearch.minNumberOfBathrooms?.let {
                append(" AND numberOfBathrooms >= ?")
                args.add(it)
            }
            propertySearch.maxNumberOfBathrooms?.let {
                append(" AND numberOfBathrooms <= ?")
                args.add(it)
            }
            propertySearch.minNumberOfBedrooms?.let {
                append(" AND numberOfBedrooms >= ?")
                args.add(it)
            }
            propertySearch.maxNumberOfBedrooms?.let {
                append(" AND numberOfBedrooms <= ?")
                args.add(it)
            }

            //  Points of interest
            if (propertySearch.nearSchool)
                append(" AND nearSchool = 1")
            if (propertySearch.nearTransports)
                append(" AND nearTransports = 1")
            if (propertySearch.nearShops)
                append(" AND nearShops = 1")
            if (propertySearch.nearParks)
                append(" AND nearParks = 1")

            //  Creation
            propertySearch.minCreationTimestamp?.let {
                append(" AND creationTimestamp >= ?")
                args.add(it)
            }
            propertySearch.maxCreationTimestamp?.let {
                append(" AND creationTimestamp <= ?")
                args.add(it)
            }

            //  Sale
            propertySearch.isSold?.let {
                if (it) append(" ") // ??
            }
            propertySearch.minSaleTimestamp?.let {
                append(" AND saleTimestamp >= ?")
                args.add(it)
            }
            propertySearch.maxSaleTimestamp?.let {
                append(" AND saleTimestamp <= ?")
                args.add(it)
            }

            //  Photos
            propertySearch.minNumberOfPhotos?.let {
                append(" AND (SELECT COUNT() FROM Photo WHERE propertyId = id) > ?")
                args.add(it)
            }
        }
        return SimpleSQLiteQuery(stringBuilder.toString(), args.toArray())
    }
}