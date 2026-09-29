package com.example

import com.example.data.puzzle.PuzzleDifficulty
import com.example.data.puzzle.PuzzleImageCatalog
import com.example.ui.screens.puzzle.PuzzlePhase
import com.example.ui.screens.puzzle.PuzzleViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PuzzleGameTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testDifficultiesAndGridSizes() {
        assertEquals(3, PuzzleDifficulty.FACIL.gridSize)
        assertEquals(9, PuzzleDifficulty.FACIL.piecesCount)

        assertEquals(4, PuzzleDifficulty.MEDIO.gridSize)
        assertEquals(16, PuzzleDifficulty.MEDIO.piecesCount)

        assertEquals(5, PuzzleDifficulty.DIFICIL.gridSize)
        assertEquals(25, PuzzleDifficulty.DIFICIL.piecesCount)

        assertEquals(6, PuzzleDifficulty.MESTRE.gridSize)
        assertEquals(36, PuzzleDifficulty.MESTRE.piecesCount)
    }

    @Test
    fun testImageCatalogRandomTrioUniqueness() {
        assertTrue("O catálogo deve conter ao menos 15 imagens oficiais", PuzzleImageCatalog.ALL_IMAGES.size >= 15)

        val trio = PuzzleImageCatalog.getRandomTrio()
        assertEquals(3, trio.size)

        val uniqueIds = trio.map { it.id }.toSet()
        assertEquals("As 3 imagens sorteadas devem ser distintas entre si", 3, uniqueIds.size)
    }

    @Test
    fun testPuzzleViewModelFlow() {
        val viewModel = PuzzleViewModel()
        assertEquals(PuzzlePhase.HOME, viewModel.uiState.value.phase)

        viewModel.startPuzzleFlow()
        assertEquals(PuzzlePhase.DIFFICULTY_SELECTION, viewModel.uiState.value.phase)

        viewModel.selectDifficulty(PuzzleDifficulty.FACIL)
        assertEquals(PuzzlePhase.IMAGE_SELECTION, viewModel.uiState.value.phase)
        assertEquals(PuzzleDifficulty.FACIL, viewModel.uiState.value.difficulty)
        assertEquals(3, viewModel.uiState.value.imageCandidates.size)

        val chosenImage = viewModel.uiState.value.imageCandidates.first()
        viewModel.selectImage(chosenImage)
        assertEquals(PuzzlePhase.PREVIEW, viewModel.uiState.value.phase)
        assertEquals(chosenImage, viewModel.uiState.value.selectedImage)
        assertEquals(9, viewModel.uiState.value.pieces.size)

        viewModel.skipPreview()
        assertEquals(PuzzlePhase.PLAYING, viewModel.uiState.value.phase)

        // Teste de seleção e troca
        viewModel.onPieceClicked(0)
        assertEquals(0, viewModel.uiState.value.selectedPieceIndex)

        // Clicar na mesma peça desmarca
        viewModel.onPieceClicked(0)
        assertEquals(null, viewModel.uiState.value.selectedPieceIndex)

        // Seleciona e troca
        viewModel.onPieceClicked(0)
        viewModel.onPieceClicked(1)
        assertEquals(1, viewModel.uiState.value.moveCount)
        assertEquals(null, viewModel.uiState.value.selectedPieceIndex)
    }
}
