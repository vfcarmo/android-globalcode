package com.example.aula2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailsActivity : AppCompatActivity() {

    companion object {
        const val TEXT = "TEXT"
    }

    private var persistText: String? = null

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val intent: Intent? = intent
        if (intent != null) {
            val info = intent.getStringExtra("INFO")
            if (info != null) {
                Toast.makeText(this, info, Toast.LENGTH_LONG).show()
            }
        }

        this.textView = findViewById(R.id.textView)
        val editText: EditText = findViewById(R.id.editText)
        val btApplyChanves: Button = findViewById(R.id.btApplyChanges)
        val btKeepValue: Button = findViewById(R.id.btKeepValue)
        val btResetValue: Button = findViewById(R.id.btResetValue)

//        if (savedInstanceState != null) {
//            val textPersisted = savedInstanceState.getString(TEXT)
//            textView.setText(textPersisted)
//        }

        btApplyChanves.setOnClickListener {
            val text = editText.text.toString()
            textView.text = text

            //Setting result
            intent?.putExtra("RESULT", text)
            setResult(Activity.RESULT_OK, intent)
            this.finish()
        }

        btKeepValue.setOnClickListener {
            persistText = editText.text.toString()
            Toast.makeText(this, "Persisted Value: $persistText", Toast.LENGTH_LONG).show()
        }

        btResetValue.setOnClickListener {
            persistText = null
            Toast.makeText(this, "Value reseted.", Toast.LENGTH_LONG).show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        if (persistText != null && persistText!!.isNotEmpty()) {
            outState?.putString(TEXT, persistText)
        } else {
            outState?.remove(TEXT)
        }

        val userSerializable = UserSerializable("Vitor", "Carmo")
        val userParcelable = UserParcelable("Vitor", "Carmo")
        outState?.putSerializable("USER_SERIALIZABLE", userSerializable)
        outState?.putParcelable("USER_PARCELABLE", userParcelable)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        this.persistText = savedInstanceState?.getString(TEXT)

        if (!persistText.isNullOrEmpty()) {
            textView.text = persistText
        }

        val userSerializable: UserSerializable? = savedInstanceState
            ?.getSerializable("USER_SERIALIZABLE") as UserSerializable

        if (userSerializable != null) {
            Log.d(localClassName, userSerializable.nome)
            Log.d(localClassName, userSerializable.sobrenome)
        }

        val userParcelable: UserParcelable? = savedInstanceState
            .getParcelable("USER_PARCELABLE") as UserParcelable

        if (userParcelable != null) {
            Log.d(localClassName, userParcelable.nome)
            Log.d(localClassName, userParcelable.sobrenome)
        }
    }
}
