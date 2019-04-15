package com.example.aula10.viewmodel

import android.app.Application
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import com.example.aula10.entities.Word
import com.example.aula10.repository.WordRepository

class WordViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val PENDING_DELETE_TIMEOUT = 3000L
    }

    private val repository = WordRepository(application)

    private val handler = Handler()
    private val pendingRunnables: MutableMap<String, Runnable> = mutableMapOf()
    private val itemsPendingDelete: MutableList<Word> = mutableListOf()

    val allWords = repository.allWords

    fun insert(word: Word) {
        repository.insert(word)
    }

    fun isPendingDelete(word: Word): Boolean = itemsPendingDelete.contains(word)

    fun pendingDelete(word: Word) {
        if (!isPendingDelete(word)) {
            itemsPendingDelete.add(word)

            val pendingDeleteRunnable = Runnable {
                delete(word)
            }
            handler.postDelayed(pendingDeleteRunnable, PENDING_DELETE_TIMEOUT)
            pendingRunnables[word.word] = pendingDeleteRunnable
        }
    }

    fun delete(word: Word) {

        if (isPendingDelete(word)) {
            itemsPendingDelete.remove(word)
        }
        repository.delete(word)
    }

    fun deleteAll() {
        itemsPendingDelete.clear()
        repository.deleteAll()
    }

}