package com.rl.eggtimer.service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.rl.eggtimer.util.getNotificationManager
import com.rl.eggtimer.util.sendNotification

class EggTimerFirebaseMessagingService : FirebaseMessagingService(){



    override fun onMessageReceived(remoteMessage: RemoteMessage) {
       with(remoteMessage){

           from?.run {

               Log.d("Info","Message Recived from $this")

           }
           data.takeIf { it.isNotEmpty() }?.run {

               Log.d("Info","Data Recieved within the message $data")

           }

           notification?.body?.run { applicationContext.getNotificationManager().sendNotification(

               this,
               applicationContext
           ) }



       }

    }










}