package com.example.exercicio3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProgrammingLanguageAdater(programmingLanguages: List<ProgrammingLanguage>, listener: View.OnClickListener) : RecyclerView.Adapter<ProgrammingLanguageAdater.ViewHolder>() {

    private val programmingLanguages: List<ProgrammingLanguage> = programmingLanguages
    private val listener: View.OnClickListener = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.programming_language, parent, false)
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

        fun bindView(programmingLanguage: ProgrammingLanguage, listener: View.OnClickListener) {
            val ivMain = itemView.findViewById<ImageView>(R.id.ivMain)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
            val tvLaunchYear = itemView.findViewById<TextView>(R.id.tvLaunchYear)
            val tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)

            ivMain.setImageResource(programmingLanguage.imageResourceId)
            tvTitle.text = programmingLanguage.title
            tvLaunchYear.text = programmingLanguage.year.toString()
            tvDescription.text = programmingLanguage.description

            itemView.setOnClickListener(listener)
        }
    }

}
