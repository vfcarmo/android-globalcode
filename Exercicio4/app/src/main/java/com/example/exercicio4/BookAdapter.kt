package com.example.exercicio4

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.books.view.*

class BookAdapter(
    private val books: List<Book>,
    private val listener: (Book) -> Unit
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
        holder.bindView(book, listener)
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bindView(book: Book, listener: (Book) -> Unit) = with(itemView) {

            ivBookCover.setImageBitmap(book.cover)
            tvTitle.text = book.title
            tvAuthor.text = "${itemView.context.getText(R.string.label_author)} ${book.author}"
            tvPublishYear.text = "${itemView.context.getText(R.string.label_publish_year)} ${book.publishYear}"
            tvDescription.text = book.description

            itemView.setOnClickListener {
                listener(book)
            }
        }
    }

}
