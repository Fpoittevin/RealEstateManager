package com.ocr.francois.realestatemanager.utils

import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.ocr.francois.realestatemanager.events.FailureEvent
import org.greenrobot.eventbus.EventBus
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ImageUtil {

    companion object {
        fun saveBitmapToFile(bitmap: Bitmap, file: File): File? {

            var fos: FileOutputStream? = null

            return try {

                val bos = ByteArrayOutputStream()
                bitmap.compress(
                    Bitmap.CompressFormat.JPEG,
                    100,
                    bos
                )
                val bitmapData = bos.toByteArray()

                fos = FileOutputStream(file).also {
                    it.write(bitmapData)
                }
                file
            } catch (e: Exception) {
                EventBus.getDefault()
                    .post(FailureEvent(e.message.toString()))
                file
            } finally {
                fos?.let {
                    it.flush()
                    it.close()
                }
            }
        }

        fun createImageFile(activity: Activity): File {
            val timeStamp: String =
                SimpleDateFormat("yyyyMMdd_HHmmss", Locale.FRENCH).format(Date())
            val storageDir: File? =
                activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)

            return File.createTempFile(
                "JPEG_${timeStamp}_",
                ".jpg",
                storageDir
            )
        }

        fun deleteFileFromUri(uri: Uri) {

            val file = File(uri.path!!)
            if (file.exists()) {
                file.delete()
            }
        }
    }
}