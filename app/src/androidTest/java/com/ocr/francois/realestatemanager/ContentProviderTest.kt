package com.ocr.francois.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.models.Property
import com.ocr.francois.realestatemanager.provider.PropertyContentProvider
import com.ocr.francois.realestatemanager.testUtils.LiveDataTestUtil.Companion.getValue
import com.ocr.francois.realestatemanager.utils.Utils
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ContentProviderTest {

    private lateinit var contentResolver: ContentResolver
    private lateinit var database: RealEstateManagerDatabase

    private val propertyTest = Property(
        null,
        "Loft",
        300000,
        350,
        3,
        1,
        2,
        "Lorem ipsum",
        "123 5th Ave",
        "apt 4",
        "New York",
        "10001",
        Utils.getTodayTimestamp(),
        null,
        "Jean",
        nearSchool = true,
        nearTransports = true,
        nearShops = true,
        nearParks = true,
        null,
        null

    )

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = RealEstateManagerDatabase.getInstance(context)
        contentResolver = context.contentResolver

        propertyTest.id = database.propertyDao().insertProperty(propertyTest)
    }

    @After
    fun deletePropertyTest() {
        database.propertyDao().deleteProperty(propertyTest)
    }


    @Test
    fun getPropertyTest() {

        assertTrue(propertyTest.id != null)

        propertyTest.id?.let { propertyTestId ->

            val propertyFromDatabase = getValue(
                database.propertyDao().getPropertyWithPhotos(
                    propertyTestId
                )
            )?.property

            val cursor = contentResolver.query(
                ContentUris.withAppendedId(
                    PropertyContentProvider.URI_PROPERTY,
                    propertyTestId
                ), null, null, null, null
            )

            ViewMatchers.assertThat(cursor, CoreMatchers.notNullValue())
            Assert.assertEquals(cursor!!.count, 1)
            assertTrue(cursor.moveToFirst())
            Assert.assertEquals(
                cursor.getString(cursor.getColumnIndexOrThrow("type")),
                propertyFromDatabase?.type
            )
            Assert.assertEquals(
                cursor.getInt(cursor.getColumnIndexOrThrow("price")),
                propertyFromDatabase?.price
            )
            Assert.assertEquals(
                cursor.getInt(cursor.getColumnIndexOrThrow("surface")),
                propertyFromDatabase?.surface
            )
            Assert.assertEquals(
                cursor.getInt(cursor.getColumnIndexOrThrow("numberOfRooms")),
                propertyFromDatabase?.numberOfRooms
            )
            Assert.assertEquals(
                cursor.getInt(cursor.getColumnIndexOrThrow("numberOfBathrooms")),
                propertyFromDatabase?.numberOfBathrooms
            )
            Assert.assertEquals(
                cursor.getInt(cursor.getColumnIndexOrThrow("numberOfBedrooms")),
                propertyFromDatabase?.numberOfBedrooms
            )
            Assert.assertEquals(
                cursor.getString(cursor.getColumnIndexOrThrow("description")),
                propertyFromDatabase?.description
            )
            Assert.assertEquals(
                cursor.getString(cursor.getColumnIndexOrThrow("addressFirst")),
                propertyFromDatabase?.addressFirst
            )
            Assert.assertEquals(
                cursor.getString(cursor.getColumnIndexOrThrow("addressSecond")),
                propertyFromDatabase?.addressSecond
            )
            Assert.assertEquals(
                cursor.getString(cursor.getColumnIndexOrThrow("city")),
                propertyFromDatabase?.city
            )
            Assert.assertEquals(
                cursor.getString(cursor.getColumnIndexOrThrow("zipCode")),
                propertyFromDatabase?.zipCode
            )
            Assert.assertEquals(
                cursor.getLong(cursor.getColumnIndexOrThrow("creationTimestamp")),
                propertyFromDatabase?.creationTimestamp
            )
            Assert.assertEquals(
                cursor.getString(cursor.getColumnIndexOrThrow("estateAgent")),
                propertyFromDatabase?.estateAgent
            )
        }
    }
}