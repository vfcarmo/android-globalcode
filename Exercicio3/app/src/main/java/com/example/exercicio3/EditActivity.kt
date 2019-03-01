package com.example.exercicio3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio3.MainActivity.Companion.GALLERY_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {

    private lateinit var ivProgrammingLanguage: ImageView
    private lateinit var etTitle: TextView
    private lateinit var etLaunchYear: TextView
    private lateinit var etDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        this.ivProgrammingLanguage = findViewById(R.id.ivProgrammingLanguage)
        this.etTitle= findViewById(R.id.etTitle)
        this.etLaunchYear = findViewById(R.id.etLaunchYear)
        this.etDescription = findViewById(R.id.etDescription)

        ivProgrammingLanguage.isClickable = true
        ivProgrammingLanguage.setOnClickListener {
            pickFromGallery()
        }

        btSalvar.setOnClickListener {
            salvar()
        }

        btCancelar.setOnClickListener {
            finish()
        }

    }

    private fun salvar() {

        val title = etTitle.text.toString()
        val launchYear = etLaunchYear.text.toString()
        val description = etDescription.text.toString()

        val valid: Boolean = isValid(title, launchYear, description)

        if (valid) {

            val programmingLanguage = ProgrammingLanguage(R.drawable.ic_developer_board,
                title, launchYear.toInt(), description)

            val intent: Intent = intent
            intent.putExtra("RESULT", programmingLanguage)
            setResult(Activity.RESULT_OK, intent)

            Log.d(
                localClassName, "Programming Language { title=${programmingLanguage.title}, " +
                        "year=${programmingLanguage.year}, description=${programmingLanguage.description} }"
            )
            finish()
        }
    }

    private fun isValid(title: String, launchYear: String, description: String): Boolean {

        var result = true

        if (description.isEmpty()) {
            etDescription.error = getString(R.string.msg_description_required)
            result = false
            etDescription.requestFocus()
        } else {
            etDescription.error = null
        }

        if (launchYear.isEmpty()) {
            etLaunchYear.error = getString(R.string.msg_launch_year_required)
            result = false
            etLaunchYear.requestFocus()
        } else {
            etLaunchYear.error = null
        }

        if (title.isEmpty()) {
            etTitle.error = getString(R.string.msg_title_required)
            result = false
            etTitle.requestFocus()
        } else {
            etTitle.error = null
        }
        return result
    }

    @SuppressLint("InlinedApi")
    private fun pickFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    val selectedImage = data.data
                    ivProgrammingLanguage.setImageURI(selectedImage)
                }
            }
        }
    }
}
