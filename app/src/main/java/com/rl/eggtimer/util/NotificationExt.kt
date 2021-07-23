package com.rl.eggtimer.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.provider.Settings.Global.getString
import android.provider.Settings.Secure.getString
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.rl.eggtimer.MainActivity
import com.rl.eggtimer.R
import com.rl.eggtimer.receiver.SnoozeReciever
import com.rl.eggtimer.timer.ImportantActivity

val REQUEST_CODE = 0
val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(
    messageBody: String,
    context: Context
) {
    val contentIntent = Intent(context, MainActivity::class.java)
        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }

    val contentPendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        contentIntent,
        PendingIntent.FLAG_UPDATE_CURRENT

    )

    val snoozeIntent = Intent(context, SnoozeReciever::class.java)
    val snoozePendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE,
        snoozeIntent,
        PendingIntent.FLAG_ONE_SHOT
    )

    val eggImage = BitmapFactory.decodeResource(
        context.resources,
        R.drawable.cooked_egg
    )

    val style = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImage)
        .bigLargeIcon(null)

    val fullScreenIntent = Intent(context, ImportantActivity::class.java)
        .apply { flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK }


    val fullScreenPendingIntent = PendingIntent.getActivity(
        context,
        REQUEST_CODE,
        fullScreenIntent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    NotificationCompat.Builder(
        context,
        context.getString(R.string.egg_notification_channel_id)
    )
        .setSmallIcon(R.drawable.egg_icon)
        .setContentTitle("Egg Timer")
        .setContentText(messageBody)
        .setContentIntent(contentPendingIntent)
        .setAutoCancel(true)
        .setStyle(style)
        .setLargeIcon(eggImage)
        .addAction(
            R.drawable.egg_icon,
            "Snooze",
            snoozePendingIntent
        )
        .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setFullScreenIntent(fullScreenPendingIntent,true)
        .setVibrate(longArrayOf(100,200,100,200))
        .run {
            notify(NOTIFICATION_ID,this.build())
        }


    fun NotificationManager.createChannel(

        channelId: String,
        channelName: String,
        channelDescription: String,
    ){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {

                enableLights(true)
                lightColor = Color.RED
                enableVibration(true)
                description = channelDescription
                setShowBadge(false)
            }.run {

                createNotificationChannel(this)

            }
        }

    }

}

fun NotificationManager.cancelNotifications() = cancelAll()