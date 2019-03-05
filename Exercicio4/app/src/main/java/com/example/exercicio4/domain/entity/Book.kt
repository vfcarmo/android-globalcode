package com.example.exercicio4.domain.entity

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class Book(
    var id: Int,
    val coverUrl: String,
    val cover: Bitmap?,
    val title: String,
    val author: String,
    val publishYear: Int,
    val description: String
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        id?.let {
            parcel.writeInt(it)
        }
        parcel.writeString(coverUrl)
        parcel.writeParcelable(cover, flags)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeInt(publishYear)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Book

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }

        private var bookId: Int = 0

        fun nextVal(): Int {
            bookId += 1
            return bookId
        }
    }
}