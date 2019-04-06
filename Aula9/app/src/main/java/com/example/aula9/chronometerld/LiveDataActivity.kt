package com.example.aula9.chronometerld

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.aula9.R
import com.example.aula9.chronometervm.getViewModel
import kotlinx.android.synthetic.main.activity_live_data.*

class LiveDataActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data)

        val liveDataViewModel = getViewModel<LiveDataViewModel>()

        liveDataViewModel.elapsedTime.observe(this, Observer {
            textView.text = "Elapsed Time: $it"
        })
    }
}
