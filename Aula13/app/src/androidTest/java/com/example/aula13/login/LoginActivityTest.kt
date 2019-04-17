package com.example.aula13.login

import androidx.test.rule.ActivityTestRule
import com.example.aula13.LoginActivity
import com.example.aula13.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LoginActivityTest {

    @get:Rule
    val rule = ActivityTestRule(LoginActivity::class.java)

    lateinit var activity: LoginActivity

    lateinit var robot: LoginTestRobot

    @Before
    fun setUp() {
        activity = rule.activity
        robot = LoginTestRobot(activity)
    }

    @Test
    fun loginSuccess() {
        robot
            .setEmail("admin@admin.com")
            .setPassword("admin")
            .clickLogin()
            .matchText(R.id.tvNameSurname, "Nome Sobrenome")
    }

    @Test
    fun loginMissingEmailPassword() {
        robot
            .clickLogin()
            .matchErrorText(R.string.missing_fields)
    }

    @Test
    fun loginMissingPassword() {
        robot
            .setEmail("admin@admin.com")
            .clickLogin()
            .matchErrorText(R.string.missing_fields)
    }

    @Test
    fun loginWrongPassword() {
        robot
            .setEmail("admin@admin.com")
            .setPassword("root")
            .clickLogin()
            .matchErrorText(R.string.login_fail)
    }
}
