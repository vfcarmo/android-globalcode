package com.example.aula8

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.aula8.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by SetContentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
        val game = Game(
            "GTA V",
            1999,
            "https://static-cdn.jtvnw.net/ttv-boxart/Grand%20Theft%20Auto%20V.jpg",
            4.0
        )
        binding.game = game

        tvRating.setOnClickListener {
            game.rating += 0.1
            Toast.makeText(this, game.rating.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
