package com.rl.eggtimer.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.content.ContextCompat

fun Context.getNotificationManager(): NotificationManager = ContextCompat.getSystemService(
    this,
    NotificationManager::class.java

) as NotificationManager