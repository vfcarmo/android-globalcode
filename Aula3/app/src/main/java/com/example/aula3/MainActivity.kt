package com.example.aula3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val programmingLanguageKotlin = ProgrammingLanguage(R.drawable.kotlin, "Kotlin", 2011,
            "Kotlin description")
        val programmingLanguageJava = ProgrammingLanguage(R.drawable.java, "Java", 1996,
            "Java Description")

        val programmingLanguages = mutableListOf<ProgrammingLanguage>(programmingLanguageKotlin, programmingLanguageJava)

        val listener: View.OnClickListener = View.OnClickListener {
            val tvTitle: TextView = it.findViewById(R.id.tvTitle)
            Toast.makeText(this, "Clicked Item: ${tvTitle.text}", Toast.LENGTH_LONG).show()
        }

        val adapter: RecyclerView.Adapter<ProgrammingLanguageAdapter.ViewHolder> = ProgrammingLanguageAdapter(programmingLanguages, listener)
        recyclerView.adapter = adapter
    }

}
