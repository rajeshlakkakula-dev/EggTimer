package com.rl.eggtimer.timer

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.CountDownTimer
import android.os.SystemClock
import androidx.core.app.AlarmManagerCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.versionedparcelable.R
import com.rl.eggtimer.receiver.AlaramReciever
import com.rl.eggtimer.util.cancelNotifications
import com.rl.eggtimer.util.getNotificationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EggTimerViewModel(private val app: Application) : AndroidViewModel(app) {

    private val REQUEST_CODE = 0
    private val TRIGGER_TIME = "TRIGGER_AT"
    private val minute: Long = 60_000L
    private val second: Long = 1_000L

    private val timerLengthOptions: IntArray
    private val notifyPendingIntent: PendingIntent



    private val alarmManager = app.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private var prefs =

        app.getSharedPreferences("com.rl.eggtimer.eggtimerNotification",Context.MODE_PRIVATE)



    private val notifyIntent = Intent(app,AlaramReciever::class.java)

    private val  _timeSelection = MutableLiveData<Int>()

    private val _elapsedTime = MutableLiveData<Long>()
    val elapsedTime: LiveData<Long>
    get() = _elapsedTime

    val timeSelection: LiveData<Int>
    get() =  _timeSelection

    private var _alarmOn = MutableLiveData<Boolean>()
    val isAlarmOn : LiveData<Boolean>
            get() = _alarmOn

    private lateinit var timer: CountDownTimer



    init {
        _alarmOn.value = PendingIntent.getBroadcast(

            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_NO_CREATE

        )!=null

        notifyPendingIntent = PendingIntent.getBroadcast(

            getApplication(),
            REQUEST_CODE,
            notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        timerLengthOptions = app.resources.getIntArray(com.rl.eggtimer.R.array.minutes_array)


        if (_alarmOn.value!!){
            createTimer()
        }


    }


    fun setAlarm(isChecked: Boolean){

        when(isChecked){

            true -> timeSelection.value?.let { startTimer(it) }
            false -> cancelNotification()
        }


    }

    private fun cancelNotification() {
        resetTimer()
        alarmManager.cancel(notifyPendingIntent)
    }


    fun setTimeSelected(timerLengthSelection : Int){


        _timeSelection.value = timerLengthSelection


    }


    private fun startTimer(timerLengthSelection: Int){

        _alarmOn.value?.let {

            if (!it){


                _alarmOn.value = true

                val selectedInterval = when (timerLengthSelection){

                    0 -> second *10 //For Testing Only
                    else
                        -> timerLengthOptions[timerLengthSelection] * minute


                }

                val triggerTime = SystemClock.elapsedRealtime() + selectedInterval



                app.applicationContext.getNotificationManager().cancelNotifications()

                AlarmManagerCompat.setExactAndAllowWhileIdle(

                    alarmManager,
                    AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    triggerTime,
                    notifyPendingIntent

                )


                viewModelScope.launch {
                    saveTime(triggerTime)
                }

            }

        }

        createTimer()


    }

    private suspend fun saveTime(triggerTime: Long) =
        withContext(Dispatchers.IO){

            prefs.edit().putLong(TRIGGER_TIME,triggerTime).apply()
        }


    private fun resetTimer(){

        timer.cancel()
        _elapsedTime.value = 0
        _alarmOn.value= false

    }

    private fun createTimer() {

        viewModelScope.launch {

            val triggerTime = loadTime()

            timer = object : CountDownTimer(triggerTime,second){
                override fun onTick(milliUntilFinished: Long) {
                    _elapsedTime.value = triggerTime - SystemClock.elapsedRealtime()

                    if (_elapsedTime.value!! <= 0){
                        resetTimer()
                    }

                }

                override fun onFinish() {
                    resetTimer()
                }


            }

            timer.start()


        }


    }

    private  suspend fun loadTime(): Long =

        withContext(Dispatchers.IO){

            prefs.getLong(TRIGGER_TIME,0)
        }

}