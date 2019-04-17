package com.example.aula13.main

import android.widget.TextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.aula13.MainActivity
import com.example.aula13.R
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val rule = ActivityTestRule(MainActivity::class.java)

    lateinit var activity: MainActivity

    @Before
    fun setUp() {
        activity = rule.activity
    }

    @Test
    fun loadActivity_shouldShowHelloWorld() {
        val viewById = activity.findViewById<TextView>(R.id.textView)

        assertThat(viewById, notNullValue())
        assertThat(viewById.text.toString(), equalTo("Hello World!"))
    }

    @Test
    fun loadActivity_shouldWriteTypedText() {
        onView(withHint("Type some text")).perform(typeText("Some Text"))
        onView(withText("Send")).perform(click())
        onView(withText("Typed: Some Text")).check(matches(isDisplayed()))
    }
}