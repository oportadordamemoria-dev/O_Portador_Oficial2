package com.example.ui.screens.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.game.CardCategory
import com.example.data.game.GameContentData
import com.example.data.game.GameLevelConfig
import com.example.data.game.GameMode
import com.example.data.game.MemoryCard
import com.example.data.game.MemoryCardRepository
import com.example.data.game.db.CreatePlayerResult
import com.example.data.game.db.GameProgressData
import com.example.data.game.db.MemoryGameRepository
import com.example.data.game.db.PlayerProfile
import com.example.data.game.db.PlayerRepository
import com.example.data.game.db.RenamePlayerResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class GameUiState(
    val currentLevel: GameLevelConfig = GameContentData.getLevel(1),
    val currentRound: Int = 1,
    val totalRounds: Int = 3,
    val slots: List<CardSlot> = emptyList(),
    val phase: GamePhase = GamePhase.Playing,
    val currentScore: Int = 0,
    val roundMemoriesRecovered: Int = 0,
    val comboStreak: Int = 0,
    val comboMultiplier: Int = 1,
    val feedbackMessage: String = "",
    val activeRelationHint: String = "",
    // Sequence mode data
    val targetSequence: List<MemoryCard> = emptyList(),
    val currentSequenceStep: Int = 0,
    // Missing card mode data
    val originalCardSet: List<MemoryCard> = emptyList(),
    val missingCard: MemoryCard? = null,
    val missingCardChoices: List<MemoryCard> = emptyList(),
    val isMistCovering: Boolean = false
)

class MemoryGameViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MemoryGameRepository.getInstance(application)
    private val playerRepository = PlayerRepository.getInstance(application)

    val progress: StateFlow<GameProgressData> = repository.gameProgress.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = GameProgressData()
    )

    val allPlayers: StateFlow<List<PlayerProfile>> = playerRepository.allPlayers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val activePlayer: StateFlow<PlayerProfile?> = playerRepository.activePlayer.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )

    private val _saveIndicatorVisible = MutableStateFlow(false)
    val saveIndicatorVisible: StateFlow<Boolean> = _saveIndicatorVisible.asStateFlow()

    private var saveIndicatorJob: Job? = null

    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var previewTimerJob: Job? = null
    private var flippedSlots: MutableList<Int> = mutableListOf()
    private var isEvaluating: Boolean = false

    fun startLevel(levelNumber: Int, customMode: GameMode? = null) {
        val levelConfig = GameContentData.getLevel(levelNumber).let {
            if (customMode != null) it.copy(gameMode = customMode) else it
        }

        _uiState.value = GameUiState(
            currentLevel = levelConfig,
            currentRound = 1,
            totalRounds = levelConfig.targetRounds,
            currentScore = 0,
            roundMemoriesRecovered = 0,
            comboStreak = 0,
            comboMultiplier = 1
        )

        loadRound(round = 1)
    }

    fun startInfiniteMode() {
        val config = GameLevelConfig(
            levelNumber = 99,
            title = "Desafio Infinito",
            subtitle = "Resistência Máxima • A névoa nunca descansa",
            rankTitle = "Portador Eterno",
            description = "Preserve o maior número de memórias sem interrupção. A cada acerto consecutivo, o multiplicador de luz aumenta!",
            environmentId = "trono_raizes",
            environmentName = "O Limiar Infinito",
            environmentImageRes = com.example.R.drawable.hero_cover_art,
            gameMode = GameMode.INFINITE,
            previewSeconds = 3,
            targetRounds = 999
        )
        _uiState.value = GameUiState(
            currentLevel = config,
            currentRound = 1,
            totalRounds = 999,
            currentScore = 0,
            roundMemoriesRecovered = 0,
            comboStreak = 0,
            comboMultiplier = 1
        )
        loadRound(round = 1)
    }

    fun startMissingCardMode() {
        startLevel(levelNumber = 5, customMode = GameMode.MISSING_CARD)
    }

    private fun loadRound(round: Int) {
        previewTimerJob?.cancel()
        flippedSlots.clear()
        isEvaluating = false

        val config = _uiState.value.currentLevel
        when (config.gameMode) {
            GameMode.PAIRS, GameMode.INFINITE -> setupPairsRound(round)
            GameMode.RELATIONS -> setupRelationsRound(round)
            GameMode.SEQUENCE -> setupSequenceRound(round)
            GameMode.PROGRESSIVE_CHAIN -> setupProgressiveRound(round)
            GameMode.MISSING_CARD -> setupMissingCardRound(round)
        }
    }

    // ==========================================
    // NÍVEL 1 & INFINITO: PARES VISUAIS
    // ==========================================
    private fun setupPairsRound(round: Int) {
        val pairCount = when (round) {
            1 -> 3 // 6 cartas
            2 -> 4 // 8 cartas
            else -> 5 // 10 cartas
        }

        val selectedUniqueCards = MemoryCardRepository.allCards
            .filter { it.imageRes != null }
            .shuffled()
            .take(pairCount)

        // Criar pares idênticos
        val cardList = mutableListOf<MemoryCard>()
        selectedUniqueCards.forEach { card ->
            cardList.add(card)
            cardList.add(card)
        }
        cardList.shuffle()

        val slots = cardList.mapIndexed { index, card ->
            CardSlot(
                slotId = index,
                card = card,
                isFaceUp = true, // Exibe abertas na prévia
                isMatched = false
            )
        }

        _uiState.value = _uiState.value.copy(
            currentRound = round,
            slots = slots,
            phase = GamePhase.Previewing(secondsRemaining = _uiState.value.currentLevel.previewSeconds),
            feedbackMessage = "Observe as memórias..."
        )

        startPreviewCountdown(_uiState.value.currentLevel.previewSeconds)
    }

    // ==========================================
    // NÍVEL 2: RELAÇÕES E ASSOCIAÇÕES
    // ==========================================
    private fun setupRelationsRound(round: Int) {
        val pairCount = if (round == 1) 3 else 4
        val availableRelationPairs = MemoryCardRepository.relationPairs.shuffled().take(pairCount)

        val cardList = mutableListOf<MemoryCard>()
        val relationHints = StringBuilder("Relações desta rodada:\n")
        availableRelationPairs.forEach { (a, b) ->
            cardList.add(a)
            cardList.add(b)
            relationHints.append("• ${a.name} ↔ ${b.name}\n")
        }
        cardList.shuffle()

        val slots = cardList.mapIndexed { index, card ->
            CardSlot(
                slotId = index,
                card = card,
                isFaceUp = true,
                isMatched = false
            )
        }

        _uiState.value = _uiState.value.copy(
            currentRound = round,
            slots = slots,
            phase = GamePhase.Previewing(secondsRemaining = 5),
            activeRelationHint = relationHints.toString().trim(),
            feedbackMessage = "Memorize as relações canônicas..."
        )

        startPreviewCountdown(5)
    }

    // ==========================================
    // NÍVEL 3: O CAMINHO DA MEMÓRIA (SEQUÊNCIA)
    // ==========================================
    private fun setupSequenceRound(round: Int) {
        val seqLength = 3 + round // 4, 5, 6 cartas
        val sequenceCards = MemoryCardRepository.allCards
            .filter { it.imageRes != null }
            .shuffled()
            .take(seqLength)

        val shuffledForBoard = sequenceCards.shuffled()

        val slots = shuffledForBoard.mapIndexed { index, card ->
            CardSlot(
                slotId = index,
                card = card,
                isFaceUp = false,
                isMatched = false
            )
        }

        _uiState.value = _uiState.value.copy(
            currentRound = round,
            slots = slots,
            targetSequence = sequenceCards,
            currentSequenceStep = 0,
            phase = GamePhase.Previewing(secondsRemaining = 4),
            feedbackMessage = "Memorize a sequência do caminho..."
        )

        startPreviewCountdown(4)
    }

    // ==========================================
    // NÍVEL 4: ANTES QUE SEJA ESQUECIDO (PROGRESSIVO)
    // ==========================================
    private fun setupProgressiveRound(round: Int) {
        val chainLength = 2 + round // 3, 4, 5, 6 cartas
        val sequenceCards = MemoryCardRepository.allCards
            .filter { it.imageRes != null }
            .shuffled()
            .take(chainLength)

        val slots = sequenceCards.shuffled().mapIndexed { index, card ->
            CardSlot(
                slotId = index,
                card = card,
                isFaceUp = false,
                isMatched = false
            )
        }

        _uiState.value = _uiState.value.copy(
            currentRound = round,
            slots = slots,
            targetSequence = sequenceCards,
            currentSequenceStep = 0,
            phase = GamePhase.Previewing(secondsRemaining = 4),
            feedbackMessage = "Mais uma memória foi acrescentada..."
        )

        startPreviewCountdown(4)
    }

    // ==========================================
    // NÍVEL 5 & ESPECIAL: QUAL MEMÓRIA DESAPARECEU?
    // ==========================================
    private fun setupMissingCardRound(round: Int) {
        val count = 5 + round // 6, 7, 8 cartas
        val cards = MemoryCardRepository.allCards
            .filter { it.imageRes != null }
            .shuffled()
            .take(count)

        val missing = cards.random()
        val remaining = cards.filter { it.id != missing.id }

        // Opções de escolha: a correta + 3 distrações
        val distractors = MemoryCardRepository.allCards
            .filter { it.id != missing.id && it !in remaining }
            .shuffled()
            .take(3)
        val choices = (distractors + missing).shuffled()

        val initialSlots = cards.mapIndexed { index, card ->
            CardSlot(
                slotId = index,
                card = card,
                isFaceUp = true,
                isMatched = false
            )
        }

        _uiState.value = _uiState.value.copy(
            currentRound = round,
            slots = initialSlots,
            originalCardSet = cards,
            missingCard = missing,
            missingCardChoices = choices,
            isMistCovering = false,
            phase = GamePhase.Previewing(secondsRemaining = 5),
            feedbackMessage = "Observe todas as memórias antes que a névoa chegue..."
        )

        previewTimerJob = viewModelScope.launch {
            for (sec in 5 downTo 1) {
                _uiState.value = _uiState.value.copy(
                    phase = GamePhase.Previewing(secondsRemaining = sec)
                )
                delay(1000)
            }

            // A névoa do esquecimento cobre o tabuleiro
            _uiState.value = _uiState.value.copy(
                isMistCovering = true,
                feedbackMessage = "A névoa está levando uma memória..."
            )
            delay(1200)

            // Revela apenas as restantes (uma desapareceu)
            val remainingSlots = remaining.mapIndexed { index, card ->
                CardSlot(
                    slotId = index,
                    card = card,
                    isFaceUp = true,
                    isMatched = false
                )
            }

            _uiState.value = _uiState.value.copy(
                slots = remainingSlots,
                isMistCovering = false,
                phase = GamePhase.Playing,
                feedbackMessage = "Qual memória desapareceu?"
            )
        }
    }

    private fun startPreviewCountdown(seconds: Int) {
        previewTimerJob = viewModelScope.launch {
            for (sec in seconds downTo 1) {
                _uiState.value = _uiState.value.copy(
                    phase = GamePhase.Previewing(secondsRemaining = sec)
                )
                delay(1000)
            }

            // Vira todas as cartas para baixo e inicia o jogo
            val hiddenSlots = _uiState.value.slots.map { it.copy(isFaceUp = false) }
            _uiState.value = _uiState.value.copy(
                slots = hiddenSlots,
                phase = GamePhase.Playing,
                feedbackMessage = "Encontre as memórias!"
            )
        }
    }

    // ==========================================
    // INTERAÇÕES DO JOGADOR
    // ==========================================

    fun onCardTapped(slotId: Int) {
        val state = _uiState.value
        if (state.phase !is GamePhase.Playing || isEvaluating) return

        val slot = state.slots.firstOrNull { it.slotId == slotId } ?: return
        if (slot.isFaceUp || slot.isMatched) return

        when (state.currentLevel.gameMode) {
            GameMode.PAIRS, GameMode.RELATIONS, GameMode.INFINITE -> handlePairCardTap(slotId)
            GameMode.SEQUENCE, GameMode.PROGRESSIVE_CHAIN -> handleSequenceCardTap(slotId)
            GameMode.MISSING_CARD -> { /* Respond via onMissingChoiceSelected */ }
        }
    }

    private fun handlePairCardTap(slotId: Int) {
        val state = _uiState.value
        val currentSlots = state.slots.toMutableList()
        val index = currentSlots.indexOfFirst { it.slotId == slotId }
        if (index == -1) return

        // Virar a carta
        currentSlots[index] = currentSlots[index].copy(isFaceUp = true)
        flippedSlots.add(slotId)
        _uiState.value = state.copy(slots = currentSlots)

        if (flippedSlots.size == 2) {
            evaluatePairMatch()
        }
    }

    private fun evaluatePairMatch() {
        isEvaluating = true
        val state = _uiState.value
        val firstSlotId = flippedSlots[0]
        val secondSlotId = flippedSlots[1]

        val firstSlot = state.slots.first { it.slotId == firstSlotId }
        val secondSlot = state.slots.first { it.slotId == secondSlotId }

        val isMatch = if (state.currentLevel.gameMode == GameMode.RELATIONS) {
            // Verificar se são o mesmo par canônico
            val pairFound = MemoryCardRepository.relationPairs.any { (a, b) ->
                (firstSlot.card.id == a.id && secondSlot.card.id == b.id) ||
                        (firstSlot.card.id == b.id && secondSlot.card.id == a.id)
            }
            pairFound
        } else {
            // Pares idênticos
            firstSlot.card.id == secondSlot.card.id
        }

        viewModelScope.launch {
            if (isMatch) {
                // ACERTO: Brilho dourado e pontuação multiplicada
                val newStreak = state.comboStreak + 1
                val multiplier = minOf(5, 1 + (newStreak / 2))
                val earnedScore = 100 * multiplier

                val updatedSlots = state.slots.map { slot ->
                    if (slot.slotId == firstSlotId || slot.slotId == secondSlotId) {
                        slot.copy(isMatched = true, isGlowActive = true)
                    } else slot
                }

                val praise = GameEncouragement.randomSuccess()
                _uiState.value = state.copy(
                    slots = updatedSlots,
                    currentScore = state.currentScore + earnedScore,
                    roundMemoriesRecovered = state.roundMemoriesRecovered + 1,
                    comboStreak = newStreak,
                    comboMultiplier = multiplier,
                    feedbackMessage = "$praise (+${earnedScore} pts)"
                )

                delay(700)
                // Desativar efeito de brilho pulsante, mantendo isMatched
                val finalSlots = _uiState.value.slots.map { it.copy(isGlowActive = false) }
                _uiState.value = _uiState.value.copy(slots = finalSlots)

                flippedSlots.clear()
                isEvaluating = false

                // Verificar se todas as cartas da rodada foram encontradas
                if (finalSlots.all { it.isMatched }) {
                    onRoundCompleted()
                }
            } else {
                // ERRO: Cartas permanecem nítidas e visíveis para memorização por mais tempo
                val mistSlots = state.slots.map { slot ->
                    if (slot.slotId == firstSlotId || slot.slotId == secondSlotId) {
                        slot.copy(isMistActive = true)
                    } else slot
                }

                val gentleMsg = GameEncouragement.randomMistake()
                _uiState.value = state.copy(
                    slots = mistSlots,
                    comboStreak = 0,
                    comboMultiplier = 1,
                    feedbackMessage = gentleMsg
                )

                // Aumentado em 1 segundo (de 1200ms para 2200ms) para memorização nítida
                delay(2200)

                // Virar de volta para baixo
                val resetSlots = _uiState.value.slots.map { slot ->
                    if (slot.slotId == firstSlotId || slot.slotId == secondSlotId) {
                        slot.copy(isFaceUp = false, isMistActive = false)
                    } else slot
                }

                _uiState.value = _uiState.value.copy(slots = resetSlots)
                flippedSlots.clear()
                isEvaluating = false
            }
        }
    }

    private fun handleSequenceCardTap(slotId: Int) {
        val state = _uiState.value
        val currentSlots = state.slots.toMutableList()
        val index = currentSlots.indexOfFirst { it.slotId == slotId }
        if (index == -1) return

        val tappedCard = currentSlots[index].card
        val expectedCard = state.targetSequence.getOrNull(state.currentSequenceStep)

        viewModelScope.launch {
            if (expectedCard != null && tappedCard.id == expectedCard.id) {
                // Acertou o passo da sequência
                currentSlots[index] = currentSlots[index].copy(
                    isFaceUp = true,
                    isMatched = true,
                    isGlowActive = true
                )
                val nextStep = state.currentSequenceStep + 1
                val earnedScore = 80 * state.comboMultiplier

                _uiState.value = state.copy(
                    slots = currentSlots,
                    currentSequenceStep = nextStep,
                    currentScore = state.currentScore + earnedScore,
                    roundMemoriesRecovered = state.roundMemoriesRecovered + 1,
                    feedbackMessage = "Passo $nextStep de ${state.targetSequence.size}!"
                )

                delay(400)
                currentSlots[index] = currentSlots[index].copy(isGlowActive = false)
                _uiState.value = _uiState.value.copy(slots = currentSlots)

                if (nextStep >= state.targetSequence.size) {
                    // Completou a sequência toda!
                    onRoundCompleted()
                }
            } else {
                // Errou a sequência: vira momentaneamente nítida para memorização e reinicia a tentativa
                currentSlots[index] = currentSlots[index].copy(isFaceUp = true, isMistActive = true)
                _uiState.value = state.copy(
                    slots = currentSlots,
                    comboStreak = 0,
                    comboMultiplier = 1,
                    feedbackMessage = GameEncouragement.randomMistake()
                )

                // Aumentado em 1 segundo (de 1100ms para 2100ms) para memorização nítida
                delay(2100)

                // Esconde as que não foram preservadas com sucesso e reinicia o passo
                val resetSlots = state.slots.map { it.copy(isFaceUp = false, isMistActive = false, isMatched = false) }
                _uiState.value = _uiState.value.copy(
                    slots = resetSlots,
                    currentSequenceStep = 0
                )
            }
        }
    }

    fun onMissingChoiceSelected(chosenCard: MemoryCard) {
        val state = _uiState.value
        if (state.phase !is GamePhase.Playing) return

        viewModelScope.launch {
            if (chosenCard.id == state.missingCard?.id) {
                // Acertou a carta que desapareceu!
                val earnedScore = 200 * state.comboMultiplier
                val newStreak = state.comboStreak + 1
                val newMultiplier = minOf(5, 1 + (newStreak / 2))

                _uiState.value = state.copy(
                    currentScore = state.currentScore + earnedScore,
                    roundMemoriesRecovered = state.roundMemoriesRecovered + 2,
                    comboStreak = newStreak,
                    comboMultiplier = newMultiplier,
                    feedbackMessage = "Você resgatou a memória de ${chosenCard.name}! A luz retornou."
                )

                delay(1200)
                onRoundCompleted()
            } else {
                // Escolha incorreta
                _uiState.value = state.copy(
                    comboStreak = 0,
                    comboMultiplier = 1,
                    feedbackMessage = "Essa memória continuou presente. Tente outra!"
                )
            }
        }
    }

    private suspend fun onRoundCompleted() {
        val state = _uiState.value
        val isFinalRound = state.currentRound >= state.totalRounds

        if (!isFinalRound) {
            // Avança para a próxima rodada do mesmo nível
            _uiState.value = state.copy(
                feedbackMessage = "Rodada ${state.currentRound} concluída! Preparando próxima memória..."
            )
            delay(1200)
            loadRound(round = state.currentRound + 1)
        } else {
            // Nível totalmente concluído!
            val stars = when {
                state.currentScore >= 600 -> 3
                state.currentScore >= 300 -> 2
                else -> 1
            }

            val nextLevel = if (state.currentLevel.levelNumber < 5) state.currentLevel.levelNumber + 1 else null

            // Salvar no Room Database
            repository.recordGameFinished(
                score = state.currentScore,
                newRecoveredMemories = state.roundMemoriesRecovered,
                comboStreak = state.comboStreak,
                levelCompleted = state.currentLevel.levelNumber,
                environmentUnlocked = state.currentLevel.environmentId
            )

            if (state.currentLevel.gameMode == GameMode.INFINITE) {
                repository.recordInfiniteScore(state.roundMemoriesRecovered)
            }

            // Notifica salvamento automático discreto (Requisito 12)
            triggerAutosaveIndicator()

            _uiState.value = state.copy(
                phase = GamePhase.RoundFinished(
                    scoreEarned = state.currentScore,
                    memoriesRecovered = state.roundMemoriesRecovered,
                    comboStreak = state.comboStreak,
                    stars = stars,
                    isLevelComplete = true,
                    nextLevelNumber = nextLevel
                ),
                feedbackMessage = "Uma nova memória foi preservada."
            )
        }
    }

    fun triggerAutosaveIndicator() {
        saveIndicatorJob?.cancel()
        saveIndicatorJob = viewModelScope.launch {
            _saveIndicatorVisible.value = true
            delay(2200)
            _saveIndicatorVisible.value = false
        }
    }

    fun createPlayer(rawName: String, onResult: (CreatePlayerResult) -> Unit) {
        viewModelScope.launch {
            val result = playerRepository.createPlayer(rawName)
            if (result is CreatePlayerResult.Success) {
                triggerAutosaveIndicator()
            }
            onResult(result)
        }
    }

    fun setActivePlayer(playerId: String) {
        viewModelScope.launch {
            playerRepository.setActivePlayer(playerId)
        }
    }

    fun renamePlayer(playerId: String, newName: String, onResult: (RenamePlayerResult) -> Unit) {
        viewModelScope.launch {
            val result = playerRepository.renamePlayer(playerId, newName)
            if (result is RenamePlayerResult.Success) {
                triggerAutosaveIndicator()
            }
            onResult(result)
        }
    }

    fun deletePlayer(playerId: String) {
        viewModelScope.launch {
            playerRepository.deletePlayer(playerId)
        }
    }

    fun restartCurrentLevel() {
        startLevel(_uiState.value.currentLevel.levelNumber)
    }
}
