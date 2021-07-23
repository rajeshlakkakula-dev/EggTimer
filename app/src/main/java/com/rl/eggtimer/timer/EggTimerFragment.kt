package com.rl.eggtimer.timer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.rl.eggtimer.databinding.FragmentEggTimerBinding
import com.rl.eggtimer.util.getNotificationManager
import com.rl.eggtimer.util.subscribeToTopicBreakfast


class EggTimerFragment : Fragment() {


    private val viewModel: EggTimerViewModel = TODO()


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
            //createEggTimerNotifcationChannel()
            //createBreakfastNotifactionChannel()
            subscribeToTopicBreakfast()

        }
    }

    //TODO Creatae the function
    //private fun Context.createBreakfastNotifactionChannel() =







    private fun Context.subscribeToTopicBreakfast() =
        FirebaseMessaging.getInstance().subscribeToTopicBreakfast(this)

    private fun Context.CreateBreakfastNotifactionChannel() {

    }

    //private fun Context.createEggTimerNotifcationChannel() =



/*
    Companion object {

        fun  newInstance() = EggTimerFragment()
    }
*/


}