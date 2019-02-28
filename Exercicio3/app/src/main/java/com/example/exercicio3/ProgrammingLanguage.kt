package com.example.exercicio3

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.DrawableRes

data class ProgrammingLanguage(
    @DrawableRes
    val imageResourceId: Int,
    val title: String,
    val year: Int,
    val description: String
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(imageResourceId)
        parcel.writeString(title)
        parcel.writeInt(year)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProgrammingLanguage> {
        override fun createFromParcel(parcel: Parcel): ProgrammingLanguage {
            return ProgrammingLanguage(parcel)
        }

        override fun newArray(size: Int): Array<ProgrammingLanguage?> {
            return arrayOfNulls(size)
        }
    }
}