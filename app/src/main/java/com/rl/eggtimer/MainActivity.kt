package com.rl.eggtimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rl.eggtimer.timer.EggTimerFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.load_fragments,EggTimerFragment.newInstance())
                .commitNow()

        }
    }
}