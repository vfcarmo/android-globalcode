package com.example.aula13.login

import android.content.Context
import com.example.aula13.R
import com.example.aula13.robot.BaseTestRobot

class LoginTestRobot(private val context: Context) : BaseTestRobot() {
    fun setEmail(email: String) = apply {
        fillEditText(R.id.etEmail, email)
    }

    fun setPassword(pass: String) = apply {
        fillEditText(R.id.etPassword, pass)
    }

    fun clickLogin() = apply {
        clickButton(R.id.btLogin)
    }

    fun matchErrorText(err: Int) = matchText(
            textView(android.R.id.message),
            context.getString(err)
    )
}