package com.example.aula10.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.aula10.dao.WordDao
import com.example.aula10.entities.Word
import org.jetbrains.anko.doAsync

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class WordRoomDatabase: RoomDatabase() {

    abstract fun wordDao(): WordDao

    companion object {
        private var instance: WordRoomDatabase? = null

        private val roomDatabaseCallback = object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)

                instance?.let {
                    Log.i("WordRoomDatabase", "Banco criado")
                }
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                instance?.let {
                    Log.i("WordRoomDatabase", "Banco aberto")
                    doAsync {
                        val dao = it.wordDao()

                        dao.deleteAll()

                        val word1 = Word("PagSeguro")
                        dao.insert(word1)

                        val word2 = Word("Kotlin Android")
                        dao.insert(word2)
                    }
                }
            }
        }

        fun getDatabase(context: Context): WordRoomDatabase {

            if (instance == null) {
                synchronized(WordRoomDatabase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordRoomDatabase::class.java,
                        "word_database"
                    ).addCallback(roomDatabaseCallback).build()
                }
            }
            return instance!!
        }
    }
}