package com.rl.eggtimer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rl.eggtimer.util.getNotificationManager
import com.rl.eggtimer.util.sendNotification

class AlaramReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        context.getNotificationManager().sendNotification(
            "It is time! Bon apetit",
            context
        )

    }
}