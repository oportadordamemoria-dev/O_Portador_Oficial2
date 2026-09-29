package com.example.ui.screens.game.saved

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.game.db.PlayerProfile
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

// ==========================================
// 1. PRIMEIRO ACESSO AO JOGO
// ==========================================
@Composable
fun FirstAccessPlayerDialog(
    onConfirmName: (String) -> Unit
) {
    var playerNameInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = { /* Obrigatório para primeiro acesso */ }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("first_access_player_dialog"),
            colors = CardDefaults.cardColors(containerColor = CanvasDeep),
            border = BorderStroke(1.5.dp, GoldPrimary),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = Color(0x33D4AF37),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, GoldLight.copy(alpha = 0.6f)),
                    modifier = Modifier.size(56.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Psychology,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "DESAFIO DA MEMÓRIA",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.5.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "“Antes de começar sua jornada, diga-nos como devemos guardar sua memória.”",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextParchment,
                        fontStyle = FontStyle.Italic,
                        lineHeight = 22.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = playerNameInput,
                    onValueChange = {
                        playerNameInput = it
                        errorMessage = null
                    },
                    label = { Text("NOME DO JOGADOR", color = GoldLight, fontSize = 11.sp) },
                    placeholder = { Text("Digite seu nome", color = TextMuted) },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("input_player_name"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextParchment,
                        unfocusedTextColor = TextParchment,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = CanvasBorder,
                        focusedContainerColor = CanvasSurface,
                        unfocusedContainerColor = CanvasSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = AmberTertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        val trimmed = playerNameInput.trim()
                        if (trimmed.isNotBlank()) {
                            onConfirmName(trimmed)
                        } else {
                            errorMessage = "Por favor, digite seu nome para guardar sua jornada."
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_start_journey"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = CanvasDeep
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "COMEÇAR MINHA JORNADA",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }
        }
    }
}

// ==========================================
// 2. CONTINUAR JOGO (BEM-VINDO DE VOLTA)
// ==========================================
@Composable
fun WelcomeBackCard(
    player: PlayerProfile,
    onContinue: () -> Unit,
    onChangePlayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .testTag("welcome_back_card"),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.2.dp, GoldPrimary.copy(alpha = 0.8f)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Surface(
                    shape = CircleShape,
                    color = Color(0x33D4AF37),
                    border = BorderStroke(1.dp, GoldLight.copy(alpha = 0.6f)),
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = GoldLight,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "DESAFIO DA MEMÓRIA",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = "Bem-vindo de volta, ${player.playerName}.",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = TextParchment,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Resumo do Progresso
            Surface(
                color = CanvasSurface,
                shape = RoundedCornerShape(10.dp),
                border = BorderStroke(0.8.dp, CanvasBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = "NÍVEL ATUAL",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextMuted,
                                fontSize = 9.sp
                            )
                        )
                        Text(
                            text = "Nível ${player.highestLevelUnlocked} • ${player.rankTitle}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = GoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "MEMÓRIAS",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextMuted,
                                fontSize = 9.sp
                            )
                        )
                        Text(
                            text = "✦ ${player.recoveredMemories}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = GoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "MELHOR PONTUAÇÃO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextMuted,
                                fontSize = 9.sp
                            )
                        )
                        Text(
                            text = "${player.highScore}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = GoldLight,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Botões de Ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onContinue,
                    modifier = Modifier
                        .weight(1f)
                        .height(46.dp)
                        .testTag("btn_continue_journey"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = CanvasDeep
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "CONTINUAR JORNADA",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }

                OutlinedButton(
                    onClick = onChangePlayer,
                    modifier = Modifier
                        .height(46.dp)
                        .testTag("btn_switch_player"),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = GoldLight
                    ),
                    border = BorderStroke(1.dp, CanvasBorder),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "TROCAR JOGADOR",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

// ==========================================
// 3. MEMÓRIAS GUARDADAS (GERENCIAMENTO DE PERFIS)
// ==========================================
@Composable
fun SavedProfilesDialog(
    players: List<PlayerProfile>,
    activePlayerId: String?,
    onSelectPlayer: (PlayerProfile) -> Unit,
    onNewPlayerClick: () -> Unit,
    onRenamePlayerClick: (PlayerProfile) -> Unit,
    onDeletePlayerClick: (PlayerProfile) -> Unit,
    onDismiss: () -> Unit
) {
    var showHelpInfo by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .testTag("saved_memories_dialog"),
            colors = CardDefaults.cardColors(containerColor = CanvasDeep),
            border = BorderStroke(1.2.dp, GoldPrimary),
            shape = RoundedCornerShape(20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Top header
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Psychology,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "MEMÓRIAS GUARDADAS",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.2.sp
                        ),
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onDismiss) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            tint = TextMuted
                        )
                    }
                }

                Text(
                    text = "Escolha de quem é a memória que deseja continuar iluminando:",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted),
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                // Lista de Perfis Locais
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f, fill = false),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(players, key = { it.playerId }) { player ->
                        SavedProfileItem(
                            player = player,
                            isActive = player.playerId == activePlayerId,
                            onContinue = { onSelectPlayer(player) },
                            onRename = { onRenamePlayerClick(player) },
                            onDelete = { onDeletePlayerClick(player) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Botão + NOVO JOGADOR
                Button(
                    onClick = onNewPlayerClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("btn_new_player"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = CanvasSurfaceVariant,
                        contentColor = GoldLight
                    ),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "+ NOVO JOGADOR",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Aviso Discreto sobre Salvamento Local (Requisito 20)
                Surface(
                    color = CanvasSurface,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(0.6.dp, CanvasBorder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showHelpInfo = !showHelpInfo }
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Seu progresso fica guardado neste dispositivo.",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextMuted,
                                fontSize = 10.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                if (showHelpInfo) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Os jogos são armazenados somente neste dispositivo. A remoção do aplicativo ou a limpeza dos dados do aplicativo pode apagar os progressos salvos.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSubtle,
                            fontSize = 10.sp,
                            lineHeight = 14.sp
                        ),
                        modifier = Modifier.padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun SavedProfileItem(
    player: PlayerProfile,
    isActive: Boolean,
    onContinue: () -> Unit,
    onRename: () -> Unit,
    onDelete: () -> Unit
) {
    Surface(
        color = if (isActive) CanvasSurfaceVariant else CanvasSurface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = if (isActive) 1.2.dp else 0.8.dp,
            color = if (isActive) GoldPrimary else CanvasBorder
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = player.playerName,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = if (isActive) GoldLight else TextParchment,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (isActive) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            color = GoldDark,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = "ATIVO",
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

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "Nível ${player.highestLevelUnlocked} • ✦ ${player.recoveredMemories} Memórias",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontSize = 11.sp
                    )
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                // Renomear
                IconButton(
                    onClick = onRename,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Renomear",
                        tint = TextMuted,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Apagar
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Apagar",
                        tint = AmberTertiary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                // Continuar
                Button(
                    onClick = onContinue,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = CanvasDeep
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        horizontal = 12.dp,
                        vertical = 6.dp
                    ),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = "CONTINUAR",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.5.sp
                        )
                    )
                }
            }
        }
    }
}

// ==========================================
// 4. DIÁLOGO: + NOVO JOGADOR (NOVA JORNADA)
// ==========================================
@Composable
fun NewPlayerDialog(
    onConfirm: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var nameInput by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .testTag("dialog_new_player"),
            colors = CardDefaults.cardColors(containerColor = CanvasDeep),
            border = BorderStroke(1.2.dp, GoldPrimary),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "NOVA JORNADA",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "“Qual é o seu nome?”",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextParchment,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center
                    )
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = {
                        nameInput = it
                        errorMessage = null
                    },
                    placeholder = { Text("Digite seu nome", color = TextMuted) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextParchment,
                        unfocusedTextColor = TextParchment,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = CanvasBorder,
                        focusedContainerColor = CanvasSurface,
                        unfocusedContainerColor = CanvasSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = AmberTertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("CANCELAR", color = TextMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val trimmed = nameInput.trim()
                            if (trimmed.isNotBlank()) {
                                onConfirm(trimmed)
                            } else {
                                errorMessage = "Digite seu nome para iniciar."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "COMEÇAR JORNADA",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 5. DIÁLOGO: NOMES REPETIDOS
// ==========================================
@Composable
fun DuplicateNameDialog(
    existingPlayer: PlayerProfile,
    onContinueAsExisting: () -> Unit,
    onUseAnotherName: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onUseAnotherName,
        containerColor = CanvasDeep,
        shape = RoundedCornerShape(18.dp),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.WarningAmber,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Memória Existente",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "“Já existe uma memória guardada com esse nome.”",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = TextParchment,
                        fontStyle = FontStyle.Italic
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Deseja continuar a jornada já guardada de ${existingPlayer.playerName} ou usar outro nome?",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onContinueAsExisting,
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = CanvasDeep
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "CONTINUAR COMO ${existingPlayer.playerName.uppercase()}",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onUseAnotherName,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextParchment),
                border = BorderStroke(1.dp, CanvasBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("USAR OUTRO NOME")
            }
        }
    )
}

// ==========================================
// 6. DIÁLOGO: CONFIRMAÇÃO DE EXCLUSÃO
// ==========================================
@Composable
fun ConfirmDeletePlayerDialog(
    player: PlayerProfile,
    onConfirmDelete: () -> Unit,
    onCancel: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancel,
        containerColor = CanvasDeep,
        shape = RoundedCornerShape(18.dp),
        title = {
            Text(
                text = "Apagar a memória de ${player.playerName}?",
                style = MaterialTheme.typography.titleMedium.copy(
                    color = GoldLight,
                    fontWeight = FontWeight.Bold
                )
            )
        },
        text = {
            Text(
                text = "Todo o progresso deste jogador será apagado deste dispositivo.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextParchment
                )
            )
        },
        confirmButton = {
            Button(
                onClick = onConfirmDelete,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB71C1C),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "APAGAR PROGRESSO",
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onCancel,
                colors = ButtonDefaults.outlinedButtonColors(contentColor = TextParchment),
                border = BorderStroke(1.dp, CanvasBorder),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("CANCELAR")
            }
        }
    )
}

// ==========================================
// 7. DIÁLOGO: RENOMEAR JOGADOR
// ==========================================
@Composable
fun RenamePlayerDialog(
    player: PlayerProfile,
    onConfirmRename: (String) -> Unit,
    onCancel: () -> Unit
) {
    var newNameInput by remember { mutableStateOf(player.playerName) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onCancel) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            colors = CardDefaults.cardColors(containerColor = CanvasDeep),
            border = BorderStroke(1.2.dp, GoldPrimary),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(22.dp)
            ) {
                Text(
                    text = "RENOMEAR JOGADOR",
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Altere o nome da memória de ${player.playerName}. Seu progresso permanecerá intacto.",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextMuted)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = newNameInput,
                    onValueChange = {
                        newNameInput = it
                        errorMessage = null
                    },
                    label = { Text("NOVO NOME", color = GoldLight, fontSize = 11.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextParchment,
                        unfocusedTextColor = TextParchment,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = CanvasBorder,
                        focusedContainerColor = CanvasSurface,
                        unfocusedContainerColor = CanvasSurface
                    ),
                    shape = RoundedCornerShape(12.dp)
                )

                if (errorMessage != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = errorMessage ?: "",
                        color = AmberTertiary,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onCancel) {
                        Text("CANCELAR", color = TextMuted)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            val trimmed = newNameInput.trim()
                            if (trimmed.isNotBlank()) {
                                onConfirmRename(trimmed)
                            } else {
                                errorMessage = "O nome não pode estar em branco."
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "SALVAR NOME",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

// ==========================================
// 8. INDICAÇÃO DE SALVAMENTO AUTOMÁTICO (REQUISITO 12)
// ==========================================
@Composable
fun SaveIndicatorChip(
    visible: Boolean,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(initialOffsetY = { -20 }),
        exit = fadeOut() + slideOutVertically(targetOffsetY = { -20 }),
        modifier = modifier
    ) {
        Surface(
            color = Color(0xEE0D131F),
            shape = RoundedCornerShape(20.dp),
            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.7f)),
            shadowElevation = 6.dp
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "✦",
                    color = GoldLight,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Memória guardada.",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldLight,
                        fontWeight = FontWeight.Medium,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }
}
