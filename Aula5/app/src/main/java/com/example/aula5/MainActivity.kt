package com.example.aula5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.longToast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val kotlin = ProgrammingLanguage(
            android.R.drawable.ic_input_add,
            "Kotlin",
            2010,
            "Description"
        )
        val items = listOf(kotlin)
        val adapter = ProgrammingLanguageAdapter(items) {
            longToast(it.title)
        }
        recyclerView.adapter = adapter

    }
}
