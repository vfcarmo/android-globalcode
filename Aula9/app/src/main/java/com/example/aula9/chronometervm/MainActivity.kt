package com.example.aula9.chronometervm

import android.os.Bundle
import android.os.SystemClock
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.aula9.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewModel = getViewModel<ChronometerViewModel>()

        if (viewModel.startTime == 0L) {
            viewModel.startTime = SystemClock.elapsedRealtime()
        }
        chronometer.base = viewModel.startTime

        chronometer.start()
    }

}

inline fun <reified T: ViewModel> FragmentActivity.getViewModel(): T {
    return ViewModelProviders.of(this).get(T::class.java)
}
