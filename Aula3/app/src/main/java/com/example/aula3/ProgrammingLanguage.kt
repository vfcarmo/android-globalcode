package com.example.aula3

import androidx.annotation.DrawableRes

data class ProgrammingLanguage(
    @DrawableRes
    val imageResourceId: Int,

    val title: String,

    val ano: Int,

    val description: String
)