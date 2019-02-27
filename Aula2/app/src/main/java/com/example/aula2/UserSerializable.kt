package com.example.aula2

import java.io.Serializable

data class UserSerializable(
    val nome: String,
    val sobrenome: String
) : Serializable