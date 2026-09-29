package com.example.data.game.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MemoryGameStatsEntity::class, PlayerProfileEntity::class],
    version = 3,
    exportSchema = false
)
abstract class MemoryGameDatabase : RoomDatabase() {

    abstract fun memoryGameDao(): MemoryGameDao
    abstract fun playerDao(): PlayerDao

    companion object {
        @Volatile
        private var INSTANCE: MemoryGameDatabase? = null

        fun getInstance(context: Context): MemoryGameDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MemoryGameDatabase::class.java,
                    "memory_game.db"
                ).fallbackToDestructiveMigration(dropAllTables = true).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
