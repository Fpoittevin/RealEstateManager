package com.ocr.francois.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.ocr.francois.realestatemanager.database.RealEstateManagerDatabase
import com.ocr.francois.realestatemanager.models.Property

class PropertyContentProvider : ContentProvider() {

    companion object {
        const val AUTHORITY = "com.ocr.francois.realestatemanager.provider"
        val TABLE_NAME: String = Property::class.java.simpleName
        val URI_PROPERTY: Uri = Uri.parse("content://$AUTHORITY/$TABLE_NAME")
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val id = ContentUris.parseId(uri)
        return RealEstateManagerDatabase
            .getInstance(context!!)
            .propertyDao()
            .getPropertyWithCursor(id).apply {
                setNotificationUri(context!!.contentResolver, uri)
            }
    }

    override fun getType(uri: Uri): String {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, values: ContentValues?): Nothing? = null

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?) = 0

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ) = 0
}