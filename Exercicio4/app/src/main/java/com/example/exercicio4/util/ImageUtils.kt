package com.example.exercicio4.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.net.URL

class ImageUtils {

    companion object {

        fun loadImage(imageUrl: String): Bitmap? {

            try {
                val url = URL(imageUrl)

                return BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                Log.e("ImageUtils", "Error loading image.", e)
            }
            return null
        }
    }
}