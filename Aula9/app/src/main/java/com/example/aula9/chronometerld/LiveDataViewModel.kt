package com.example.aula9.chronometerld

import android.os.SystemClock
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class LiveDataViewModel : ViewModel() {

    val elapsedTime = MutableLiveData<Long>()

    init {
        val initialTime = SystemClock.elapsedRealtime()
        Timer().scheduleAtFixedRate(
            object : TimerTask() {
                override fun run() {
                    val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                    elapsedTime.postValue(newValue)
                }
            }, 1000, 1000
        )
    }
}
