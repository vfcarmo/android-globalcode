package com.example.exercicio3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EDIT_REQUEST_CODE = 1

        const val GALLERY_REQUEST_CODE = 2

        const val RESULT = "RESULT"
    }

    private lateinit var recyclerView: RecyclerView

    private val programmingLanguages: MutableList<ProgrammingLanguage> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        this.recyclerView = findViewById(R.id.recyclerView)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val programmingLanguageKotlin = ProgrammingLanguage(R.drawable.kotlin, "Kotlin", 2011,
            "Kotlin description")
        val programmingLanguageJava = ProgrammingLanguage(R.drawable.java, "Java", 1996,
            "Java Description")

        this.programmingLanguages.add(programmingLanguageKotlin)
        this.programmingLanguages.add(programmingLanguageJava)

        val listener = View.OnClickListener {
            val tvTitle: TextView = it.findViewById(R.id.tvTitle)
            Toast.makeText(this, "Clicked Item: ${tvTitle.text}", Toast.LENGTH_LONG).show()
        }
        recyclerView.adapter = ProgrammingLanguageAdater(programmingLanguages, listener)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
            val intent = Intent(this, EditActivity::class.java)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {

                EDIT_REQUEST_CODE -> {

                    val programmingLanguage: ProgrammingLanguage? =
                            data.getParcelableExtra(RESULT)
                    if (programmingLanguage != null) {
                        this.programmingLanguages.add(programmingLanguage)
                        recyclerView.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
