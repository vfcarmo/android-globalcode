package com.example.aula9

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SeekBarViewModel: ViewModel() {

    val seekBarValue = MutableLiveData<Int>()
}