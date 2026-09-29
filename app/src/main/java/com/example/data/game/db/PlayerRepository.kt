package com.example.data.game.db

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

sealed interface CreatePlayerResult {
    data class Success(val player: PlayerProfile) : CreatePlayerResult
    data class DuplicateName(val existingPlayer: PlayerProfile) : CreatePlayerResult
    data class Error(val message: String) : CreatePlayerResult
}

sealed interface RenamePlayerResult {
    data object Success : RenamePlayerResult
    data class DuplicateName(val existingPlayer: PlayerProfile) : RenamePlayerResult
    data class Error(val message: String) : RenamePlayerResult
}

class PlayerRepository(
    private val playerDao: PlayerDao,
    private val localGameStorage: LocalGameStorage
) {
    private val repositoryScope = CoroutineScope(Dispatchers.IO)

    private val _activePlayerIdState = MutableStateFlow<String?>(localGameStorage.getActivePlayerId())
    val activePlayerIdState = _activePlayerIdState.asStateFlow()

    val allPlayers: Flow<List<PlayerProfile>> = playerDao.getAllPlayers().map { entities ->
        entities.map { it.toDomain() }
    }

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val activePlayer: Flow<PlayerProfile?> = _activePlayerIdState.flatMapLatest { id ->
        if (id == null) {
            flowOf(null)
        } else {
            playerDao.getPlayerById(id).map { it?.toDomain() }
        }
    }.distinctUntilChanged()

    init {
        // Inicializa o jogador ativo caso exista pelo menos um e nenhum esteja selecionado
        repositoryScope.launch {
            val currentId = localGameStorage.getActivePlayerId()
            if (currentId == null) {
                val all = playerDao.getAllPlayersDirect()
                if (all.isNotEmpty()) {
                    val first = all.first()
                    localGameStorage.setActivePlayerId(first.playerId)
                    _activePlayerIdState.value = first.playerId
                }
            } else {
                val exists = playerDao.getPlayerByIdDirect(currentId)
                if (exists == null) {
                    val all = playerDao.getAllPlayersDirect()
                    if (all.isNotEmpty()) {
                        val first = all.first()
                        localGameStorage.setActivePlayerId(first.playerId)
                        _activePlayerIdState.value = first.playerId
                    } else {
                        localGameStorage.clearActivePlayerId()
                        _activePlayerIdState.value = null
                    }
                }
            }
        }
    }

    suspend fun getPlayerCount(): Int = withContext(Dispatchers.IO) {
        playerDao.getPlayerCount()
    }

    suspend fun getActivePlayerDirect(): PlayerProfile? = withContext(Dispatchers.IO) {
        val id = _activePlayerIdState.value ?: localGameStorage.getActivePlayerId() ?: return@withContext null
        playerDao.getPlayerByIdDirect(id)?.toDomain()
    }

    suspend fun findExistingByNormalizedName(rawName: String): PlayerProfile? = withContext(Dispatchers.IO) {
        val normalized = normalizeName(rawName)
        playerDao.getPlayerByNormalizedName(normalized)?.toDomain()
    }

    suspend fun createPlayer(rawName: String): CreatePlayerResult = withContext(Dispatchers.IO) {
        val trimmed = rawName.trim()
        if (trimmed.isBlank()) {
            return@withContext CreatePlayerResult.Error("O nome não pode estar em branco.")
        }
        val normalized = normalizeName(trimmed)

        val existing = playerDao.getPlayerByNormalizedName(normalized)
        if (existing != null) {
            return@withContext CreatePlayerResult.DuplicateName(existing.toDomain())
        }

        val newId = UUID.randomUUID().toString()
        val now = System.currentTimeMillis()
        val newEntity = PlayerProfileEntity(
            playerId = newId,
            playerName = trimmed,
            normalizedName = normalized,
            createdAt = now,
            lastPlayedAt = now,
            currentLevel = 1,
            highestLevelUnlocked = 1,
            totalScore = 0,
            highScore = 0,
            recoveredMemories = 0,
            maxComboStreak = 0,
            totalMatches = 0,
            totalGamesPlayed = 0,
            infiniteModeRecord = 0,
            unlockedEnvironments = "blumenau"
        )
        playerDao.insertOrUpdate(newEntity)
        localGameStorage.setActivePlayerId(newId)
        _activePlayerIdState.value = newId

        CreatePlayerResult.Success(newEntity.toDomain())
    }

    suspend fun setActivePlayer(playerId: String): Boolean = withContext(Dispatchers.IO) {
        val player = playerDao.getPlayerByIdDirect(playerId) ?: return@withContext false
        val updated = player.copy(lastPlayedAt = System.currentTimeMillis())
        playerDao.insertOrUpdate(updated)
        localGameStorage.setActivePlayerId(playerId)
        _activePlayerIdState.value = playerId
        true
    }

    suspend fun renamePlayer(playerId: String, newRawName: String): RenamePlayerResult = withContext(Dispatchers.IO) {
        val trimmed = newRawName.trim()
        if (trimmed.isBlank()) {
            return@withContext RenamePlayerResult.Error("O nome não pode estar em branco.")
        }
        val normalized = normalizeName(trimmed)

        val existing = playerDao.getPlayerByNormalizedName(normalized)
        if (existing != null && existing.playerId != playerId) {
            return@withContext RenamePlayerResult.DuplicateName(existing.toDomain())
        }

        playerDao.updatePlayerName(playerId, trimmed, normalized)
        RenamePlayerResult.Success
    }

    suspend fun deletePlayer(playerId: String): Boolean = withContext(Dispatchers.IO) {
        playerDao.deletePlayer(playerId)
        if (_activePlayerIdState.value == playerId) {
            val remaining = playerDao.getAllPlayersDirect()
            if (remaining.isNotEmpty()) {
                val nextActive = remaining.first()
                localGameStorage.setActivePlayerId(nextActive.playerId)
                _activePlayerIdState.value = nextActive.playerId
            } else {
                localGameStorage.clearActivePlayerId()
                _activePlayerIdState.value = null
            }
        }
        true
    }

    suspend fun recordGameFinished(
        playerId: String?,
        score: Int,
        newRecoveredMemories: Int,
        comboStreak: Int,
        levelCompleted: Int,
        environmentUnlocked: String? = null
    ) = withContext(Dispatchers.IO) {
        val targetId = playerId ?: _activePlayerIdState.value ?: localGameStorage.getActivePlayerId() ?: return@withContext
        val current = playerDao.getPlayerByIdDirect(targetId) ?: return@withContext

        val currentEnvs = current.unlockedEnvironments.split(",").filter { it.isNotBlank() }.toMutableSet()
        if (!environmentUnlocked.isNullOrBlank()) {
            currentEnvs.add(environmentUnlocked)
        }

        val updated = current.copy(
            totalScore = current.totalScore + score,
            highScore = maxOf(current.highScore, score),
            recoveredMemories = current.recoveredMemories + newRecoveredMemories,
            currentLevel = minOf(5, levelCompleted + 1),
            highestLevelUnlocked = maxOf(current.highestLevelUnlocked, minOf(5, levelCompleted + 1)),
            maxComboStreak = maxOf(current.maxComboStreak, comboStreak),
            totalMatches = current.totalMatches + 1,
            totalGamesPlayed = current.totalGamesPlayed + 1,
            unlockedEnvironments = currentEnvs.joinToString(","),
            lastPlayedAt = System.currentTimeMillis()
        )
        playerDao.insertOrUpdate(updated)
    }

    suspend fun recordInfiniteScore(playerId: String?, streak: Int) = withContext(Dispatchers.IO) {
        val targetId = playerId ?: _activePlayerIdState.value ?: localGameStorage.getActivePlayerId() ?: return@withContext
        val current = playerDao.getPlayerByIdDirect(targetId) ?: return@withContext

        val updated = current.copy(
            infiniteModeRecord = maxOf(current.infiniteModeRecord, streak),
            recoveredMemories = current.recoveredMemories + streak,
            maxComboStreak = maxOf(current.maxComboStreak, streak),
            totalGamesPlayed = current.totalGamesPlayed + 1,
            lastPlayedAt = System.currentTimeMillis()
        )
        playerDao.insertOrUpdate(updated)
    }

    companion object {
        fun normalizeName(name: String): String {
            return name.trim().lowercase()
        }

        @Volatile
        private var INSTANCE: PlayerRepository? = null

        fun getInstance(context: Context): PlayerRepository {
            return INSTANCE ?: synchronized(this) {
                val db = MemoryGameDatabase.getInstance(context)
                val storage = LocalGameStorage.getInstance(context)
                val repo = PlayerRepository(db.playerDao(), storage)
                INSTANCE = repo
                repo
            }
        }
    }
}
