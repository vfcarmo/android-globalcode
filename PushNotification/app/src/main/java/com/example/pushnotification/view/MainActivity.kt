package com.example.pushnotification.view

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.example.pushnotification.R
import com.example.pushnotification.domain.LoadImageType
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.longToast
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton

class MainActivity : AppCompatActivity() {

    private var loadImageType: LoadImageType = LoadImageType.GLIDE

    private val appKey = "AIzaSyBygWlmVNO5TV1A8NBVMe520hZRX0Fny4g"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btAddPlaceHolder.setOnClickListener { addPlaceHolder() }
        btClear.setOnClickListener { clearNotificationFields() }

        rgLoadImageSystem.setOnCheckedChangeListener { _, checkedId ->
            loadImageSystemChangeListener(checkedId)
        }

        rgLocalizationSystem.setOnCheckedChangeListener { _, checkedId ->
            localizationOptionChangeListener(checkedId)
        }
        btCreate.setOnClickListener { createPushNotification() }
    }

    private fun addPlaceHolder() {
        alert(getString(R.string.msg_dialog_add_placeholder), getString(R.string.title_dialog_add_placeholder)) {
            yesButton {
                etTitle.setText(R.string.placeholder_notification_title)
                etBody.setText(R.string.placeholder_notification_body)
            }
            noButton { }
        }.show()
    }

    private fun clearNotificationFields() {
        alert(getString(R.string.msg_dialog_clear_input_texts), getString(R.string.title_dialog_clear_input_texts)) {
            yesButton {
                etTitle.setText("")
                etBody.setText("")
            }
            noButton { }
        }.show()
    }

    private fun loadImageSystemChangeListener(checkedId: Int) {
        when (checkedId) {
            R.id.rbGlide -> loadImageType = LoadImageType.GLIDE
            R.id.rbPicasso -> loadImageType = LoadImageType.PICASSO
        }
    }

    private fun localizationOptionChangeListener(checkedId: Int) {
        val refereceFieldId: Int
        val frPlaces = fragmentManager
            .findFragmentById(R.id.frPlaces) as PlaceAutocompleteFragment
        val params = frPlaces.view?.layoutParams as ConstraintLayout.LayoutParams

        when (checkedId) {
            R.id.rbAddress -> {
                etSearch.visibility = View.VISIBLE
                frPlaces.view?.visibility = View.INVISIBLE

                refereceFieldId = etSearch.id

                etSearch.requestFocus()
            }
            R.id.rbPlaces -> {
                etSearch.visibility = View.INVISIBLE
                frPlaces.view?.visibility = View.VISIBLE

                refereceFieldId = frPlaces.id
            }
            else -> {
                etSearch.visibility = View.INVISIBLE
                frPlaces.view?.visibility = View.INVISIBLE

                refereceFieldId = params.topToBottom
            }
        }
        val set = ConstraintSet()
        set.clone(root)
        set.connect(rgLocalizationSystem.id, ConstraintSet.TOP, refereceFieldId, ConstraintSet.BOTTOM)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(root)
        }
        set.applyTo(root)
    }

    private fun createPushNotification() {

        when (rgLocalizationSystem.checkedRadioButtonId) {
            R.id.rbNone -> notify(false)
        }

        val msgActionButtons = if (cbActionButtons.isChecked) " with action buttons " else ""
        longToast("Creating push notification using ${loadImageType.name.toLowerCase()} as a load image system$msgActionButtons.")
    }

    private fun notify(loadingImage: Boolean) {
        val includeActions = cbActionButtons.isChecked

        NotificationCreation.create(
            this,
            etTitle.text.toString(),
            etBody.text.toString(),
            loadingImage,
            includeActions
        )
    }

}
