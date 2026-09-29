package com.example

import android.app.Application
import androidx.test.core.app.ApplicationProvider
import com.example.data.game.CardCategory
import com.example.data.game.CardType
import com.example.data.game.GameContentData
import com.example.data.game.GameMode
import com.example.data.game.MemoryCardRepository
import com.example.data.game.db.MemoryGameDatabase
import com.example.data.game.db.MemoryGameRepository
import com.example.ui.screens.game.GamePhase
import com.example.ui.screens.game.MemoryGameViewModel
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
class MemoryGameTest {

    @Test
    fun testMemoryCardRepository_containsCanonicalElements() {
        val cards = MemoryCardRepository.allCards
        assertTrue("Deveria conter mais de 15 cartas canônicas", cards.size >= 15)

        // Personagens canônicos
        assertNotNull(cards.find { it.id == "char_jonatas" })
        assertNotNull(cards.find { it.id == "char_rafael" })
        assertNotNull(cards.find { it.id == "char_miriam" })
        assertNotNull(cards.find { it.id == "char_daniel" })
        assertNotNull(cards.find { it.id == "char_menina_cancao" })
        assertNotNull(cards.find { it.id == "char_rei_esquecimento" })

        // Lugares canônicos
        assertNotNull(cards.find { it.id == "loc_blumenau" })
        assertNotNull(cards.find { it.id == "loc_floresta" })
        assertNotNull(cards.find { it.id == "loc_jardim" })
        assertNotNull(cards.find { it.id == "loc_arvore_nomes" })

        // Valores
        assertNotNull(cards.find { it.id == "val_memoria" })
        assertNotNull(cards.find { it.id == "val_esperanca" })
        assertNotNull(cards.find { it.id == "val_compaixao" })
    }

    @Test
    fun testRelationPairs_canonicalIntegrity() {
        val relationPairs = MemoryCardRepository.relationPairs
        assertTrue("Deveria conter pares relacionais", relationPairs.isNotEmpty())

        val miriamPair = relationPairs.firstOrNull { (a, b) ->
            (a.id == "char_miriam" && b.id == "loc_jardim") || (b.id == "char_miriam" && a.id == "loc_jardim")
        }
        assertNotNull("Miriam deve estar associada ao Jardim das Crianças", miriamPair)

        val jonatasPair = relationPairs.firstOrNull { (a, b) ->
            (a.id == "char_jonatas" && b.id == "loc_arvore_nomes") || (b.id == "char_jonatas" && a.id == "loc_arvore_nomes")
        }
        assertNotNull("Jônatas deve estar associado à Árvore dos Nomes", jonatasPair)
    }

    @Test
    fun testLevelConfigurations_progressionRanks() {
        val levels = GameContentData.levels
        assertEquals(5, levels.size)

        assertEquals("Lembrança", levels[0].rankTitle)
        assertEquals(GameMode.PAIRS, levels[0].gameMode)

        assertEquals("Guardião", levels[1].rankTitle)
        assertEquals(GameMode.RELATIONS, levels[1].gameMode)

        assertEquals("Protetor", levels[2].rankTitle)
        assertEquals(GameMode.SEQUENCE, levels[2].gameMode)

        assertEquals("Guardião da Memória", levels[3].rankTitle)
        assertEquals(GameMode.PROGRESSIVE_CHAIN, levels[3].gameMode)

        assertEquals("Portador da Memória", levels[4].rankTitle)
        assertEquals(GameMode.MISSING_CARD, levels[4].gameMode)
    }

    @Test
    fun testEnvironmentJourney_order() {
        val envs = GameContentData.environments
        assertEquals(6, envs.size)
        assertEquals("Blumenau", envs[0].name)
        assertEquals("Floresta Oblivionis", envs[1].name)
        assertEquals("Jardim das Crianças", envs[2].name)
        assertEquals("Árvore dos Nomes", envs[3].name)
        assertEquals("Vale dos Silenciados", envs[4].name)
        assertEquals("Trono sob as Raízes", envs[5].name)
    }

    @Test
    fun testRoomPersistence_recordingProgress() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val repo = MemoryGameRepository.getInstance(context)

        // Gravar resultado do nível 1
        repo.recordGameFinished(
            score = 750,
            newRecoveredMemories = 8,
            comboStreak = 4,
            levelCompleted = 1,
            environmentUnlocked = "floresta"
        )

        val progress = repo.gameProgress.first()
        assertTrue("Pontuação deve ser gravada", progress.highScore >= 750)
        assertTrue("Memórias devem ser somadas", progress.recoveredMemories >= 8)
        assertTrue("Nível 2 deve ser desbloqueado", progress.highestLevelUnlocked >= 2)
        assertEquals("Guardião", progress.rankTitle)
        assertTrue("Ambiente floresta deve estar desbloqueado", progress.unlockedEnvironments.contains("floresta"))
    }

    @Test
    fun testViewModel_initializationAndLevelStart() {
        val context = ApplicationProvider.getApplicationContext<Application>()
        val viewModel = MemoryGameViewModel(context)

        viewModel.startLevel(1)
        val uiState = viewModel.uiState.value

        assertEquals(1, uiState.currentLevel.levelNumber)
        assertEquals(1, uiState.currentRound)
        assertTrue("Deveria gerar cartas para o nível 1", uiState.slots.isNotEmpty())
        assertTrue("Deveria iniciar em fase de prévia ou jogo", uiState.phase is GamePhase.Previewing || uiState.phase is GamePhase.Playing)
    }
}
