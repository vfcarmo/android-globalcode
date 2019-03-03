package com.example.exercicio4

import android.app.Activity
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_edit.*
import androidx.core.graphics.drawable.toBitmap


class EditActivity : AppCompatActivity() {

    private var book: Book? = null

    private lateinit var editHelper: EditHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        this.editHelper = EditHelper(this)

        this.book = intent.getParcelableExtra(MainActivity.BOOK_PARCELABLE)
        book?.let {
            editHelper.bindView(it)
        }

        btSave.setOnClickListener {
            salve()
        }

        btCancel.setOnClickListener {
            finish()
        }
    }

    private fun salve() {
        this.book = editHelper.getModel()

        book?.let {
            intent.putExtra(MainActivity.RESULT, it)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {
            if (book != null) {

                it.putParcelable(MainActivity.BOOK_PARCELABLE, book)
            } else {
                it.remove(MainActivity.BOOK_PARCELABLE)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let { savedState ->
            this.book = savedState.getParcelable(MainActivity.BOOK_PARCELABLE)
            book?.let { editHelper.bindView(it) }
        }
    }

    class EditHelper(private val context: Activity) {

        private val ivBookCover: ImageView = context.findViewById(R.id.ivBookCover)
        private val etBookCover: EditText = context.findViewById(R.id.etBookCover)
        private val etTitle: EditText = context.findViewById(R.id.etTitle)
        private val etAuthor: EditText = context.findViewById(R.id.etAuthor)
        private val etPublishYear: EditText = context.findViewById(R.id.etPublishYear)
        private val etDescription: EditText = context.findViewById(R.id.etDescription)

        fun bindView(book: Book) {
            ivBookCover.setImageBitmap(book.cover)
            etBookCover.setText(book.coverUrl)
            etTitle.setText(book.title)
            etAuthor.setText(book.author)
            etPublishYear.setText(book.publishYear.toString())
            etDescription.setText(book.description)
        }

        fun getModel(): Book? {

//            val bitmap = ivBookCover.drawable.toBitmap()
            val bookCover = etBookCover.text.toString()
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val publishYear = etPublishYear.text.toString()
            val description = etDescription.text.toString()

            return if (isValid(bookCover, title, author, publishYear, description)) {
                Book(bookCover, null, title, author, publishYear.toInt(), description)
            } else {
                null
            }
        }

        private fun isValid(
//            bitmap: Bitmap?,
            bookCover: String,
            title: String,
            author: String,
            publishYear: String,
            description: String
        ): Boolean {
            var result = true

            if (description.isEmpty()) {
                etDescription.error = context.getString(R.string.msg_book_description_required)
                result = false
                etDescription.requestFocus()
            } else {
                etDescription.error = null
            }

            if (publishYear.isEmpty()) {
                etPublishYear.error = context.getString(R.string.msg_publish_year_required)
                result = false
                etPublishYear.requestFocus()
            } else {
                etDescription.error = null
            }

            if (author.isEmpty()) {
                etAuthor.error = context.getString(R.string.msg_author_required)
                result = false
                etAuthor.requestFocus()
            } else {
                etAuthor.error = null
            }

            if (title.isEmpty()) {
                etTitle.error = context.getString(R.string.msg_book_title_required)
                result = false
                etTitle.requestFocus()
            } else {
                etTitle.error = null
            }

            if (publishYear.isEmpty()) {
                etPublishYear.error = context.getString(R.string.msg_publish_year_required)
                result = false
                etPublishYear.requestFocus()
            } else {
                etDescription.error = null
            }

            if (bookCover.isEmpty()) {
                etBookCover.error = context.getString(R.string.msg_book_cover_required)
                result = false
                etBookCover.requestFocus()
            } else {
                etBookCover.error = null
            }
            return result
        }

    }
}
