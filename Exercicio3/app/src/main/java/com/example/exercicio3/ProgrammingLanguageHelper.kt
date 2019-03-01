package com.example.exercicio3

import android.app.Activity
import android.widget.ImageView
import android.widget.TextView

class ProgrammingLanguageHelper(private val context: Activity) {

    private val ivProgrammingLanguage: ImageView = context.findViewById(R.id.ivProgrammingLanguage)
    private val etTitle: TextView = context.findViewById(R.id.etTitle)
    private val etLaunchYear: TextView = context.findViewById(R.id.etLaunchYear)
    private val etDescription: TextView = context.findViewById(R.id.etDescription)

    fun bindView(programmingLanguage: ProgrammingLanguage?) {

        if (programmingLanguage != null) {

            ivProgrammingLanguage.setImageResource(programmingLanguage.imageResourceId)
            etTitle.text = programmingLanguage.title
            etLaunchYear.text = programmingLanguage.year.toString()
            etDescription.text = programmingLanguage.description
        }
    }

    fun getModel(): ProgrammingLanguage? {
        val title = etTitle.text.toString()
        val launchYear = etLaunchYear.text.toString()
        val description = etDescription.text.toString()

        return if (isValid(title, launchYear, description)) {
            ProgrammingLanguage(R.drawable.ic_developer_board,
                title, launchYear.toInt(), description)
        } else {
            null
        }
    }

    private fun isValid(title: String, launchYear: String, description: String): Boolean {
        var result = true

        if (description.isEmpty()) {
            etDescription.error = context.getString(R.string.msg_description_required)
            result = false
            etDescription.requestFocus()
        } else {
            etDescription.error = null
        }

        if (launchYear.isEmpty()) {
            etLaunchYear.error = context.getString(R.string.msg_launch_year_required)
            result = false
            etLaunchYear.requestFocus()
        } else {
            etLaunchYear.error = null
        }

        if (title.isEmpty()) {
            etTitle.error = context.getString(R.string.msg_title_required)
            result = false
            etTitle.requestFocus()
        } else {
            etTitle.error = null
        }
        return result
    }
}