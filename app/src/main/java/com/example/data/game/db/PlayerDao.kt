package com.example.data.game.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Query("SELECT * FROM player_profiles ORDER BY lastPlayedAt DESC")
    fun getAllPlayers(): Flow<List<PlayerProfileEntity>>

    @Query("SELECT * FROM player_profiles ORDER BY lastPlayedAt DESC")
    suspend fun getAllPlayersDirect(): List<PlayerProfileEntity>

    @Query("SELECT * FROM player_profiles WHERE playerId = :playerId LIMIT 1")
    fun getPlayerById(playerId: String): Flow<PlayerProfileEntity?>

    @Query("SELECT * FROM player_profiles WHERE playerId = :playerId LIMIT 1")
    suspend fun getPlayerByIdDirect(playerId: String): PlayerProfileEntity?

    @Query("SELECT * FROM player_profiles WHERE normalizedName = :normalizedName LIMIT 1")
    suspend fun getPlayerByNormalizedName(normalizedName: String): PlayerProfileEntity?

    @Query("SELECT COUNT(*) FROM player_profiles")
    suspend fun getPlayerCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(player: PlayerProfileEntity)

    @Query("DELETE FROM player_profiles WHERE playerId = :playerId")
    suspend fun deletePlayer(playerId: String)

    @Query("UPDATE player_profiles SET playerName = :newName, normalizedName = :newNormalizedName WHERE playerId = :playerId")
    suspend fun updatePlayerName(playerId: String, newName: String, newNormalizedName: String)
}
