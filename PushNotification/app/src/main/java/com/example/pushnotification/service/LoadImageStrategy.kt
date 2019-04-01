package com.example.pushnotification.service

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.example.pushnotification.domain.LoadImageType
import com.squareup.picasso.Picasso


object LoadImageStrategy {

    fun loadImage(context: Context, loadImageType: LoadImageType, imageUrl: String): Bitmap {
        return when (loadImageType) {
            LoadImageType.GLIDE -> {
                Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .into(-1, -1)
                    .get()

            }
            LoadImageType.PICASSO -> {
                Picasso
                    .get()
                    .load(imageUrl)
                    .get()
            }
        }
    }
}