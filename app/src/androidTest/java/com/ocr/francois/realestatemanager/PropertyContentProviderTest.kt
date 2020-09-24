package com.ocr.francois.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.provider.PropertyContentProvider
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyContentProviderTest {

    private lateinit var contentResolver: ContentResolver

    companion object {
        const val PROPERTY_ID = 1
    }
    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        Room.inMemoryDatabaseBuilder(
            context,
            RealEstateManagerDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
        contentResolver = context.contentResolver
    }

    @Test
    fun insertAndGetProperty() {

        contentResolver.insert(PropertyContentProvider.URI_PROPERTY, generateProperty())
        val cursor = contentResolver.query(ContentUris.withAppendedId(PropertyContentProvider.URI_PROPERTY,
            PROPERTY_ID.toLong()), null, null, null, null
        )
        assertThat(cursor, notNullValue())
        //assertEquals(cursor!!.count, 1)
        //assertTrue(cursor.moveToFirst())
        //assertEquals(cursor.getString(cursor.getColumnIndexOrThrow("description")), "Lorem Ipsum")
    }

    private fun generateProperty(): ContentValues {

        return ContentValues().apply {
            put("type", "loft")
            put("price", 1000000)
            put("surface", 345)
            put("numberOfRooms", 3)
            put("numberOfBathrooms", 1)
            put("numberOfBedrooms", 2)
            put("description","Lorem Ipsum")
            put("addressFirst", "240 5th Avenue")
            put("addressSecond", "appt 4")
            put("city", "New York City")
            put("zipCode", "10001")
            put("estateAgent", "Bob")

        }
    }
}