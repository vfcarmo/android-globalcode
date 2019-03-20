package com.example.exercicio4.presentation.activity

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio4.R
import com.example.exercicio4.domain.entity.Book
import com.example.exercicio4.util.ImageUtils
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread


class EditActivity : AppCompatActivity() {

    companion object {
        const val BOOK_COVER = "BOOK_COVER"
    }

    private var book: Book? = null

    private var cover: Bitmap? = null

    private lateinit var editHelper: EditHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        this.editHelper = EditHelper(this)

        this.book = intent.getParcelableExtra(MainActivity.RESULT)
        this.book?.let {
            editHelper.bindView(it)
            intent.removeExtra(MainActivity.RESULT)
        }

        btSave.setOnClickListener {
            salve()
        }

        btCancel.setOnClickListener {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {saveState ->
            book?.let {
                saveState.putParcelable(MainActivity.BOOK_PARCELABLE, it)
            }
            cover?.let {
                saveState.putParcelable(BOOK_COVER, it)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let { savedState ->
            this.book = savedState.getParcelable(MainActivity.BOOK_PARCELABLE)
            book?.let {
                editHelper.bindView(it)
                savedState.remove(MainActivity.BOOKS_PARCELABLE)
            }
            this.cover = savedState.getParcelable(BOOK_COVER)
            this.cover?.let {
                editHelper.bindView(it)
                savedState.remove(BOOK_COVER)
            }
        }
    }

    private fun salve() {
        this.book = editHelper.getModel()
        this.book?.let {
            intent.putExtra(MainActivity.RESULT, it)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    inner class EditHelper(private val context: Activity) {

        private var bookCoverUrl: String? = null

        private lateinit var progresDialog: ProgressDialog

        init {
            btLoadPhoto.setOnClickListener {
                loadBookCover()
            }
        }

        private fun loadBookCover() {

            this.progresDialog = ProgressDialog.show(
                this.context,
                context.getString(R.string.label_wait),
                context.getString(R.string.msg_loading_image)
            )

            doAsync {

                val bookCoverUrl = etBookCover.text.toString()
                val bitmap = ImageUtils.loadImage(bookCoverUrl)

                bitmap?.let {
                    this@EditActivity.cover = it
                    this@EditHelper.bookCoverUrl = bookCoverUrl

                    uiThread {
                        ivBookCover.setImageBitmap(cover)
                        this@EditHelper.progresDialog.dismiss()
                    }
                }

            }
        }

        fun bindView(book: Book) {
            this@EditActivity.cover = book.cover
            this.bookCoverUrl = book.coverUrl
            tvId.text = book.id.toString()
            ivBookCover.setImageBitmap(cover)
            etBookCover.setText(book.coverUrl)
            etTitle.setText(book.title)
            etAuthor.setText(book.author)
            etPublishYear.setText(book.publishYear.toString())
            etDescription.setText(book.description)
        }

        fun bindView(cover: Bitmap) {
            ivBookCover.setImageBitmap(cover)
        }

        fun getModel(): Book? {
            val id = if (tvId.text.toString().isNotBlank()) tvId.text.toString().toInt() else -1
            val bookCover = etBookCover.text.toString()
            val title = etTitle.text.toString()
            val author = etAuthor.text.toString()
            val publishYear = etPublishYear.text.toString()
            val description = etDescription.text.toString()

            return if (isValid(title, author, publishYear, description, bookCover)) {
                Book(
                    id,
                    bookCover,
                    cover,
                    title,
                    author,
                    publishYear.toInt(),
                    description
                )
            } else {
                null
            }
        }

        private fun isValid(
            title: String,
            author: String,
            publishYear: String,
            description: String,
            bookCover: String
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

            when {
                bookCover.isEmpty() -> {
                    etBookCover.error = context.getString(R.string.msg_book_cover_required)
                    result = false
                    etBookCover.requestFocus()
                }
                cover == null -> {
                    etBookCover.error = context.getString(R.string.msg_book_cover_must_be_loaded)
                    result = false
                    etBookCover.requestFocus()
                }
                else -> etBookCover.error = null
            }

            if (title.isEmpty()) {
                etTitle.error = context.getString(R.string.msg_book_title_required)
                result = false
                etTitle.requestFocus()
            } else {
                etTitle.error = null
            }
            return result
        }

    }
}
