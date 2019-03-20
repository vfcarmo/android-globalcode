package com.example.exercicio4.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio4.R
import com.example.exercicio4.domain.entity.Book
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private var book: Book? = null

    private lateinit var detailsHelper: DetailsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        this.detailsHelper = DetailsHelper()

        this.book = intent.getParcelableExtra(MainActivity.RESULT)
        this.book?.let {
            this.detailsHelper.bindView(it)
            intent.removeExtra(MainActivity.RESULT)
        }

        btEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(MainActivity.RESULT, book)
            startActivityForResult(intent,
                MainActivity.EDIT_REQUEST_CODE
            )
        }

        btCancel.setOnClickListener {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let { saveState ->
            book?.let {
                saveState.putParcelable(MainActivity.BOOK_PARCELABLE, it)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let { savedState ->
            this.book = savedState.getParcelable(MainActivity.BOOK_PARCELABLE)
            book?.let {
                detailsHelper.bindView(it)
                savedState.remove(MainActivity.BOOKS_PARCELABLE)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                MainActivity.EDIT_REQUEST_CODE -> {
                    val book: Book? = data.getParcelableExtra(MainActivity.RESULT)

                    book?.let {
                        data.removeExtra(MainActivity.RESULT)
                        intent.putExtra(MainActivity.RESULT, it)
                        setResult(Activity.RESULT_OK, intent)
                        finish()
                    }
                }
            }
        }
    }

    inner class DetailsHelper {

        fun bindView(book: Book) {
            tvId.text = book.id.toString()
            ivBookCover.setImageBitmap(book.cover)
            tvTitle.text = book.title
            tvAuthor.text = book.author
            tvPublishYear.text = book.publishYear.toString()
            tvDescription.text = book.description
        }
    }
}
