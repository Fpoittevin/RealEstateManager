package com.ocr.francois.realestatemanager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ocr.francois.realestatemanager.R
import com.ocr.francois.realestatemanager.ui.MainActivity
import com.ocr.francois.realestatemanager.ui.propertyDetails.PropertyDetailsFragment

class NotificationSender(private val context: Context) {

    companion object {
        const val CHANNEL_ID = "RealEstateManagerChannelId"
        const val CHANNEL_NAME = "RealEstateManagerChannel"
    }

    fun sendNotification(propertyId: Long) {

        val channelId = CHANNEL_ID

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelName = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)

            with(NotificationManagerCompat.from(context)) {
                createNotificationChannel(channel)
            }
        }

        val intent = Intent(context, MainActivity::class.java).apply {
            putExtra(PropertyDetailsFragment.PROPERTY_ID_KEY, propertyId)
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)


        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getText(R.string.notification_title))
            .setContentText(context.getText(R.string.notification_text))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }
}