package com.example.exercicio1

import android.os.Parcel
import android.os.Parcelable

data class Usuario(val nome: String?,
                   val sobrenome: String?,
                   val email: String?,
                   val password: String?,
                   val genero: Genero) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        Genero.valueOf(parcel.readString()!!)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(sobrenome)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeValue(genero.name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Usuario> {
        override fun createFromParcel(parcel: Parcel): Usuario {
            return Usuario(parcel)
        }

        override fun newArray(size: Int): Array<Usuario?> {
            return arrayOfNulls(size)
        }
    }
}