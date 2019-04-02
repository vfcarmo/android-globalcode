package com.example.aula6

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btLoadImage.setOnClickListener {
            loadImage()
        }
    }

    private fun loadImage() {
        val pickIntent = Intent(Intent.ACTION_GET_CONTENT)
        pickIntent.type = "image/*"

        val pickGalleryIntent = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val chooserIntent = Intent.createChooser(pickIntent, "Selecte or take a picture")
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
            arrayOf(pickGalleryIntent, takePictureIntent))

        startActivityForResult(chooserIntent, SELECT_PICTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                data?.extras?.let {
                    val bitmap = it.get("data") as Bitmap
                    ivMain.setImageBitmap(bitmap)
                } ?: data?.data?.let {
                    Picasso.get().load(it).into(ivMain)
                }
            }
        }
    }

    companion object {
        private const val SELECT_PICTURE = 1
    }
}
