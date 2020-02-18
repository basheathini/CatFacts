package com.basheathini.catfacts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

@Database(entities = arrayOf (Cat::class), version = 1, exportSchema = false)

abstract class CatRoomDatabase : RoomDatabase() {

    abstract fun catDao(): CatDao

    private class CatDatabaseCallback( private val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database -> scope.launch { val catDao = database.catDao()

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: CatRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): CatRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CatRoomDatabase::class.java,
                    "cat_database"
                ).addCallback(CatDatabaseCallback(scope)).build()
                INSTANCE = instance
                instance
            }
        }
    }
}