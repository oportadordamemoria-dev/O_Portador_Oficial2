package com.example.data.game.db

data class PlayerProfile(
    val playerId: String,
    val playerName: String,
    val normalizedName: String,
    val createdAt: Long,
    val lastPlayedAt: Long,
    val currentLevel: Int = 1,
    val highestLevelUnlocked: Int = 1,
    val totalScore: Int = 0,
    val highScore: Int = 0,
    val recoveredMemories: Int = 0,
    val maxComboStreak: Int = 0,
    val totalMatches: Int = 0,
    val totalGamesPlayed: Int = 0,
    val infiniteModeRecord: Int = 0,
    val unlockedEnvironments: List<String> = listOf("blumenau"),
    val rankTitle: String = "Lembrança"
)

fun PlayerProfileEntity.toDomain(): PlayerProfile {
    val envs = unlockedEnvironments.split(",").filter { it.isNotBlank() }
    val title = MemoryGameRepository.getTitleForLevel(highestLevelUnlocked)
    return PlayerProfile(
        playerId = playerId,
        playerName = playerName,
        normalizedName = normalizedName,
        createdAt = createdAt,
        lastPlayedAt = lastPlayedAt,
        currentLevel = currentLevel,
        highestLevelUnlocked = highestLevelUnlocked,
        totalScore = totalScore,
        highScore = highScore,
        recoveredMemories = recoveredMemories,
        maxComboStreak = maxComboStreak,
        totalMatches = totalMatches,
        totalGamesPlayed = totalGamesPlayed,
        infiniteModeRecord = infiniteModeRecord,
        unlockedEnvironments = if (envs.isEmpty()) listOf("blumenau") else envs,
        rankTitle = title
    )
}
