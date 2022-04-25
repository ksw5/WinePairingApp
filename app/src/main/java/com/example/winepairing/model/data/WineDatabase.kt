package com.example.winepairing.model.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [ProductMatches::class], version = 1)
abstract class WineDatabase: RoomDatabase() {
    abstract fun wineDao(): WineDao

    companion object {
        @Volatile
        private var INSTANCE: WineDatabase? = null
        fun getInstance(context: Context): WineDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    WineDatabase::class.java,
                    "wine_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}