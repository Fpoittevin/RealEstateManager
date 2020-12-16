package com.ocr.francois.realestatemanager

import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.provider.PropertyContentProvider
import com.ocr.francois.realestatemanager.testUtils.LiveDataTestUtil.Companion.getValue
import org.hamcrest.CoreMatchers
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class ContentProviderTest {

    private lateinit var contentResolver: ContentResolver
    private lateinit var database: RealEstateManagerDatabase

    companion object {
        const val PROPERTY_ID = 1
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = RealEstateManagerDatabase.getInstance(context)
        contentResolver = context.contentResolver
    }

    @Test
    fun getPropertyTest() {

        val propertyFromDatabase = getValue(
            database.propertyDao().getPropertyWithPhotos(
                PROPERTY_ID.toLong()
            )
        )?.property

        val cursor = contentResolver.query(
            ContentUris.withAppendedId(
                PropertyContentProvider.URI_PROPERTY,
                PROPERTY_ID.toLong()
            ), null, null, null, null
        )
        ViewMatchers.assertThat(cursor, CoreMatchers.notNullValue())
        Assert.assertEquals(cursor!!.count, 1)
        Assert.assertTrue(cursor.moveToFirst())
        Assert.assertEquals(
            cursor.getString(cursor.getColumnIndexOrThrow("type")),
            propertyFromDatabase?.type
        )
        Assert.assertEquals(
            cursor.getInt(cursor.getColumnIndexOrThrow("surface")),
            propertyFromDatabase?.surface
        )
        Assert.assertEquals(
            cursor.getInt(cursor.getColumnIndexOrThrow("price")),
            propertyFromDatabase?.price
        )
    }
}