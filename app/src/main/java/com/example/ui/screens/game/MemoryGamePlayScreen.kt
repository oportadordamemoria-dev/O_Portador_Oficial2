package com.example.ui.screens.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.data.game.GameMode
import com.example.data.game.MemoryCard
import com.example.ui.screens.game.saved.SaveIndicatorChip
import com.example.ui.theme.AmberTertiary
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.CanvasSurfaceVariant
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment
import com.example.ui.theme.TextSubtle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoryGamePlayScreen(
    levelNumber: Int,
    specialMode: String? = null,
    onBack: () -> Unit,
    onNavigateNextLevel: (Int) -> Unit = {},
    viewModel: MemoryGameViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val saveIndicatorVisible by viewModel.saveIndicatorVisible.collectAsState()
    var showRelationHelpDialog by remember { mutableStateOf(false) }

    LaunchedEffect(levelNumber, specialMode) {
        when (specialMode) {
            "infinite" -> viewModel.startInfiniteMode()
            "missing_card" -> viewModel.startMissingCardMode()
            else -> viewModel.startLevel(levelNumber)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = uiState.currentLevel.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldLight,
                                fontWeight = FontWeight.Bold
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "${uiState.currentLevel.environmentName} • Rodada ${uiState.currentRound} de ${uiState.totalRounds}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextMuted,
                                fontSize = 11.sp
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("game_play_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Sair do Desafio",
                            tint = GoldLight
                        )
                    }
                },
                actions = {
                    if (uiState.currentLevel.gameMode == GameMode.RELATIONS) {
                        IconButton(
                            onClick = { showRelationHelpDialog = true },
                            modifier = Modifier.testTag("btn_relation_help")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Ver Relações",
                                tint = GoldLight
                            )
                        }
                    }
                    IconButton(
                        onClick = { viewModel.restartCurrentLevel() },
                        modifier = Modifier.testTag("btn_restart_round")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reiniciar Desafio",
                            tint = TextMuted
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CanvasDeep
                )
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp, vertical = 6.dp)
            ) {
                // ==========================================
                // HUD DO JOGO (PONTOS, MEMÓRIAS, COMBO)
                // ==========================================
                GameHud(
                    score = uiState.currentScore,
                    recoveredMemories = uiState.roundMemoriesRecovered,
                    comboStreak = uiState.comboStreak,
                    multiplier = uiState.comboMultiplier
                )

                Spacer(modifier = Modifier.height(8.dp))

                // ==========================================
                // FAIXA DE STATUS / FEEDBACK / CONTAGEM
                // ==========================================
                PhaseStatusBanner(
                    phase = uiState.phase,
                    feedbackMessage = uiState.feedbackMessage,
                    gameMode = uiState.currentLevel.gameMode
                )

                Spacer(modifier = Modifier.height(10.dp))

                // ==========================================
                // ÁREA DO TABULEIRO CONFORME O MODO
                // ==========================================
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    when (uiState.currentLevel.gameMode) {
                        GameMode.PAIRS, GameMode.RELATIONS, GameMode.INFINITE -> {
                            PairsGameBoard(
                                slots = uiState.slots,
                                enabled = uiState.phase is GamePhase.Playing,
                                onCardTapped = { slotId -> viewModel.onCardTapped(slotId) }
                            )
                        }
                        GameMode.SEQUENCE, GameMode.PROGRESSIVE_CHAIN -> {
                            SequenceGameBoard(
                                slots = uiState.slots,
                                targetSequence = uiState.targetSequence,
                                isPreviewing = uiState.phase is GamePhase.Previewing,
                                currentStep = uiState.currentSequenceStep,
                                onCardTapped = { slotId -> viewModel.onCardTapped(slotId) }
                            )
                        }
                        GameMode.MISSING_CARD -> {
                            MissingCardGameBoard(
                                slots = uiState.slots,
                                isMistCovering = uiState.isMistCovering,
                                isPreviewing = uiState.phase is GamePhase.Previewing,
                                choices = uiState.missingCardChoices,
                                onChoiceSelected = { chosen -> viewModel.onMissingChoiceSelected(chosen) }
                            )
                        }
                    }
                }
            }

            // ==========================================
            // OVERLAY: DESAFIO CONCLUÍDO (FIM DA RODADA)
            // ==========================================
            val currentPhase = uiState.phase
            if (currentPhase is GamePhase.RoundFinished) {
                RoundFinishedDialog(
                    roundResult = currentPhase,
                    levelTitle = uiState.currentLevel.title,
                    environmentName = uiState.currentLevel.environmentName,
                    onPlayAgain = { viewModel.restartCurrentLevel() },
                    onNextChallenge = {
                        val next = currentPhase.nextLevelNumber
                        if (next != null) {
                            onNavigateNextLevel(next)
                        } else {
                            onBack()
                        }
                    },
                    onBackToHub = onBack
                )
            }

            // Diálogo de Guia de Relações (Nível 2)
            if (showRelationHelpDialog && uiState.activeRelationHint.isNotBlank()) {
                Dialog(onDismissRequest = { showRelationHelpDialog = false }) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = CanvasCard),
                        border = BorderStroke(1.dp, GoldLight)
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Relações Canônicas",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = GoldLight,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = uiState.activeRelationHint,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TextParchment,
                                    lineHeight = 22.sp
                                ),
                                textAlign = TextAlign.Start
                            )
                            Spacer(modifier = Modifier.height(18.dp))
                            Button(
                                onClick = { showRelationHelpDialog = false },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = GoldPrimary,
                                    contentColor = CanvasDeep
                                )
                            ) {
                                Text("Entendi")
                            }
                        }
                    }
                }
            }
        }
    }

    // Indicador discreto de salvamento automático (Requisito 12)
        SaveIndicatorChip(
            visible = saveIndicatorVisible,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 72.dp)
                .zIndex(30f)
        )
    }
}

/**
 * HUD superior com Pontuação, Fragmentos e Combo Streak.
 */
@Composable
private fun GameHud(
    score: Int,
    recoveredMemories: Int,
    comboStreak: Int,
    multiplier: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("game_hud"),
        colors = CardDefaults.cardColors(containerColor = CanvasSurface),
        border = BorderStroke(1.dp, CanvasBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Pontuação
            Column {
                Text(
                    text = "PONTUAÇÃO",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextMuted,
                        fontSize = 9.sp
                    )
                )
                Text(
                    text = "$score",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // Memórias Recuperadas (✦)
            Surface(
                color = CanvasDeep,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(0.8.dp, CanvasBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "✦", color = GoldLight, fontSize = 14.sp)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$recoveredMemories",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = TextParchment,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Multiplicador / Sequência
            Surface(
                color = if (multiplier > 1) GoldDark else CanvasDeep,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(0.8.dp, if (multiplier > 1) GoldLight else CanvasBorder)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Bolt,
                        contentDescription = null,
                        tint = if (multiplier > 1) CanvasDeep else GoldLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "${multiplier}x",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = if (multiplier > 1) CanvasDeep else GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

/**
 * Faixa informativa que mostra instruções e contagem regressiva da prévia.
 */
@Composable
private fun PhaseStatusBanner(
    phase: GamePhase,
    feedbackMessage: String,
    gameMode: GameMode
) {
    when (phase) {
        is GamePhase.Previewing -> {
            Surface(
                color = Color(0x33D4AF37),
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(1.dp, GoldLight.copy(alpha = 0.6f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Observe as memórias... virando em ${phase.secondsRemaining}s",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        text = "${phase.secondsRemaining}s",
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
        else -> {
            Surface(
                color = CanvasCard,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(0.8.dp, CanvasBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = feedbackMessage.ifBlank { "Toque nas cartas para revelar as memórias" },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextParchment,
                        fontStyle = FontStyle.Italic
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                )
            }
        }
    }
}

/**
 * Tabuleiro do modo PARES (Nível 1, 2 e Infinito)
 */
@Composable
private fun PairsGameBoard(
    slots: List<CardSlot>,
    enabled: Boolean,
    onCardTapped: (slotId: Int) -> Unit
) {
    val columns = when {
        slots.size <= 6 -> 3
        slots.size <= 8 -> 4
        else -> 4 // 10 cartas ou mais
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(4.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxSize()
            .testTag("pairs_game_board")
    ) {
        items(slots, key = { it.slotId }) { slot ->
            MemoryCardView(
                slot = slot,
                enabled = enabled,
                onClick = { onCardTapped(slot.slotId) }
            )
        }
    }
}

/**
 * Tabuleiro do modo SEQUÊNCIA (Nível 3 e 4)
 */
@Composable
private fun SequenceGameBoard(
    slots: List<CardSlot>,
    targetSequence: List<MemoryCard>,
    isPreviewing: Boolean,
    currentStep: Int,
    onCardTapped: (slotId: Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPreviewing) {
            // Durante a prévia, exibe a sequência em ordem horizontal
            Text(
                text = "CAMINHO A SER RECORDADO",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = GoldLight,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                ),
                modifier = Modifier.padding(bottom = 6.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
            ) {
                items(targetSequence) { card ->
                    Card(
                        modifier = Modifier
                            .width(88.dp)
                            .height(125.dp),
                        colors = CardDefaults.cardColors(containerColor = CanvasCard),
                        border = BorderStroke(1.dp, GoldLight),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            if (card.imageRes != null) {
                                Image(
                                    painter = painterResource(id = card.imageRes),
                                    contentDescription = card.name,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .weight(1f)
                                )
                            }
                            Text(
                                text = card.name,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GoldLight,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
        } else {
            // Em jogo: indicador de progresso da sequência
            Row(
                modifier = Modifier.padding(vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                targetSequence.forEachIndexed { index, _ ->
                    Surface(
                        shape = CircleShape,
                        color = when {
                            index < currentStep -> GoldLight
                            index == currentStep -> GoldDark
                            else -> CanvasCard
                        },
                        border = BorderStroke(1.dp, if (index <= currentStep) GoldLight else CanvasBorder),
                        modifier = Modifier.size(14.dp)
                    ) {}
                }
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Passo ${currentStep + 1} de ${targetSequence.size}",
                    style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                )
            }
        }

        // Cartas embaralhadas no tabuleiro para o jogador tocar
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(slots, key = { it.slotId }) { slot ->
                MemoryCardView(
                    slot = slot,
                    enabled = !isPreviewing,
                    onClick = { onCardTapped(slot.slotId) }
                )
            }
        }
    }
}

/**
 * Tabuleiro do modo QUAL MEMÓRIA DESAPARECEU? (Nível 5 e Modo Infantil)
 */
@Composable
private fun MissingCardGameBoard(
    slots: List<CardSlot>,
    isMistCovering: Boolean,
    isPreviewing: Boolean,
    choices: List<MemoryCard>,
    onChoiceSelected: (MemoryCard) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tabuleiro com as cartas (com efeito de névoa ao cobrir)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(slots, key = { it.slotId }) { slot ->
                    MemoryCardView(
                        slot = slot,
                        enabled = false,
                        onClick = {}
                    )
                }
            }

            // Efeito de Névoa Animado cobrindo o tabuleiro
            if (isMistCovering) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color(0xDD0A0E17),
                                    Color(0xFA000000),
                                    Color(0xDD0A0E17)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "🌫️", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "A névoa do esquecimento está passando...",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = GoldLight,
                                fontStyle = FontStyle.Italic
                            )
                        )
                    }
                }
            }
        }

        // Opções de escolha na fase de jogo
        if (!isPreviewing && !isMistCovering && choices.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "QUAL MEMÓRIA DESAPARECEU?",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    choices.forEach { choice ->
                        Card(
                            modifier = Modifier
                                .weight(1f)
                                .height(96.dp)
                                .clickable { onChoiceSelected(choice) },
                            colors = CardDefaults.cardColors(containerColor = CanvasCard),
                            border = BorderStroke(1.dp, CanvasBorder),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Column(
                                modifier = Modifier.fillMaxSize(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                if (choice.imageRes != null) {
                                    Image(
                                        painter = painterResource(id = choice.imageRes),
                                        contentDescription = choice.name,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(44.dp)
                                            .clip(RoundedCornerShape(6.dp))
                                    )
                                } else {
                                    Text(text = choice.symbolIcon, fontSize = 24.sp)
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = choice.name,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = TextParchment,
                                        fontSize = 9.5.sp,
                                        fontWeight = FontWeight.Bold
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Diálogo de Conclusão de Rodada com Estrelas, Estatísticas e Próximo Desafio.
 */
@Composable
private fun RoundFinishedDialog(
    roundResult: GamePhase.RoundFinished,
    levelTitle: String,
    environmentName: String,
    onPlayAgain: () -> Unit,
    onNextChallenge: () -> Unit,
    onBackToHub: () -> Unit
) {
    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .testTag("dialog_round_finished"),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = CanvasCard),
            border = BorderStroke(1.5.dp, GoldLight)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Ícone de Conquista Sagrada
                Surface(
                    shape = CircleShape,
                    color = Color(0x33D4AF37),
                    border = BorderStroke(1.dp, GoldLight),
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "DESAFIO CONCLUÍDO",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "“Uma nova memória foi preservada.”",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontStyle = FontStyle.Italic
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Avaliação em Estrelas
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    repeat(3) { index ->
                        val isLit = index < roundResult.stars
                        Icon(
                            imageVector = if (isLit) Icons.Default.Star else Icons.Default.StarBorder,
                            contentDescription = null,
                            tint = if (isLit) GoldLight else TextMuted,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quadro de métricas conquistadas
                Surface(
                    color = CanvasDeep,
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(0.8.dp, CanvasBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Pontuação Total", color = TextMuted, fontSize = 12.sp)
                            Text(
                                text = "${roundResult.scoreEarned} pts",
                                color = GoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Memórias Recuperadas", color = TextMuted, fontSize = 12.sp)
                            Text(
                                text = "✦ ${roundResult.memoriesRecovered}",
                                color = GoldLight,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Maior Sequência", color = TextMuted, fontSize = 12.sp)
                            Text(
                                text = "${roundResult.comboStreak}x",
                                color = TextParchment,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Botão de Próximo Desafio
                if (roundResult.nextLevelNumber != null) {
                    Button(
                        onClick = onNextChallenge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("btn_dialog_next_challenge"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "PRÓXIMO DESAFIO",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }

                // Botão Jogar Novamente
                OutlinedButton(
                    onClick = onPlayAgain,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("btn_dialog_play_again"),
                    border = BorderStroke(1.dp, GoldLight),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "JOGAR NOVAMENTE",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Voltar à Jornada
                Button(
                    onClick = onBackToHub,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .testTag("btn_dialog_back_hub"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = TextMuted
                    )
                ) {
                    Text(
                        text = "Voltar à Jornada",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                    )
                }
            }
        }
    }
}
