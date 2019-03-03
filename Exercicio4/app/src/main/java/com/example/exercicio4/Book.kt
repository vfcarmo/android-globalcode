package com.example.exercicio4

import android.graphics.Bitmap
import android.os.Parcel
import android.os.Parcelable

data class Book(
    val coverUrl: String,
    val cover: Bitmap?,
    val title: String,
    val author: String,
    val publishYear: Int,
    val description: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readParcelable(Bitmap::class.java.classLoader),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
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

    companion object CREATOR : Parcelable.Creator<Book> {
        override fun createFromParcel(parcel: Parcel): Book {
            return Book(parcel)
        }

        override fun newArray(size: Int): Array<Book?> {
            return arrayOfNulls(size)
        }
    }
}