package com.example.aula10.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.aula10.entities.Word
import com.example.aula10.repository.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = WordRepository(application)

    val allWords = repository.allWords

    fun insert(word: Word) {
        repository.insert(word)
    }

    fun delete(word: Word) {
        repository.delete(word)
    }

    fun deleteAll() {
        repository.deleteAll()
    }
}