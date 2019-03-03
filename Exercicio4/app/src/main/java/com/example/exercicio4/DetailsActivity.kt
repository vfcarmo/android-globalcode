package com.example.exercicio4

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    private var book: Book? = null

    private lateinit var detailsHelper: DetailsHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        this.detailsHelper = DetailsHelper(this)

        this.book = intent.getParcelableExtra(MainActivity.BOOK_PARCELABLE)
        this.book?.let {
            this.detailsHelper.bindView(it)
        }

        btEdit.setOnClickListener {
            val intent = Intent(this, EditActivity::class.java)
            intent.putExtra(MainActivity.BOOK_PARCELABLE, book)
            startActivity(intent)
            finish()
        }

        btCancel.setOnClickListener {
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
            book?.let { detailsHelper.bindView(it) }
        }
    }

    class DetailsHelper(context: Activity) {

        private val ivBookCover: ImageView = context.findViewById(R.id.ivBookCover)
        private val tvTitle: TextView = context.findViewById(R.id.tvTitle)
        private val tvAuthor: TextView = context.findViewById(R.id.tvAuthor)
        private val tvPublishYear: TextView = context.findViewById(R.id.tvPublishYear)
        private val tvDescription: TextView = context.findViewById(R.id.tvDescription)

        fun bindView(book: Book) {
            ivBookCover.setImageBitmap(book.cover)
            tvTitle.text = book.title
            tvAuthor.text = book.author
            tvPublishYear.text = book.publishYear.toString()
            tvDescription.text = book.description
        }
    }
}
