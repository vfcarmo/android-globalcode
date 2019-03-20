package com.example.aula5

import androidx.annotation.DrawableRes

data class ProgrammingLanguage(
    @DrawableRes
    val imageResourceId: Int,
    val title: String,
    val launchYear: Int,
    val description: String)