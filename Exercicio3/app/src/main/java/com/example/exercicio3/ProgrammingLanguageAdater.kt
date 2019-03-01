package com.example.exercicio3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.programming_language.view.*

class ProgrammingLanguageAdater(
    private val programmingLanguages: List<ProgrammingLanguage>,
    private val listener: (ProgrammingLanguage) -> Unit
) : RecyclerView.Adapter<ProgrammingLanguageAdater.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.programming_language, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return programmingLanguages.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val programmingLanguage = programmingLanguages[position]
        holder.bindView(programmingLanguage, listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(programmingLanguage: ProgrammingLanguage, listener:
            (ProgrammingLanguage) -> Unit) = with(itemView) {
            ivMain.setImageResource(programmingLanguage.imageResourceId)
            tvTitle.text = programmingLanguage.title
            tvLaunchYear.text = programmingLanguage.year.toString()
            tvDescription.text = programmingLanguage.description

            itemView.setOnClickListener {
                listener(programmingLanguage)
            }
        }
    }

}
