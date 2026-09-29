package com.example.data.game.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "memory_game_stats")
data class MemoryGameStatsEntity(
    @PrimaryKey val id: Int = 1,
    val highScore: Int = 0,
    val recoveredMemories: Int = 0,
    val highestLevelUnlocked: Int = 1,
    val maxComboStreak: Int = 0,
    val totalMatches: Int = 0,
    val totalGamesPlayed: Int = 0,
    val infiniteModeRecord: Int = 0,
    val unlockedEnvironments: String = "blumenau",
    val lastPlayedTimestamp: Long = System.currentTimeMillis()
)
