package com.example.ui.screens.game

import com.example.data.game.MemoryCard

data class CardSlot(
    val slotId: Int,
    val card: MemoryCard,
    val isFaceUp: Boolean = false,
    val isMatched: Boolean = false,
    val isGlowActive: Boolean = false,
    val isMistActive: Boolean = false,
    val isSelected: Boolean = false
)

sealed interface GamePhase {
    data class Previewing(val secondsRemaining: Int) : GamePhase
    object Playing : GamePhase
    data class SuccessFeedback(val message: String) : GamePhase
    data class ErrorFeedback(val message: String) : GamePhase
    data class RoundFinished(
        val scoreEarned: Int,
        val memoriesRecovered: Int,
        val comboStreak: Int,
        val stars: Int,
        val isLevelComplete: Boolean,
        val nextLevelNumber: Int?
    ) : GamePhase
    object GameOverSummary : GamePhase
}

object GameEncouragement {
    private val successPhrases = listOf(
        "Memória recuperada.",
        "Você se lembrou.",
        "A lembrança permanece.",
        "Uma nova luz se acendeu.",
        "A memória foi preservada."
    )

    private val gentleMistakePhrases = listOf(
        "Observe novamente.",
        "Essa memória ainda pode ser recuperada.",
        "Tente lembrar onde ela estava.",
        "A névoa logo se dissipará.",
        "Respire e observe com atenção."
    )

    fun randomSuccess(): String = successPhrases.random()
    fun randomMistake(): String = gentleMistakePhrases.random()
}
