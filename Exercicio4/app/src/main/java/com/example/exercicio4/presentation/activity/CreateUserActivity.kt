package com.example.exercicio4.presentation.activity

import android.app.Activity
import android.app.ProgressDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.exercicio4.R
import com.example.exercicio4.domain.entity.User
import com.example.exercicio4.util.ImageUtils
import kotlinx.android.synthetic.main.activity_create_user.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class CreateUserActivity : AppCompatActivity() {

    private lateinit var createUserHelper: CreateUserHelper

    private lateinit var rootView: View

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        this.createUserHelper = CreateUserHelper(this)

        this.user = intent.getParcelableExtra(LoginActivity.RESULT)
        this.user?.let {
            createUserHelper.bindView(it)
            intent.removeExtra(LoginActivity.RESULT)
        }

        btSave.setOnClickListener { save() }
        btCancel.setOnClickListener { finish() }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.let {saveState ->
            user?.let {
                saveState.putParcelable(LoginActivity.USER, it)
            }
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let { savedState ->
            this.user = savedState.getParcelable(LoginActivity.USER)
            this.user?.let {
                createUserHelper.bindView(it)
                savedState.remove(LoginActivity.USER)
            }
        }
    }

    private fun save() {
        this.user = createUserHelper.getModel()
        this.user?.let {
            intent.putExtra(LoginActivity.RESULT, it)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    inner class CreateUserHelper(private val context: Activity) {

        private var photo: Bitmap? = null

        private lateinit var progresDialog: ProgressDialog

        init {
            btLoad.setOnClickListener {
                loadUserPhoto()
            }
        }

        private fun loadUserPhoto() {

            this.progresDialog = ProgressDialog.show(
                this.context,
                context.getString(R.string.label_wait),
                context.getString(R.string.msg_loading_image)
            )
            doAsync {

                val photoUrl = etPhoto.text.toString()
                val bitmap = ImageUtils.loadImage(photoUrl)

                bitmap?.let {

                    this@CreateUserHelper.photo = it

                    uiThread {

                        ivPhoto.setImageBitmap(photo)
                        progresDialog.dismiss()
                    }
                }
            }
        }

        fun bindView(user: User) {
            this.photo = user.photo
            ivPhoto.setImageBitmap(user.photo)
            etName.setText(user.name)
            etEmail.setText(user.email)
            etPhoto.setText(user.photoUrl)
        }

        fun getModel(): User? {

            val name = etName.text.toString()
            val email = etEmail.text.toString()
            val photoUrl = etPhoto.text.toString()

            return if (this.isValid(photo, name, email, photoUrl)) {
                User(photo, photoUrl, name, email)
            } else {
                null
            }
        }

        private fun isValid(
            photo: Bitmap?,
            name: String,
            email: String,
            photoUrl: String
        ): Boolean {
            var result = true

            if (photo == null) {
                result = false
                etPhoto.error = getString(R.string.msg_user_photo_must_be_loaded)
            }

            if (TextUtils.isEmpty(photoUrl)) {
                result = false
                etPhoto.error = getString(R.string.msg_user_photo_required)
                etPhoto.requestFocus()
            } else {
                etPhoto.error = null
            }

            if (TextUtils.isEmpty(email)) {
                result = false
                etEmail.error = getString(R.string.msg_email_address_required)
                etEmail.requestFocus()
            } else {
                etEmail.error = null
            }

            if (TextUtils.isEmpty(name)) {
                result = false
                etName.error = getString(R.string.msg_user_name_required)
                etName.requestFocus()
            } else {
                etName.error = null
            }
            return result
        }
    }

}
