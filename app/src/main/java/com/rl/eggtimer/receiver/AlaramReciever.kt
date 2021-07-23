package com.rl.eggtimer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.rl.eggtimer.util.getNotificationManager
import com.rl.eggtimer.util.sendNotifcation

class AlaramReciever : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        context.getNotificationManager().sendNotifcation(

            "Bon Appetite",
            context
        )

    }
}