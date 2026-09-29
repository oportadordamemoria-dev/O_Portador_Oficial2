package com.example.ui.screens.puzzle

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

/**
 * Tela Raiz do Jogo "Puzzle da Memória".
 * Gerencia a máquina de estados independente do jogo:
 * HOME -> ESCOLHA DIFICULDADE -> ESCOLHA IMAGEM -> PRÉVIA -> JOGO -> CONCLUÍDO
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleGameScreen(
    onNavigateBackToApp: () -> Unit,
    viewModel: PuzzleViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var showQuitDialog by remember { mutableStateOf(false) }

    // Interceptador do botão Voltar do sistema Android
    BackHandler {
        when (uiState.phase) {
            PuzzlePhase.HOME -> onNavigateBackToApp()
            PuzzlePhase.DIFFICULTY_SELECTION -> viewModel.goBackHome()
            PuzzlePhase.IMAGE_SELECTION -> viewModel.openDifficultySelection()
            PuzzlePhase.PREVIEW -> viewModel.skipPreview()
            PuzzlePhase.PLAYING -> showQuitDialog = true
            PuzzlePhase.SOLVED -> viewModel.goBackHome()
        }
    }

    // Diálogo de confirmação para sair de uma partida em andamento
    if (showQuitDialog) {
        AlertDialog(
            onDismissRequest = { showQuitDialog = false },
            title = {
                Text(
                    text = "Sair do Quebra-Cabeça?",
                    color = GoldLight,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text(
                    text = "A partida atual não será salva. Deseja retornar ao menu do Puzzle?",
                    color = TextParchment
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showQuitDialog = false
                        viewModel.goBackHome()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = GoldPrimary)
                ) {
                    Text("Sair", color = CanvasDeep, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showQuitDialog = false }) {
                    Text("Continuar Jogando", color = MistLight)
                }
            },
            containerColor = CanvasCard,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.testTag("puzzle_quit_dialog")
        )
    }

    // Overlay visual "VER IMAGEM" (Ajuda visual temporária - Requisito 12)
    AnimatedVisibility(
        visible = uiState.isPeekingImage && uiState.selectedImage != null,
        enter = fadeIn(),
        exit = fadeOut(),
        modifier = Modifier.zIndex(100f)
    ) {
        val img = uiState.selectedImage
        if (img != null) {
            Dialog(onDismissRequest = { viewModel.setPeekingImage(false) }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(BorderStroke(2.dp, GoldLight), RoundedCornerShape(16.dp))
                        .clickable { viewModel.setPeekingImage(false) },
                    colors = CardDefaults.cardColors(containerColor = CanvasDeep),
                    elevation = CardDefaults.cardElevation(defaultElevation = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = img.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldLight,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            IconButton(
                                onClick = { viewModel.setPeekingImage(false) },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Fechar imagem",
                                    tint = GoldLight
                                )
                            }
                        }

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(10.dp)),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Image(
                                painter = painterResource(id = img.imageRes),
                                contentDescription = img.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Text(
                            text = "Toque em qualquer lugar para retornar ao quebra-cabeça",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MistLight,
                                fontStyle = FontStyle.Italic
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }

    // Transição entre as telas/fases do jogo
    when (uiState.phase) {
        PuzzlePhase.HOME -> {
            PuzzleHomeScreen(
                onStartPuzzle = { viewModel.startPuzzleFlow() },
                onBack = onNavigateBackToApp
            )
        }

        PuzzlePhase.DIFFICULTY_SELECTION -> {
            DifficultySelectorScreen(
                onSelectDifficulty = { diff -> viewModel.selectDifficulty(diff) },
                onBack = { viewModel.goBackHome() }
            )
        }

        PuzzlePhase.IMAGE_SELECTION -> {
            PuzzleImageSelectorScreen(
                difficulty = uiState.difficulty,
                images = uiState.imageCandidates,
                onSelectImage = { img -> viewModel.selectImage(img) },
                onRerollImages = { viewModel.rerollImages() },
                onChangeDifficulty = { viewModel.openDifficultySelection() },
                onBack = { viewModel.openDifficultySelection() }
            )
        }

        PuzzlePhase.PREVIEW -> {
            val img = uiState.selectedImage
            if (img != null) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Puzzle da Memória",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = GoldLight,
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = CanvasDeep)
                        )
                    },
                    containerColor = CanvasDeep
                ) { paddingValues ->
                    PuzzlePreviewView(
                        puzzleImage = img,
                        difficulty = uiState.difficulty,
                        previewProgress = uiState.previewProgress,
                        onSkip = { viewModel.skipPreview() },
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }

        PuzzlePhase.PLAYING -> {
            val img = uiState.selectedImage
            if (img != null) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Column {
                                    Text(
                                        text = img.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            color = TextParchment,
                                            fontFamily = FontFamily.Serif,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        ),
                                        maxLines = 1
                                    )
                                    Text(
                                        text = "${uiState.difficulty.title} (${uiState.difficulty.dimensionLabel})",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = MistLight
                                        )
                                    )
                                }
                            },
                            navigationIcon = {
                                IconButton(
                                    onClick = { showQuitDialog = true },
                                    modifier = Modifier.testTag("puzzle_btn_quit_game")
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Voltar",
                                        tint = GoldLight
                                    )
                                }
                            },
                            actions = {
                                // Botão de Ajuda Visual: VER IMAGEM (Requisito 12)
                                Surface(
                                    shape = RoundedCornerShape(10.dp),
                                    color = GoldContainer,
                                    border = BorderStroke(1.dp, GoldPrimary),
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .pointerInput(Unit) {
                                            detectTapGestures(
                                                onPress = {
                                                    viewModel.setPeekingImage(true)
                                                    tryAwaitRelease()
                                                    viewModel.setPeekingImage(false)
                                                },
                                                onTap = {
                                                    viewModel.setPeekingImage(!uiState.isPeekingImage)
                                                }
                                            )
                                        }
                                        .testTag("puzzle_btn_peek_image")
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Visibility,
                                            contentDescription = "Ver Imagem Completa",
                                            tint = GoldLight,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Text(
                                            text = "VER IMAGEM",
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = GoldLight,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 11.sp
                                            )
                                        )
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = CanvasDeep
                            )
                        )
                    },
                    containerColor = CanvasDeep
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Painel do HUD Superior: MOVIMENTOS e TEMPO (Requisito 11)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            HudItem(label = "MOVIMENTOS", value = "${uiState.moveCount}")
                            HudItem(label = "TEMPO", value = uiState.formattedTime)
                        }

                        // Tabuleiro Protagonista (Requisitos 8, 9, 10, 19, 30)
                        PuzzleBoardView(
                            puzzleImage = img,
                            difficulty = uiState.difficulty,
                            pieces = uiState.pieces,
                            selectedPieceIndex = uiState.selectedPieceIndex,
                            recentlyCorrectPieceId = uiState.recentlyCorrectPieceId,
                            isSolved = false,
                            onPieceClicked = { index -> viewModel.onPieceClicked(index) },
                            onSwapPieces = { a, b -> viewModel.swapPieces(a, b) }
                        )

                        // Dica suave na parte inferior sem poluir a imagem
                        Text(
                            text = if (uiState.selectedPieceIndex == null) {
                                "Toque em uma peça para selecioná-la"
                            } else {
                                "Agora toque na peça com a qual deseja trocar"
                            },
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = if (uiState.selectedPieceIndex == null) MistLight else GoldLight,
                                fontStyle = FontStyle.Italic,
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }
                }
            }
        }

        PuzzlePhase.SOLVED -> {
            val img = uiState.selectedImage
            if (img != null) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Puzzle da Memória",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = GoldLight,
                                        fontFamily = FontFamily.Serif,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            },
                            colors = TopAppBarDefaults.topAppBarColors(containerColor = CanvasDeep)
                        )
                    },
                    containerColor = CanvasDeep
                ) { paddingValues ->
                    PuzzleSolvedView(
                        puzzleImage = img,
                        difficulty = uiState.difficulty,
                        formattedTime = uiState.formattedTime,
                        moveCount = uiState.moveCount,
                        onAnotherPuzzle = { viewModel.playAnotherPuzzle() },
                        onPlayAgain = { viewModel.playAgainSameImage() },
                        onChangeDifficulty = { viewModel.openDifficultySelection() },
                        onBack = { viewModel.goBackHome() },
                        modifier = Modifier.padding(paddingValues)
                    )
                }
            }
        }
    }
}

@Composable
private fun HudItem(
    label: String,
    value: String
) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = CanvasSurface,
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontSize = 10.sp,
                    letterSpacing = 1.sp
                )
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = GoldLight,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}
