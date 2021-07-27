package com.rl.eggtimer.timer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.messaging.FirebaseMessaging
import com.rl.eggtimer.R
import com.rl.eggtimer.databinding.FragmentEggTimerBinding
import com.rl.eggtimer.util.createChannel
import com.rl.eggtimer.util.getNotificationManager
import com.rl.eggtimer.util.subscribeToTopicBreakfast


class EggTimerFragment : Fragment() {


    private val viewModel: EggTimerViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEggTimerBinding.inflate(inflater,container,false)
        .apply {
            eggTimerViewModel = viewModel
            lifecycleOwner = viewLifecycleOwner

        }
        .root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        context?.run {
            createEggTimerNotificationChannel()
            createBreakfastNotificationChannel()
            subscribeToTopicBreakfast()

        }
    }



    private fun Context.createEggTimerNotificationChannel() =

        getNotificationManager().createChannel(

            getString(R.string.egg_notification_channel_id),
            getString(R.string.egg_notification_channel_name),
            getString(R.string.egg_notification_channel_description)

        )

    private fun Context.createBreakfastNotificationChannel() =

    getNotificationManager().createChannel(

        getString(R.string.breakfast_notification_channel_id),
        getString(R.string.breakfast_channel_name),
        getString(R.string.breakfast_channel_description)

    )



    private fun Context.subscribeToTopicBreakfast() = FirebaseMessaging.getInstance().subscribeToTopicBreakfast(this)


    companion object{

        fun newInstance() = EggTimerFragment()
    }




}