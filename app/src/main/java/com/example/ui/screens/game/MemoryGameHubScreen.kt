package com.example.ui.screens.game

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.AllInclusive
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.R
import com.example.data.game.GameContentData
import com.example.data.game.GameLevelConfig
import com.example.data.game.db.CreatePlayerResult
import com.example.data.game.db.GameProgressData
import com.example.data.game.db.PlayerProfile
import com.example.data.game.db.RenamePlayerResult
import com.example.ui.screens.game.saved.ConfirmDeletePlayerDialog
import com.example.ui.screens.game.saved.DuplicateNameDialog
import com.example.ui.screens.game.saved.FirstAccessPlayerDialog
import com.example.ui.screens.game.saved.NewPlayerDialog
import com.example.ui.screens.game.saved.RenamePlayerDialog
import com.example.ui.screens.game.saved.SaveIndicatorChip
import com.example.ui.screens.game.saved.SavedProfilesDialog
import com.example.ui.screens.game.saved.WelcomeBackCard
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
fun MemoryGameHubScreen(
    onBack: () -> Unit,
    onStartGame: (levelNumber: Int) -> Unit,
    onStartSpecialMode: (modeName: String) -> Unit,
    viewModel: MemoryGameViewModel = viewModel()
) {
    val progress by viewModel.progress.collectAsState()
    val allPlayers by viewModel.allPlayers.collectAsState()
    val activePlayer by viewModel.activePlayer.collectAsState()
    val saveIndicatorVisible by viewModel.saveIndicatorVisible.collectAsState()

    var showSavedProfilesDialog by remember { mutableStateOf(false) }
    var showNewPlayerDialog by remember { mutableStateOf(false) }
    var playerToRename by remember { mutableStateOf<PlayerProfile?>(null) }
    var playerToDelete by remember { mutableStateOf<PlayerProfile?>(null) }
    var duplicatePlayerCandidate by remember { mutableStateOf<PlayerProfile?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "DESAFIO DA MEMÓRIA",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldLight,
                                letterSpacing = 1.2.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = onBack,
                            modifier = Modifier.testTag("memory_hub_back_button")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Voltar",
                                tint = GoldLight
                            )
                        }
                    },
                    actions = {
                        activePlayer?.let { player ->
                            Surface(
                                color = CanvasSurfaceVariant,
                                shape = RoundedCornerShape(16.dp),
                                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
                                modifier = Modifier
                                    .padding(end = 12.dp)
                                    .clickable { showSavedProfilesDialog = true }
                                    .testTag("memory_hub_top_player_badge")
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = GoldLight,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Text(
                                        text = player.playerName,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = TextParchment,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                            }
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CanvasDeep
                    )
                )
            },
            containerColor = CanvasDeep
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .testTag("memory_game_hub_list"),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                // ==========================================
                // HERO BANNER & APRESENTAÇÃO
                // ==========================================
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.hero_cover_art),
                            contentDescription = "Desafio da Memória Banner",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color(0x33000000),
                                            Color(0xBB080B10),
                                            CanvasDeep
                                        )
                                    )
                                )
                        )

                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0x33D4AF37),
                                border = BorderStroke(1.dp, GoldLight.copy(alpha = 0.6f)),
                                modifier = Modifier.size(48.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Psychology,
                                        contentDescription = null,
                                        tint = GoldLight,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Text(
                                text = "DESAFIO DA MEMÓRIA",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = GoldLight,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.5.sp
                                ),
                                textAlign = TextAlign.Center
                            )

                            Text(
                                text = "“Quanto você consegue lembrar antes que tudo seja esquecido?”",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMuted,
                                    fontStyle = FontStyle.Italic
                                ),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }

                // ==========================================
                // BEM-VINDO DE VOLTA / CONTINUAR JORNADA (REQUISITO 6)
                // ==========================================
                item {
                    activePlayer?.let { player ->
                        WelcomeBackCard(
                            player = player,
                            onContinue = {
                                onStartGame(player.highestLevelUnlocked)
                            },
                            onChangePlayer = {
                                showSavedProfilesDialog = true
                            }
                        )
                    }
                }

                // ==========================================
                // TEXTO DE CONVOCAÇÃO DA MEMÓRIA
                // ==========================================
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, CanvasBorder),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "“Algumas memórias permanecem.\nOutras desaparecem quando deixamos de olhar para elas.\n\nObserve.\nLembre.\nRelacione.\n\nE não deixe que sejam esquecidas.”",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextParchment,
                                fontStyle = FontStyle.Italic,
                                lineHeight = 22.sp,
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))

                        // BOTÃO PRINCIPAL: INICIAR DESAFIO
                        Button(
                            onClick = {
                                onStartGame(progress.highestLevelUnlocked)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp)
                                .testTag("memory_hub_btn_start"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = CanvasDeep
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PlayArrow,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "INICIAR DESAFIO",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                )
                            )
                        }
                    }
                }
            }

            // ==========================================
            // PAINEL DE ESTATÍSTICAS E CONQUISTAS
            // ==========================================
            item {
                StatsDashboard(progress = progress)
            }

            // ==========================================
            // JORNADA VISUAL PELOS LUGARES DO UNIVERSO
            // ==========================================
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Jornada pelos Lugares do Universo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Text(
                        text = "Cada ambiente preservado ilumina um novo horizonte de memória e desbloqueia o desafio seguinte.",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                    )
                }
            }

            items(GameContentData.levels) { levelConfig ->
                LevelJourneyCard(
                    levelConfig = levelConfig,
                    isUnlocked = levelConfig.levelNumber <= progress.highestLevelUnlocked,
                    isCurrent = levelConfig.levelNumber == progress.highestLevelUnlocked,
                    onSelect = { onStartGame(levelConfig.levelNumber) }
                )
            }

            // ==========================================
            // MODOS ESPECIAIS
            // ==========================================
            item {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Text(
                        text = "Mecânicas Especiais",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier.padding(vertical = 6.dp)
                    )

                    // Modo Infantil / Observação: Qual memória desapareceu?
                    SpecialModeCard(
                        title = "Qual Memória Desapareceu?",
                        subtitle = "Ideal para crianças e observadores atentos • Identifique a carta que a névoa levou",
                        icon = Icons.Default.Search,
                        badge = "Observação",
                        testTag = "mode_btn_missing_card",
                        onClick = { onStartSpecialMode("missing_card") }
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Desafio Infinito
                    SpecialModeCard(
                        title = "Desafio Infinito",
                        subtitle = "Sequência progressiva contínua • Recorde pessoal: ${progress.infiniteModeRecord} acertos",
                        icon = Icons.Default.AllInclusive,
                        badge = "Resistência",
                        testTag = "mode_btn_infinite",
                        onClick = { onStartSpecialMode("infinite") }
                    )
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
                .zIndex(20f)
        )
    }

    // ==========================================
    // DIÁLOGOS DE GERENCIAMENTO DE JOGADORES (REQUISITOS 3, 7, 8, 9, 10, 11)
    // ==========================================

    // Primeiro Acesso (se não houver nenhum jogador cadastrado)
    if (allPlayers.isEmpty() && activePlayer == null) {
        FirstAccessPlayerDialog(
            onConfirmName = { rawName ->
                viewModel.createPlayer(rawName) { result ->
                    if (result is CreatePlayerResult.DuplicateName) {
                        duplicatePlayerCandidate = result.existingPlayer
                    }
                }
            }
        )
    }

    // Gerenciamento de Memórias Guardadas
    if (showSavedProfilesDialog) {
        SavedProfilesDialog(
            players = allPlayers,
            activePlayerId = activePlayer?.playerId,
            onSelectPlayer = { selected ->
                viewModel.setActivePlayer(selected.playerId)
                showSavedProfilesDialog = false
            },
            onNewPlayerClick = {
                showNewPlayerDialog = true
            },
            onRenamePlayerClick = { target ->
                playerToRename = target
            },
            onDeletePlayerClick = { target ->
                playerToDelete = target
            },
            onDismiss = {
                showSavedProfilesDialog = false
            }
        )
    }

    // Criar Novo Jogador a partir de "Memórias Guardadas"
    if (showNewPlayerDialog) {
        NewPlayerDialog(
            onConfirm = { newName ->
                viewModel.createPlayer(newName) { result ->
                    when (result) {
                        is CreatePlayerResult.Success -> {
                            showNewPlayerDialog = false
                            showSavedProfilesDialog = false
                        }
                        is CreatePlayerResult.DuplicateName -> {
                            duplicatePlayerCandidate = result.existingPlayer
                        }
                        is CreatePlayerResult.Error -> {
                            // Erro tratado internamente no dialog
                        }
                    }
                }
            },
            onDismiss = {
                showNewPlayerDialog = false
            }
        )
    }

    // Nomes Repetidos (Tratamento Elegante)
    duplicatePlayerCandidate?.let { existing ->
        DuplicateNameDialog(
            existingPlayer = existing,
            onContinueAsExisting = {
                viewModel.setActivePlayer(existing.playerId)
                duplicatePlayerCandidate = null
                showNewPlayerDialog = false
                showSavedProfilesDialog = false
            },
            onUseAnotherName = {
                duplicatePlayerCandidate = null
            }
        )
    }

    // Confirmação de Exclusão
    playerToDelete?.let { target ->
        ConfirmDeletePlayerDialog(
            player = target,
            onConfirmDelete = {
                viewModel.deletePlayer(target.playerId)
                playerToDelete = null
            },
            onCancel = {
                playerToDelete = null
            }
        )
    }

    // Renomear Jogador
    playerToRename?.let { target ->
        RenamePlayerDialog(
            player = target,
            onConfirmRename = { updatedName ->
                viewModel.renamePlayer(target.playerId, updatedName) { result ->
                    when (result) {
                        is RenamePlayerResult.Success -> {
                            playerToRename = null
                        }
                        is RenamePlayerResult.DuplicateName -> {
                            duplicatePlayerCandidate = result.existingPlayer
                        }
                        is RenamePlayerResult.Error -> {
                            // Tratado internamente
                        }
                    }
                }
            },
            onCancel = {
                playerToRename = null
            }
        )
    }
}

/**
 * Painel com 4 métricas locais salvas em Room.
 */
@Composable
private fun StatsDashboard(progress: GameProgressData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasSurface),
        border = BorderStroke(1.dp, CanvasBorder),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "SUA TRAVESSIA DE PRESERVAÇÃO",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = GoldLight,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatMetricItem(
                    label = "Melhor Pontuação",
                    value = progress.highScore.toString(),
                    icon = "🏆"
                )
                StatMetricItem(
                    label = "Nível / Título",
                    value = progress.rankTitle,
                    icon = "👑"
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatMetricItem(
                    label = "Memórias Recuperadas",
                    value = "✦ ${progress.recoveredMemories}",
                    icon = "✨"
                )
                StatMetricItem(
                    label = "Maior Sequência",
                    value = "${progress.maxComboStreak}x",
                    icon = "⚡"
                )
            }
        }
    }
}

@Composable
private fun StatMetricItem(
    label: String,
    value: String,
    icon: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CanvasDeep,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(0.8.dp, CanvasBorder),
        modifier = modifier
            .width(160.dp)
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextMuted,
                        fontSize = 9.sp
                    )
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * Card representativo de cada nível na jornada pelos ambientes.
 */
@Composable
private fun LevelJourneyCard(
    levelConfig: GameLevelConfig,
    isUnlocked: Boolean,
    isCurrent: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(enabled = isUnlocked, onClick = onSelect)
            .testTag("level_card_${levelConfig.levelNumber}"),
        colors = CardDefaults.cardColors(
            containerColor = if (isCurrent) CanvasSurfaceVariant else CanvasCard
        ),
        border = BorderStroke(
            width = if (isCurrent) 1.5.dp else 1.dp,
            color = if (isCurrent) GoldPrimary else CanvasBorder
        ),
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Thumbnail do Ambiente
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(CanvasDeep)
            ) {
                Image(
                    painter = painterResource(id = levelConfig.environmentImageRes),
                    contentDescription = levelConfig.environmentName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (!isUnlocked) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xCC000000)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Bloqueado",
                            tint = TextMuted,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "NÍVEL ${levelConfig.levelNumber} — ${levelConfig.rankTitle.uppercase()}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isUnlocked) GoldLight else TextMuted,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.8.sp
                        )
                    )
                    if (isCurrent) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = GoldDark,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "ATUAL",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = CanvasDeep,
                                    fontSize = 8.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }

                Text(
                    text = levelConfig.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = if (isUnlocked) TextParchment else TextMuted,
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = levelConfig.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isUnlocked) TextMuted else TextSubtle,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            IconButton(
                onClick = onSelect,
                enabled = isUnlocked
            ) {
                Icon(
                    imageVector = if (isUnlocked) Icons.AutoMirrored.Filled.ArrowForward else Icons.Default.Lock,
                    contentDescription = null,
                    tint = if (isUnlocked) GoldLight else TextMuted
                )
            }
        }
    }
}

/**
 * Card de modo especial com visual destacado.
 */
@Composable
private fun SpecialModeCard(
    title: String,
    subtitle: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    badge: String,
    testTag: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag(testTag),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                color = Color(0x33D4AF37),
                shape = CircleShape,
                border = BorderStroke(0.8.dp, GoldPrimary.copy(alpha = 0.5f)),
                modifier = Modifier.size(44.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Surface(
                        color = Color(0x331E293B),
                        shape = RoundedCornerShape(4.dp),
                        border = BorderStroke(0.5.dp, GoldLight.copy(alpha = 0.4f))
                    ) {
                        Text(
                            text = badge,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldLight,
                                fontSize = 8.5.sp
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                        )
                    }
                }

                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontSize = 11.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = GoldLight,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}
