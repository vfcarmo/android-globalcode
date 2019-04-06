package com.example.aula8

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

class Game(val name: String,
                val launchYear: Int,
                val imageUrl: String,
                rating: Double): BaseObservable() {
    val isClassic = launchYear < 2000
    @get:Bindable
    var rating by bindable(rating, BR.rating)
}
