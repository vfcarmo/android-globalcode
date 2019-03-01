package com.example.exercicio3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio3.MainActivity.Companion.GALLERY_REQUEST_CODE
import kotlinx.android.synthetic.main.activity_edit.*


class EditActivity : AppCompatActivity() {

    private lateinit var programmingLanguageHelper: ProgrammingLanguageHelper

    private var programmingLanguage: ProgrammingLanguage? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        this.programmingLanguageHelper = ProgrammingLanguageHelper(this)

        this.programmingLanguage = intent.getParcelableExtra(MainActivity.PROGRAMMING_LANGUAGE)
        this.programmingLanguage?.let {
            programmingLanguageHelper.bindView(it)
        }

        this.ivProgrammingLanguage.isClickable = true
        this.ivProgrammingLanguage.setOnClickListener {
            pickFromGallery()
        }

        this.btSalvar.setOnClickListener {
            salvar()
        }

        this.btCancelar.setOnClickListener {
            finish()
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {

        outState?.putParcelable(MainActivity.PROGRAMMING_LANGUAGE, programmingLanguage)

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            this.programmingLanguage = savedInstanceState.getParcelable(MainActivity.PROGRAMMING_LANGUAGE)
            programmingLanguageHelper.bindView(programmingLanguage)
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                GALLERY_REQUEST_CODE -> {
                    val selectedImage = data.data
                    this.ivProgrammingLanguage.setImageURI(selectedImage)
                }
            }
        }
    }

    private fun salvar() {

        val programmingLanguage = programmingLanguageHelper.getModel()

        programmingLanguage?.let {
            val intent: Intent = intent
            intent.putExtra("RESULT", it)
            setResult(Activity.RESULT_OK, intent)

            Log.d(
                localClassName, "Programming Language { title=${it.title}, year=${it.year}, " +
                        "description=${it.description} }"
            )
            finish()
        }
    }

    @SuppressLint("InlinedApi")
    private fun pickFromGallery() {

        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg", "image/png")
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

}
