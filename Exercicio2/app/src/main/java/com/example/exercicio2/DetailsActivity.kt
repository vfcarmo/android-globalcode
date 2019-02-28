package com.example.exercicio2

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DetailsActivity : AppCompatActivity() {

    private lateinit var tvNome: TextView
    private lateinit var tvSobrenome: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvGenero: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        this.tvNome = findViewById(R.id.tvNome)
        this.tvSobrenome = findViewById(R.id.tvSobrenome)
        this.tvEmail = findViewById(R.id.tvEmail)
        this.tvGenero = findViewById(R.id.tvGenero)



        val btVoltar: Button = findViewById(R.id.btVoltar)
        btVoltar.setOnClickListener {
            finish()
        }
    }
}
