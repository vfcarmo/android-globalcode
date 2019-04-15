package com.example.aula10.view

import android.app.Activity
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
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


class MainActivity : AppCompatActivity() {

    companion object {
        private const val NEW_WORD_REQUEST_CODE = 1
    }

    private val wordViewModel by lazy {
        ViewModelProviders.of(this).get(WordViewModel::class.java)
    }

    private var paint = Paint()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        setUpRecyclerView()

        fab.setOnClickListener {
            startActivityForResult<NewWordActivity>(NEW_WORD_REQUEST_CODE)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                alert(getString(R.string.msg_confirm_delete_all), getString(R.string.title_confirm_delete_all)) {
                    yesButton {
                        wordViewModel.deleteAll()
                        longToast(getString(R.string.msg_records_deleted))
                    }
                    cancelButton {

                    }
                }.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    private fun setUpRecyclerView() {
        val adapter = WordListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        wordViewModel.allWords.observe(this, Observer {
            adapter.items = it
        })
        setUpItemTouchHelper()
        setUpAnimationDecoratorHelper()
    }

    private fun setUpItemTouchHelper() {
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
                        wordViewModel.pendingDelete(word)
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
                        paint.color = Color.parseColor("#388E3C")
                        val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                        c.drawRect(background, paint)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_border_color_white_24dp)
                        val iconDest = RectF(itemView.left.toFloat() + width, itemView.top.toFloat() + width, itemView.left.toFloat() + 2 * width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, iconDest, paint)
                    } else {
                        paint.color = Color.parseColor("#D32F2F")
                        val background = RectF(itemView.right.toFloat() + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
                        c.drawRect(background, paint)
                        icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete_sweep_white_24dp)
                        val iconDest = RectF(itemView.right.toFloat() - 2 * width, itemView.top.toFloat() + width, itemView.right.toFloat() - width, itemView.bottom.toFloat() - width)
                        c.drawBitmap(icon, null, iconDest, paint)
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }
        }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setUpAnimationDecoratorHelper() {
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            var background: Drawable? = null
            var initiated: Boolean = false

            private fun init() {
                background = ColorDrawable(Color.RED)
                initiated = true
            }

            override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

                if (!initiated) {
                    init()
                }
                parent.itemAnimator?.let {
                    if (it.isRunning) {
                        var lastViewComingDown: View? = null
                        var firstViewComingUp: View? = null

                        val left = 0
                        val right = parent.width

                        var top = 0
                        var bottom = 0

                        val childCount = parent.layoutManager?.childCount!!

                        for (i in 0 until childCount) {
                            val child = parent.layoutManager?.getChildAt(i)!!
                            if (child.translationY < 0) {
                                lastViewComingDown = child
                            } else if (child.translationY > 0) {
                                if (firstViewComingUp == null) {
                                    firstViewComingUp = child
                                }
                            }
                        }

                        if (lastViewComingDown != null && firstViewComingUp != null) {
                            top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                            bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                        } else if (lastViewComingDown != null) {
                            top = lastViewComingDown.bottom + lastViewComingDown.translationY.toInt()
                            bottom = lastViewComingDown.bottom
                        } else if (firstViewComingUp != null) {
                            top = firstViewComingUp.top
                            bottom = firstViewComingUp.top + firstViewComingUp.translationY.toInt()
                        }
                        background?.setBounds(left, top, right, bottom)
                        background?.draw(c)
                    }
                }
                super.onDraw(c, parent, state)
            }
        })
    }
}
