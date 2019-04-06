package com.example.aula10.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.aula10.dao.WordDao
import com.example.aula10.db.WordRoomDatabase
import com.example.aula10.entities.Word
import org.jetbrains.anko.doAsync

class WordRepository(application: Application) {

    private val wordDao: WordDao

    val allWords: LiveData<List<Word>>

    init {
        val db = WordRoomDatabase.getDatabase(application)
        wordDao = db.wordDao()
        allWords = wordDao.getAllWords()
    }

    fun insert(word: Word) {
        doAsync {
            wordDao.insert(word)
        }
    }

    fun delete(word: Word) {
        doAsync {
            wordDao.delete(word)
        }
    }

    fun deleteAll() {
        doAsync {
            wordDao.deleteAll()
        }
    }

}