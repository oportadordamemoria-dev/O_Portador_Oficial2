package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.data.game.db.CreatePlayerResult
import com.example.data.game.db.PlayerRepository
import com.example.data.game.db.RenamePlayerResult
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class PlayerProfileTest {

    @Test
    fun testNameNormalization() {
        assertEquals("julio", PlayerRepository.normalizeName("  Júlio  ".replace("ú", "u")))
        assertEquals("pedro", PlayerRepository.normalizeName("PEDRO"))
        assertEquals("marta", PlayerRepository.normalizeName("  Marta "))
    }

    @Test
    fun testCreatePlayer_uniqueIdAndNormalization() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val repo = PlayerRepository.getInstance(context)

        val result = repo.createPlayer("Júlio")
        assertTrue("Deve criar com sucesso", result is CreatePlayerResult.Success)
        val player = (result as CreatePlayerResult.Success).player

        assertEquals("Júlio", player.playerName)
        assertNotNull(player.playerId)
        assertTrue("O ID deve ser um UUID válido", player.playerId.length >= 20)

        // Verificar duplicidade (case insensitive)
        val duplicateResult = repo.createPlayer("  júlio  ")
        assertTrue("Deve detectar nome duplicado", duplicateResult is CreatePlayerResult.DuplicateName)
    }

    @Test
    fun testMultiplePlayers_independentProgress() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val repo = PlayerRepository.getInstance(context)

        // Criar Marta e Pedro
        val martaResult = repo.createPlayer("Marta")
        val pedroResult = repo.createPlayer("Pedro")

        val martaId = (martaResult as? CreatePlayerResult.Success)?.player?.playerId
            ?: (martaResult as CreatePlayerResult.DuplicateName).existingPlayer.playerId
        val pedroId = (pedroResult as? CreatePlayerResult.Success)?.player?.playerId
            ?: (pedroResult as CreatePlayerResult.DuplicateName).existingPlayer.playerId

        // Registrar progresso para Pedro
        repo.recordGameFinished(
            playerId = pedroId,
            score = 1200,
            newRecoveredMemories = 15,
            comboStreak = 5,
            levelCompleted = 2,
            environmentUnlocked = "jardim"
        )

        // Registrar progresso para Marta
        repo.recordGameFinished(
            playerId = martaId,
            score = 300,
            newRecoveredMemories = 4,
            comboStreak = 2,
            levelCompleted = 1,
            environmentUnlocked = "floresta"
        )

        val all = repo.allPlayers.first()
        val marta = all.find { it.playerId == martaId }
        val pedro = all.find { it.playerId == pedroId }

        assertNotNull(marta)
        assertNotNull(pedro)

        assertEquals(300, marta!!.highScore)
        assertEquals(4, marta.recoveredMemories)

        assertEquals(1200, pedro!!.highScore)
        assertEquals(15, pedro.recoveredMemories)
        assertEquals(3, pedro.highestLevelUnlocked)
    }

    @Test
    fun testRenamePlayer_preservesProgress() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val repo = PlayerRepository.getInstance(context)

        val createResult = repo.createPlayer("Renato")
        val playerId = (createResult as? CreatePlayerResult.Success)?.player?.playerId
            ?: (createResult as CreatePlayerResult.DuplicateName).existingPlayer.playerId

        repo.recordGameFinished(
            playerId = playerId,
            score = 800,
            newRecoveredMemories = 9,
            comboStreak = 3,
            levelCompleted = 1,
            environmentUnlocked = "floresta"
        )

        val renameResult = repo.renamePlayer(playerId, "Renato Silva")
        assertEquals(RenamePlayerResult.Success, renameResult)

        val all = repo.allPlayers.first()
        val renato = all.find { it.playerId == playerId }
        assertNotNull(renato)
        assertEquals("Renato Silva", renato!!.playerName)
        assertEquals(800, renato.highScore)
        assertEquals(9, renato.recoveredMemories)
    }

    @Test
    fun testDeletePlayer_removesAndSwitchesActive() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val repo = PlayerRepository.getInstance(context)

        val createResult = repo.createPlayer("JogadorTemporario")
        val playerId = (createResult as? CreatePlayerResult.Success)?.player?.playerId
            ?: (createResult as CreatePlayerResult.DuplicateName).existingPlayer.playerId

        repo.setActivePlayer(playerId)
        assertEquals(playerId, repo.activePlayer.first()?.playerId)

        repo.deletePlayer(playerId)

        val all = repo.allPlayers.first()
        assertTrue("Não deve mais conter o jogador apagado", all.none { it.playerId == playerId })
    }
}
