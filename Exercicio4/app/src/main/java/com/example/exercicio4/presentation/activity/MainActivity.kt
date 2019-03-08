package com.example.exercicio4.presentation.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.exercicio4.R
import com.example.exercicio4.domain.entity.Book
import com.example.exercicio4.domain.entity.User
import com.example.exercicio4.presentation.adapter.BookAdapter
import com.example.exercicio4.util.ImageUtils
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.cancelButton
import org.jetbrains.anko.longToast
import org.jetbrains.anko.okButton

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {

        const val BOOKS_PARCELABLE = "BOOKS_PARCELABLE"

        const val BOOK_PARCELABLE = "BOOK_PARCELABLE"

        const val RESULT = "RESULT"

        const val DETAILS_REQUEST_CODE = 1

        const val EDIT_REQUEST_CODE = 2
    }

    private var books: MutableList<Book>? = null
    private var book: Book? = null
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (books == null) {
            books = mutableListOf(
                Book(
                    Book.nextVal(),
                    "https://logodetimes.com/times/flamengo/logo-flamengo-256.png",
                    null, "Flamengo", "CRF", 1981, "Mundial e Libertadores"
                )
            )
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BookAdapter(books!!,
            onClickListener = {
                edit(it)
        }, onLongClickListener = {
                delete(it)
        })

        fab.setOnClickListener { view ->
            val intent = Intent(this, EditActivity::class.java)
            startActivityForResult(
                intent,
                EDIT_REQUEST_CODE
            )
        }

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        val navHeader = nav_view.getHeaderView(0)

        val ivPhoto: ImageView = navHeader.findViewById(R.id.ivPhoto)
        val tvName: TextView = navHeader.findViewById(R.id.tvName)
        val tvEmail: TextView = navHeader.findViewById(R.id.tvEmail)

        this.user = intent.getParcelableExtra(LoginActivity.USER)

        longToast("Seja bem vindo ${user.name}!!!")

        Glide.with(this).load(user.photoUrl)
            .crossFade()
            .thumbnail(0.5f)
            .bitmapTransform(ImageUtils(this))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(ivPhoto)
        tvName.text = user.name
        tvEmail.text = user.email
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.let { saveState ->
            saveState.putParcelable(BOOK_PARCELABLE, book)
            val state = recyclerView.layoutManager!!.onSaveInstanceState()
            state?.let {
                saveState.putParcelable(BOOKS_PARCELABLE, it)
            }
        }
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState?.let { savedState ->
            this.book = savedState.getParcelable(BOOK_PARCELABLE)
            val state = savedState.getParcelable(BOOKS_PARCELABLE) as Parcelable?
            state?.let {
                recyclerView.layoutManager!!.onRestoreInstanceState(it)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && data != null) {
            when (requestCode) {
                DETAILS_REQUEST_CODE -> {
                    val book: Book? = data.getParcelableExtra(RESULT)
                    save(book)
                    data.removeExtra(RESULT)
                }
                EDIT_REQUEST_CODE -> {
                    val book: Book? = data.getParcelableExtra(RESULT)
                    save(book)
                    data.removeExtra(RESULT)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            R.id.logoff -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun save(book: Book?) {
        book?.let {
            books?.remove(book)

            val savedBook = preSave(it)

            books?.add(savedBook)
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

    private fun preSave(book: Book): Book {
        if (book.id == -1) {
            book.id = Book.nextVal()
        }
        return book
    }

    private fun edit(book: Book) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(RESULT, book)
        startActivityForResult(
            intent,
            DETAILS_REQUEST_CODE
        )
    }

    private fun delete(book: Book): Boolean {
        alert(message = getString(R.string.msg_question_delete_book), title = book.title) {
            okButton { _ ->

                books?.remove(book)
                recyclerView?.adapter?.notifyDataSetChanged()
            }
            cancelButton { }
        }.show()
        return true
    }

}
