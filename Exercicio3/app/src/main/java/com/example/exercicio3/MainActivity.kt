package com.example.exercicio3

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        const val EDIT_REQUEST_CODE = 1

        const val GALLERY_REQUEST_CODE = 2

        const val SAVED_INSTANCE = "SAVED_INSTANCE"

        const val PROGRAMMING_LANGUAGE = "PROGRAMMING_LANGUAGE"

        const val RESULT = "RESULT"
    }

    private val programmingLanguages: MutableList<ProgrammingLanguage> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val programmingLanguageKotlin = ProgrammingLanguage(R.drawable.kotlin, "Kotlin", 2011,
            "Kotlin description")
        val programmingLanguageJava = ProgrammingLanguage(R.drawable.java, "Java", 1996,
            "Java Description")

        this.programmingLanguages.add(programmingLanguageKotlin)
        this.programmingLanguages.add(programmingLanguageJava)

        recyclerView.adapter = ProgrammingLanguageAdater(programmingLanguages) {
            Toast.makeText(this, "Clicked Item: ${it.title}", Toast.LENGTH_LONG).show()

            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(PROGRAMMING_LANGUAGE, it)
            startActivityForResult(intent, EDIT_REQUEST_CODE)
        }

        fab.setOnClickListener {
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
