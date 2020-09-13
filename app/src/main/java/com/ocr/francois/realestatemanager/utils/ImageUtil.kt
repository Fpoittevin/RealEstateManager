package com.ocr.francois.realestatemanager.utils

import android.content.Context
import android.graphics.Bitmap
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class ImageUtil {

    companion object {
        fun saveBitmapToFile(context: Context, bitmap: Bitmap, file: File): File? {

            var fos: FileOutputStream? = null

            return try {

                //Convert bitmap to byte array
                val bos = ByteArrayOutputStream()
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    bos
                ) //Save it in JPEG
                val bitmapData = bos.toByteArray()

                //Write the bytes in file
                fos = FileOutputStream(file).also {
                    it.write(bitmapData)
                }
                file
            } catch (e: Exception) {
                //TODO: ERROR GESTION
                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()
                file // it will return null
            } finally {
                fos?.let {
                    it.flush()
                    it.close()
                }
            }
        }
    }
}