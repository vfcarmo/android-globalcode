package com.example.aula1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvPrincipal: TextView = findViewById(R.id.tvPrincipal)
        val etPrincipal: EditText = findViewById(R.id.etPrincipal)
        val btEnviar: Button = findViewById(R.id.btEnviar)

        btEnviar.setOnClickListener {

            var text = etPrincipal.text.toString()

            if (text.isEmpty()) {
                text = "Digite alguma coisa"
            }
            tvPrincipal.text = text
        }
    }
}
