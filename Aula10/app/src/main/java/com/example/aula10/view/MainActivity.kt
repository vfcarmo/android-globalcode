package com.example.aula10.view

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aula10.NewWordActivity
import com.example.aula10.R
import com.example.aula10.entities.Word
import com.example.aula10.viewmodel.WordViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.design.longSnackbar
import android.graphics.RectF
import android.graphics.BitmapFactory

class MainActivity : AppCompatActivity() {

    companion object {
        private const val NEW_WORD_REQUEST_CODE = 1
    }

    private var view: View? = null
    private var add = false
    private var edit_position: Int = -1
    private var p = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        wordViewModel.allWords.observe(this, Observer {
            adapter.items = it
        })
        initSwipe()

        fab.setOnClickListener { view ->
            startActivityForResult<NewWordActivity>(NEW_WORD_REQUEST_CODE)
        }
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val word = wordViewModel.allWords.value?.let { it[position] }

                if (direction == ItemTouchHelper.LEFT) {
                    word?.let {
                        wordViewModel.delete(word)
                    }
                } else {
                    longToast("Editando o word: ${word?.word}")
                }
            }

            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                                     viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                                     actionState: Int, isCurrentlyActive: Boolean) {
                val icon: Bitmap

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                    val itemView = viewHolder.itemView
                    val height = (itemView.bottom - itemView.top).toFloat()
                    val width = height / 3

                    if (dX > 0) {
                        p.setColor(Color.parseColor("#388E3C"))
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_border_color_white_24dp)
                        val icon_dest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    } else {
                        p.color = Color.parseColor("#D32F2F")
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, p)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_sweep_white_24dp)
                        val icon_dest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, icon_dest, p)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private val wordViewModel by lazy {
        ViewModelProviders.of(this).get(WordViewModel::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NEW_WORD_REQUEST_CODE) {

            when (resultCode) {
                Activity.RESULT_OK -> {
                    data?.let {
                        val word = Word(data.getStringExtra(NewWordActivity.WORD_KEY))
                        wordViewModel.insert(word)
                    }
                }
                Activity.RESULT_CANCELED -> {
                    fab.longSnackbar("Word was empty.", "Try again") {
                        fab.performClick()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_delete_all -> {
                alert("Deseja excluir todos os registros?", "Confirmação de exclusão") {
                    yesButton {
                        wordViewModel.deleteAll()
                        longToast("Todos os registros excluídos")
                    }
                    cancelButton {

                    }
                }.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
