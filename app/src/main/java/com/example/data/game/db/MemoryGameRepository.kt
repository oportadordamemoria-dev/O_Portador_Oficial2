package com.example.data.game.db

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

data class GameProgressData(
    val highScore: Int = 0,
    val recoveredMemories: Int = 0,
    val highestLevelUnlocked: Int = 1,
    val maxComboStreak: Int = 0,
    val totalMatches: Int = 0,
    val totalGamesPlayed: Int = 0,
    val infiniteModeRecord: Int = 0,
    val unlockedEnvironments: List<String> = listOf("blumenau"),
    val rankTitle: String = "Lembrança"
)

fun PlayerProfile.toGameProgressData(): GameProgressData {
    return GameProgressData(
        highScore = highScore,
        recoveredMemories = recoveredMemories,
        highestLevelUnlocked = highestLevelUnlocked,
        maxComboStreak = maxComboStreak,
        totalMatches = totalMatches,
        totalGamesPlayed = totalGamesPlayed,
        infiniteModeRecord = infiniteModeRecord,
        unlockedEnvironments = unlockedEnvironments,
        rankTitle = rankTitle
    )
}

class MemoryGameRepository(
    private val dao: MemoryGameDao,
    val playerRepository: PlayerRepository? = null
) {

    val gameProgress: Flow<GameProgressData> = if (playerRepository != null) {
        playerRepository.activePlayer.combine(dao.getStats()) { player, entityStats ->
            when {
                player != null -> player.toGameProgressData()
                entityStats != null -> entityStats.toDomain()
                else -> GameProgressData()
            }
        }
    } else {
        dao.getStats().map { entity ->
            entity?.toDomain() ?: GameProgressData()
        }
    }

    suspend fun recordGameFinished(
        score: Int,
        newRecoveredMemories: Int,
        comboStreak: Int,
        levelCompleted: Int,
        environmentUnlocked: String? = null
    ) {
        // Grava no perfil do jogador ativo
        playerRepository?.recordGameFinished(
            playerId = null,
            score = score,
            newRecoveredMemories = newRecoveredMemories,
            comboStreak = comboStreak,
            levelCompleted = levelCompleted,
            environmentUnlocked = environmentUnlocked
        )

        // Também grava na tabela local de estatísticas para compatibilidade
        val current = dao.getStatsDirect() ?: MemoryGameStatsEntity()
        val currentEnvs = current.unlockedEnvironments.split(",").filter { it.isNotBlank() }.toMutableSet()
        if (environmentUnlocked != null && environmentUnlocked.isNotBlank()) {
            currentEnvs.add(environmentUnlocked)
        }

        val updated = current.copy(
            highScore = maxOf(current.highScore, score),
            recoveredMemories = current.recoveredMemories + newRecoveredMemories,
            highestLevelUnlocked = maxOf(current.highestLevelUnlocked, minOf(5, levelCompleted + 1)),
            maxComboStreak = maxOf(current.maxComboStreak, comboStreak),
            totalMatches = current.totalMatches + 1,
            totalGamesPlayed = current.totalGamesPlayed + 1,
            unlockedEnvironments = currentEnvs.joinToString(","),
            lastPlayedTimestamp = System.currentTimeMillis()
        )
        dao.saveStats(updated)
    }

    suspend fun recordInfiniteScore(streak: Int) {
        playerRepository?.recordInfiniteScore(playerId = null, streak = streak)

        val current = dao.getStatsDirect() ?: MemoryGameStatsEntity()
        val updated = current.copy(
            infiniteModeRecord = maxOf(current.infiniteModeRecord, streak),
            recoveredMemories = current.recoveredMemories + streak,
            maxComboStreak = maxOf(current.maxComboStreak, streak),
            totalGamesPlayed = current.totalGamesPlayed + 1,
            lastPlayedTimestamp = System.currentTimeMillis()
        )
        dao.saveStats(updated)
    }

    companion object {
        fun getTitleForLevel(level: Int): String {
            return when (level) {
                1 -> "Lembrança"
                2 -> "Guardião"
                3 -> "Protetor"
                4 -> "Guardião da Memória"
                5 -> "Portador da Memória"
                else -> "Portador da Memória"
            }
        }

        @Volatile
        private var INSTANCE: MemoryGameRepository? = null

        fun getInstance(context: Context): MemoryGameRepository {
            return INSTANCE ?: synchronized(this) {
                val db = MemoryGameDatabase.getInstance(context)
                val playerRepo = PlayerRepository.getInstance(context)
                val repo = MemoryGameRepository(db.memoryGameDao(), playerRepo)
                INSTANCE = repo
                repo
            }
        }
    }
}

private fun MemoryGameStatsEntity.toDomain(): GameProgressData {
    val envs = unlockedEnvironments.split(",").filter { it.isNotBlank() }
    val title = MemoryGameRepository.getTitleForLevel(highestLevelUnlocked)
    return GameProgressData(
        highScore = highScore,
        recoveredMemories = recoveredMemories,
        highestLevelUnlocked = highestLevelUnlocked,
        maxComboStreak = maxComboStreak,
        totalMatches = totalMatches,
        totalGamesPlayed = totalGamesPlayed,
        infiniteModeRecord = infiniteModeRecord,
        unlockedEnvironments = if (envs.isEmpty()) listOf("blumenau") else envs,
        rankTitle = title
    )
}
