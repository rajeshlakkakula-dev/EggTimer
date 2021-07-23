package com.rl.eggtimer.util

import android.content.Context
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessaging

private const val BREAKFAST_TOPIC = "breakfast"

fun FirebaseMessaging.subscribeToTopicBreakfast(context: Context){


    subscribeToTopic(BREAKFAST_TOPIC)
        .addOnCompleteListener {

            val userFeedback = if( it.isSuccessful)
                "Suscribed to topic"
            else
                "Failed to subscribe to topic"
            Toast.makeText(context.applicationContext,userFeedback,Toast.LENGTH_LONG).show()

        }




}