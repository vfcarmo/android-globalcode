package com.example.aula4

import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.WeakReference
import java.net.URL

class MainActivity : AppCompatActivity() {

    companion object {
        const val IMAGE_URL = "https://gocode.academy/wp-content/uploads/2016/11/python.sh-600x600-1.png"

        class MyAsyncTask(mainActivity: MainActivity) : AsyncTask<Unit, Unit, Bitmap>() {

            private val activityReference: WeakReference<MainActivity> =
                WeakReference(mainActivity)

            private lateinit var progresDialog: ProgressDialog

            override fun doInBackground(vararg params: Unit?): Bitmap? {

                return loadImage(IMAGE_URL)
            }

            override fun onPostExecute(bitmap: Bitmap?) {
                val mainActivity = activityReference.get()

                if (mainActivity == null || mainActivity.isFinishing) {
                    return
                }
                mainActivity.ivImage.setImageBitmap(bitmap)

                progresDialog.dismiss()
            }

            override fun onPreExecute() {
                val mainActivity = activityReference.get()
                progresDialog = ProgressDialog.show(
                    mainActivity,
                    "Wait",
                    "Loadiing image..."
                )
                mainActivity?.ivImage?.setImageResource(android.R.color.transparent)
            }
        }

        fun loadImage(imageUrl: String): Bitmap? {

            try {
                val url = URL(imageUrl)

                return BitmapFactory.decodeStream(url.openConnection().getInputStream())
            } catch (e: Exception) {
                Log.e("MainActivity", "Error loading image.", e)
            }
            return null
        }

    }

    lateinit var ivImage: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ivImage = findViewById(R.id.imageView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item != null) {
            when (item.itemId) {
                R.id.mnSearch -> {
                    Toast.makeText(this, "Menu Search", Toast.LENGTH_LONG).show()
                }
                R.id.mnInfo -> {
                    Toast.makeText(this, "Menu Info", Toast.LENGTH_LONG).show()
                }
            }
        }
        return true
    }

    fun clickWorkerThread(view: View) {
        Toast.makeText(this, "Work Thread", Toast.LENGTH_LONG).show()

        Thread(Runnable {
            val bitmap: Bitmap? = loadImage(IMAGE_URL)
            ivImage.post {
                ivImage.setImageBitmap(bitmap)
            }
        }).start()
    }

    fun clickAsyncTask(view: View) {
        Toast.makeText(this, "Async Task", Toast.LENGTH_LONG).show()

        MyAsyncTask(this).execute()
    }

}
