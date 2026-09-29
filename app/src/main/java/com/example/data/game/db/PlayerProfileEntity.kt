package com.example.data.game.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "player_profiles")
data class PlayerProfileEntity(
    @PrimaryKey val playerId: String,
    val playerName: String,
    val normalizedName: String,
    val createdAt: Long = System.currentTimeMillis(),
    val lastPlayedAt: Long = System.currentTimeMillis(),
    val currentLevel: Int = 1,
    val highestLevelUnlocked: Int = 1,
    val totalScore: Int = 0,
    val highScore: Int = 0,
    val recoveredMemories: Int = 0,
    val maxComboStreak: Int = 0,
    val totalMatches: Int = 0,
    val totalGamesPlayed: Int = 0,
    val infiniteModeRecord: Int = 0,
    val unlockedEnvironments: String = "blumenau"
)
