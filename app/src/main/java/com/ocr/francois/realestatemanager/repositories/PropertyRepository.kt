package com.ocr.francois.realestatemanager.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.google.android.gms.maps.model.LatLngBounds
import com.ocr.francois.realestatemanager.api.GeocoderService
import com.ocr.francois.realestatemanager.database.dao.PropertyDao
import com.ocr.francois.realestatemanager.models.GeocodeResponse
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.models.PropertySearch
import com.ocr.francois.realestatemanager.models.PropertyWithPhotos
import com.ocr.francois.realestatemanager.utils.LocationTool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PropertyRepository(private val propertyDao: PropertyDao, private val context: Context) {

    private var geocodeService: GeocoderService = GeocoderService
        .retrofit
        .create(GeocoderService::class.java)

    fun getPropertiesInBounds(bounds: LatLngBounds): LiveData<List<Property>> =
        propertyDao.selectPropertiesInBounds(
            bounds.southwest.latitude,
            bounds.northeast.latitude,
            bounds.southwest.longitude,
            bounds.northeast.longitude
        )

    fun getPropertiesWithPhotos(propertySearch: PropertySearch?): LiveData<List<PropertyWithPhotos>> {
        propertySearch?.let {
            Log.e("search ?", "yes")
            return propertyDao.getPropertiesBySearch(
                generateSqlLiteQuerySearch(propertySearch)
            )
        } ?: run {
            Log.e("search ?", "no")
            return propertyDao.getPropertiesWithPhotos()
        }
    }

    fun getProperty(id: Long): LiveData<Property> =
        propertyDao.getProperty(id)

    fun getPropertyWithPhotos(id: Long): LiveData<PropertyWithPhotos> =
        propertyDao.getPropertyWithPhotos(id)

    suspend fun insertPropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) {
        propertyDao.insertPropertyWithPhotos(propertyWithPhotos)
        getLocation(propertyWithPhotos.property)
    }

    suspend fun updatePropertyWithPhotos(propertyWithPhotos: PropertyWithPhotos) =
        propertyDao.updatePropertyWithPhotos(propertyWithPhotos)

    private fun generateSqlLiteQuerySearch(propertySearch: PropertySearch): SupportSQLiteQuery {

        val stringBuilder = StringBuilder()
        val args = ArrayList<Any>()

        fun java.lang.StringBuilder.addWhereOrAnd() {
            if(contains("WHERE")){
                append(" AND ")
            }else {
                append(" WHERE ")
            }
        }

        stringBuilder.append("SELECT * FROM Property AS Pr").apply {

            //  Price
            propertySearch.minPrice?.let {
                addWhereOrAnd()
                append("price >= ?")
                args.add(it)
            }
            propertySearch.maxPrice?.let {
                addWhereOrAnd()
                append("price <= ?")
                args.add(it)
            }

            //  Surface
            propertySearch.minSurface?.let {
                addWhereOrAnd()
                append("surface >= ?")
                args.add(it)
            }
            propertySearch.maxSurface?.let {
                addWhereOrAnd()
                append("surface <= ?")
                args.add(it)
            }

            //  Rooms
            propertySearch.minNumberOfRooms?.let {
                addWhereOrAnd()
                append("numberOfRooms >= ?")
                args.add(it)
            }
            propertySearch.maxNumberOfRooms?.let {
                addWhereOrAnd()
                append("numberOfRooms <= ?")
                args.add(it)
            }
            propertySearch.minNumberOfBathrooms?.let {
                addWhereOrAnd()
                append("numberOfBathrooms >= ?")
                args.add(it)
            }
            propertySearch.maxNumberOfBathrooms?.let {
                addWhereOrAnd()
                append("numberOfBathrooms <= ?")
                args.add(it)
            }
            propertySearch.minNumberOfBedrooms?.let {
                addWhereOrAnd()
                append("numberOfBedrooms >= ?")
                args.add(it)
            }
            propertySearch.maxNumberOfBedrooms?.let {
                addWhereOrAnd()
                append("numberOfBedrooms <= ?")
                args.add(it)
            }

            //  Points of interest
            if (propertySearch.nearSchool) {
                addWhereOrAnd()
                append("nearSchool = 1")
            }
            if (propertySearch.nearTransports) {
                addWhereOrAnd()
                append("nearTransports = 1")
            }
            if (propertySearch.nearShops) {
                addWhereOrAnd()
                append("nearShops = 1")
            }
            if (propertySearch.nearParks) {
                addWhereOrAnd()
                append("nearParks = 1")
            }

            //  Creation
            propertySearch.minCreationTimestamp?.let {
                addWhereOrAnd()
                append("creationTimestamp >= ?")
                args.add(it)
            }
            propertySearch.maxCreationTimestamp?.let {
                addWhereOrAnd()
                append("creationTimestamp <= ?")
                args.add(it)
            }

            //  Sale
            propertySearch.isSold?.let {
                addWhereOrAnd()
                if (it) append("saleTimestamp IS NOT NULL") // ??
            }
            propertySearch.minSaleTimestamp?.let {
                addWhereOrAnd()
                append("saleTimestamp >= ?")
                args.add(it)
            }
            propertySearch.maxSaleTimestamp?.let {
                addWhereOrAnd()
                append("saleTimestamp <= ?")
                args.add(it)
            }

            //  Photos
            propertySearch.minNumberOfPhotos?.let {
                addWhereOrAnd()
                append("(SELECT COUNT() FROM Photo WHERE propertyId = Pr.id) > ?")
                args.add(it)
            }

            //  Location
            propertySearch.city?.let {
                addWhereOrAnd()
                append("city LIKE ?")
                val string = "%$it%"
                args.add(string)
            }
        }
        return SimpleSQLiteQuery(stringBuilder.toString(), args.toArray())

    }

    private fun getLocation(property: Property) {

        val stringAddress = LocationTool.addressConcatenation(property, false)

        GeocoderService
            .retrofit
            .create(
                GeocoderService::class.java
            ).getLocation(stringAddress)
            .enqueue(object : Callback<GeocodeResponse> {

                override fun onResponse(
                    call: Call<GeocodeResponse>,
                    response: Response<GeocodeResponse>
                ) {
                    for (result in response.body()!!.results!!) {
                        Log.e("google response: ", result.geometry!!.location!!.lat!!.toString())
                    }

                }

                override fun onFailure(call: Call<GeocodeResponse>, t: Throwable) {

                }

            })
    }
}