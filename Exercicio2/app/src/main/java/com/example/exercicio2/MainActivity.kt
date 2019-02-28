package com.example.exercicio2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.example.exercicio1.Usuario
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_CODE: Int = 1
        const val USUARIO = "USUARIO"
    }

    private var myIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            //            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()

            val intent = Intent(this, EditActivity::class.java)
            myIntent = intent
            startActivityForResult(intent, REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                REQUEST_CODE -> {
                    val result = data.getParcelableExtra<Usuario>("RESULT")

                    val intent = Intent(this, DetailsActivity::class.java)
                    intent.putExtra(USUARIO, result)
                    startActivity(intent)

                    Log.d(
                        localClassName, "Usuário { nome=${result.nome}, sobrenome=${result.sobrenome}, " +
                                "email=${result.email}, genero=${result.genero.name.toLowerCase()}, password=*** }"
                    )
                }
            }
        }
    }

}
