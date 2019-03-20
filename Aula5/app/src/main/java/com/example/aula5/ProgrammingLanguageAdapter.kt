package com.example.aula5

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.programming_language.view.*

class ProgrammingLanguageAdapter(
    private val items: List<ProgrammingLanguage>,
    private val listener: (ProgrammingLanguage) -> Unit) :
    RecyclerView.Adapter<ProgrammingLanguageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.programming_language, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val programmingLanguage = items[position]
        holder.bindView(programmingLanguage, listener)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(item: ProgrammingLanguage, listener: (ProgrammingLanguage) -> Unit) = with(itemView) {
            ivMain.setImageResource(item.imageResourceId)
            tvTitle.text = item.title
            tvLaunchYear.text = item.launchYear.toString()
            setOnClickListener { listener(item) }
        }
    }

}