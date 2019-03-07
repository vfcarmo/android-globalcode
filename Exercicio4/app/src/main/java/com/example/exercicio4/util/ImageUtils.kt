package com.example.exercicio4.util

import android.content.Context
import android.graphics.*
import android.util.Log
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.net.URL


class ImageUtils(context: Context) : BitmapTransformation(context) {

    override fun getId(): String {
        return javaClass.name
    }

    override fun transform(pool: BitmapPool?, toTransform: Bitmap?, outWidth: Int, outHeight: Int): Bitmap? {
        return cropImage(pool, toTransform)
    }

    companion object {

        private fun cropImage(pool: BitmapPool?, source: Bitmap?): Bitmap? {

            source?.let {

                val size = Math.min(it.width, it.height)
                val x = (it.width - size) / 2
                val y = (it.height - size) / 2

                val squared = Bitmap.createBitmap(it, x, y, size, size)

                var result = pool?.get(size, size, Bitmap.Config.ARGB_8888)
                if (result == null) {
                    result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
                }

                result?.let { bitmap ->

                    val canvas = Canvas(bitmap)
                    val paint = Paint()
                    paint.shader = BitmapShader(squared, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
                    paint.isAntiAlias = true
                    val r = size / 2f
                    canvas.drawCircle(r, r, r, paint)

                    return bitmap
                }
            }
            return null
        }

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