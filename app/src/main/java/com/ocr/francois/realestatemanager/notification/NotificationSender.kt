package com.ocr.francois.realestatemanager.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ocr.francois.realestatemanager.R

class NotificationSender {

    companion object {
        const val CHANNEL_ID = "RealEstateManagerChannelId"
        const val CHANNEL_NAME = "RealEstateManagerChannel"
    }

    fun sendNotification(context: Context) {

        val channelId = CHANNEL_ID

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelName = CHANNEL_NAME
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(channelId, channelName, importance)

            with(NotificationManagerCompat.from(context)) {
                createNotificationChannel(channel)
            }
        }

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(context.getText(R.string.notification_title))
            .setContentText(context.getText(R.string.notification_text))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(context)) {
            notify(1, builder.build())
        }
    }
}