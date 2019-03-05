package com.example.exercicio4.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercicio4.R
import com.example.exercicio4.domain.entity.Book
import kotlinx.android.synthetic.main.books.view.*

class BookAdapter(
    private val books: List<Book>,
    private val onClickListener: (Book) -> Unit,
    private val onLongClickListener: (Book) -> Boolean
) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.books, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return books.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = books[position]
        holder.bindView(book, onClickListener, onLongClickListener)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindView(
            book: Book,
            onClickListener: (Book) -> Unit,
            onLongClickListener: (Book) -> Boolean
        ) = with(itemView) {

            ivBookCover.setImageBitmap(book.cover)
            tvTitle.text = book.title
            tvAuthor.text = "${itemView.context.getText(R.string.label_author)} ${book.author}"
            tvPublishYear.text = "${itemView.context.getText(R.string.label_publish_year)} ${book.publishYear}"
            tvDescription.text = book.description

            itemView.setOnClickListener {
                onClickListener(book)
            }

            itemView.setOnLongClickListener {
                onLongClickListener(book)
            }
        }
    }

}
