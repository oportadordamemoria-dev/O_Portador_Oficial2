package com.example.data.game.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MemoryGameDao {

    @Query("SELECT * FROM memory_game_stats WHERE id = 1 LIMIT 1")
    fun getStats(): Flow<MemoryGameStatsEntity?>

    @Query("SELECT * FROM memory_game_stats WHERE id = 1 LIMIT 1")
    suspend fun getStatsDirect(): MemoryGameStatsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStats(stats: MemoryGameStatsEntity)
}
