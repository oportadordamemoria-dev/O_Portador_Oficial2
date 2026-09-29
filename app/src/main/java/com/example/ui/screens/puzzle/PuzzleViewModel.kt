package com.example.ui.screens.puzzle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.puzzle.PuzzleDifficulty
import com.example.data.puzzle.PuzzleImage
import com.example.data.puzzle.PuzzleImageCatalog
import com.example.data.puzzle.PuzzlePiece
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Fases de Navegação do Puzzle da Memória.
 */
enum class PuzzlePhase {
    HOME,
    DIFFICULTY_SELECTION,
    IMAGE_SELECTION,
    PREVIEW,
    PLAYING,
    SOLVED
}

/**
 * Estado da Partida e da Interface do Puzzle da Memória.
 * Não utiliza nenhum banco de dados ou persistência entre sessões.
 */
data class PuzzleUiState(
    val phase: PuzzlePhase = PuzzlePhase.HOME,
    val difficulty: PuzzleDifficulty = PuzzleDifficulty.FACIL,
    val selectedImage: PuzzleImage? = null,
    val imageCandidates: List<PuzzleImage> = emptyList(),
    val pieces: List<PuzzlePiece> = emptyList(),
    val selectedPieceIndex: Int? = null,
    val recentlyCorrectPieceId: Int? = null,
    val moveCount: Int = 0,
    val elapsedSeconds: Int = 0,
    val isPeekingImage: Boolean = false,
    val previewProgress: Float = 0f
) {
    val isAllCorrect: Boolean get() = pieces.isNotEmpty() && pieces.all { it.isCorrect }
    val formattedTime: String
        get() {
            val minutes = elapsedSeconds / 60
            val seconds = elapsedSeconds % 60
            return "%02d:%02d".format(minutes, seconds)
        }
}

class PuzzleViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PuzzleUiState())
    val uiState: StateFlow<PuzzleUiState> = _uiState.asStateFlow()

    private var timerJob: Job? = null
    private var previewJob: Job? = null
    private var glowJob: Job? = null

    init {
        // Inicializa com 3 imagens sorteadas caso vá para a seleção
        _uiState.update { it.copy(imageCandidates = PuzzleImageCatalog.getRandomTrio()) }
    }

    /**
     * Inicia o fluxo: vai da Home para a Escolha de Dificuldade
     */
    fun startPuzzleFlow() {
        _uiState.update { it.copy(phase = PuzzlePhase.DIFFICULTY_SELECTION) }
    }

    /**
     * Seleciona a dificuldade e sorteia 3 opções de imagens diferentes
     */
    fun selectDifficulty(difficulty: PuzzleDifficulty) {
        val candidates = PuzzleImageCatalog.getRandomTrio()
        _uiState.update {
            it.copy(
                difficulty = difficulty,
                imageCandidates = candidates,
                phase = PuzzlePhase.IMAGE_SELECTION
            )
        }
    }

    /**
     * Sorteia outras 3 imagens diferentes sem mudar a dificuldade
     */
    fun rerollImages() {
        val current = _uiState.value.selectedImage?.id
        val newCandidates = PuzzleImageCatalog.getRandomTrio(excludeImageId = current)
        _uiState.update { it.copy(imageCandidates = newCandidates) }
    }

    /**
     * Jogador escolhe uma das 3 imagens:
     * Inicia pré-visualização de 3 segundos e embaralha as peças.
     */
    fun selectImage(image: PuzzleImage) {
        val difficulty = _uiState.value.difficulty
        val shuffledPieces = generateScrambledPieces(difficulty)

        _uiState.update {
            it.copy(
                selectedImage = image,
                pieces = shuffledPieces,
                selectedPieceIndex = null,
                recentlyCorrectPieceId = null,
                moveCount = 0,
                elapsedSeconds = 0,
                isPeekingImage = false,
                previewProgress = 0f,
                phase = PuzzlePhase.PREVIEW
            )
        }

        startPreviewAnimation()
    }

    /**
     * Animação da pré-visualização:
     * Mostra a imagem completa por ~3 segundos, depois inicia o jogo.
     */
    private fun startPreviewAnimation() {
        previewJob?.cancel()
        previewJob = viewModelScope.launch {
            val totalSteps = 30
            for (step in 1..totalSteps) {
                delay(100)
                _uiState.update { it.copy(previewProgress = step.toFloat() / totalSteps) }
            }
            startGameplay()
        }
    }

    /**
     * Pula a prévia ou conclui o tempo e inicia o jogo
     */
    fun skipPreview() {
        previewJob?.cancel()
        startGameplay()
    }

    private fun startGameplay() {
        _uiState.update {
            it.copy(
                phase = PuzzlePhase.PLAYING,
                previewProgress = 1f
            )
        }
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                _uiState.update { it.copy(elapsedSeconds = it.elapsedSeconds + 1) }
            }
        }
    }

    private fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    /**
     * Interação com uma peça do tabuleiro:
     * Toque na Peça A seleciona.
     * Toque na Peça B troca com a Peça A.
     */
    fun onPieceClicked(clickedIndex: Int) {
        val state = _uiState.value
        if (state.phase != PuzzlePhase.PLAYING) return

        val selected = state.selectedPieceIndex
        if (selected == null) {
            // Seleciona a primeira peça
            _uiState.update { it.copy(selectedPieceIndex = clickedIndex) }
        } else if (selected == clickedIndex) {
            // Clicou na mesma peça: desmarca seleção
            _uiState.update { it.copy(selectedPieceIndex = null) }
        } else {
            // Troca a posição das duas peças
            swapPieces(selected, clickedIndex)
        }
    }

    /**
     * Realiza a troca das peças entre dois índices
     */
    fun swapPieces(indexA: Int, indexB: Int) {
        val currentPieces = _uiState.value.pieces.toMutableList()
        if (indexA !in currentPieces.indices || indexB !in currentPieces.indices) return

        val pieceA = currentPieces[indexA]
        val pieceB = currentPieces[indexB]

        // Troca as posições atuais
        val newPieceA = pieceA.copy(
            currentRow = pieceB.currentRow,
            currentCol = pieceB.currentCol
        )
        val newPieceB = pieceB.copy(
            currentRow = pieceA.currentRow,
            currentCol = pieceA.currentCol
        )

        currentPieces[indexA] = newPieceB
        currentPieces[indexB] = newPieceA

        val newMoveCount = _uiState.value.moveCount + 1

        // Verifica se alguma peça recém-trocada encaixou no local correto
        val justCorrectPieceId = when {
            newPieceA.isCorrect -> newPieceA.id
            newPieceB.isCorrect -> newPieceB.id
            else -> null
        }

        // Verifica se todas as peças estão nas posições corretas
        val isSolved = currentPieces.all { it.isCorrect }

        _uiState.update {
            it.copy(
                pieces = currentPieces,
                selectedPieceIndex = null,
                moveCount = newMoveCount,
                recentlyCorrectPieceId = justCorrectPieceId,
                phase = if (isSolved) PuzzlePhase.SOLVED else PuzzlePhase.PLAYING
            )
        }

        if (justCorrectPieceId != null) {
            triggerTemporaryGlow(justCorrectPieceId)
        }

        if (isSolved) {
            stopTimer()
        }
    }

    private fun triggerTemporaryGlow(pieceId: Int) {
        glowJob?.cancel()
        glowJob = viewModelScope.launch {
            delay(500)
            _uiState.update {
                if (it.recentlyCorrectPieceId == pieceId) {
                    it.copy(recentlyCorrectPieceId = null)
                } else it
            }
        }
    }

    /**
     * Controla o recurso "VER IMAGEM" (ajuda visual)
     */
    fun setPeekingImage(isPeeking: Boolean) {
        _uiState.update { it.copy(isPeekingImage = isPeeking) }
    }

    /**
     * JOGAR NOVAMENTE:
     * Utiliza a MESMA imagem e MESMA dificuldade, gerando novo embaralhamento.
     */
    fun playAgainSameImage() {
        val state = _uiState.value
        val image = state.selectedImage ?: return
        val difficulty = state.difficulty
        val shuffledPieces = generateScrambledPieces(difficulty)

        _uiState.update {
            it.copy(
                pieces = shuffledPieces,
                selectedPieceIndex = null,
                recentlyCorrectPieceId = null,
                moveCount = 0,
                elapsedSeconds = 0,
                isPeekingImage = false,
                previewProgress = 0f,
                phase = PuzzlePhase.PREVIEW
            )
        }

        startPreviewAnimation()
    }

    /**
     * OUTRO PUZZLE:
     * Mantém a dificuldade atualmente escolhida e sorteia 3 novas imagens.
     */
    fun playAnotherPuzzle() {
        val currentImgId = _uiState.value.selectedImage?.id
        val candidates = PuzzleImageCatalog.getRandomTrio(excludeImageId = currentImgId)
        _uiState.update {
            it.copy(
                imageCandidates = candidates,
                phase = PuzzlePhase.IMAGE_SELECTION,
                selectedPieceIndex = null,
                recentlyCorrectPieceId = null,
                isPeekingImage = false
            )
        }
    }

    /**
     * Retorna à seleção de dificuldade (Fácil, Médio, Difícil, Mestre)
     */
    fun openDifficultySelection() {
        _uiState.update {
            it.copy(
                phase = PuzzlePhase.DIFFICULTY_SELECTION,
                selectedPieceIndex = null,
                isPeekingImage = false
            )
        }
    }

    /**
     * Volta para a tela inicial do Puzzle da Memória
     */
    fun goBackHome() {
        stopTimer()
        previewJob?.cancel()
        _uiState.update {
            it.copy(
                phase = PuzzlePhase.HOME,
                selectedPieceIndex = null,
                isPeekingImage = false
            )
        }
    }

    /**
     * Embaralhamento robusto e não trivial:
     * Garante que o puzzle nunca comece resolvido e que haja dispersão expressiva das peças.
     */
    private fun generateScrambledPieces(difficulty: PuzzleDifficulty): List<PuzzlePiece> {
        val gridSize = difficulty.gridSize
        val totalPieces = gridSize * gridSize

        // Gera as peças com suas posições originais
        val originalPieces = mutableListOf<PuzzlePiece>()
        var idCounter = 0
        for (row in 0 until gridSize) {
            for (col in 0 until gridSize) {
                originalPieces.add(
                    PuzzlePiece(
                        id = idCounter++,
                        originalRow = row,
                        originalCol = col,
                        currentRow = row,
                        currentCol = col
                    )
                )
            }
        }

        // Gera posições embaralhadas garantindo dispersão inicial
        // Máximo tolerado de peças no lugar certo inicialmente: 1 no Fácil (3x3), 2 nos demais
        val maxInitialCorrect = if (gridSize <= 3) 1 else 2
        var shuffledIndices: List<Int>

        do {
            shuffledIndices = (0 until totalPieces).shuffled()
            var correctCount = 0
            for (i in 0 until totalPieces) {
                if (shuffledIndices[i] == i) {
                    correctCount++
                }
            }
        } while (correctCount > maxInitialCorrect)

        // Atribui as posições embaralhadas
        val result = mutableListOf<PuzzlePiece>()
        for (i in 0 until totalPieces) {
            val targetSlotIndex = shuffledIndices[i]
            val slotRow = targetSlotIndex / gridSize
            val slotCol = targetSlotIndex % gridSize

            val origPiece = originalPieces[i]
            result.add(
                origPiece.copy(
                    currentRow = slotRow,
                    currentCol = slotCol
                )
            )
        }

        // Ordena pela posição no tabuleiro (linha e coluna atual) para facilitar renderização em grid
        return result.sortedBy { it.currentRow * gridSize + it.currentCol }
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
        previewJob?.cancel()
        glowJob?.cancel()
    }
}
