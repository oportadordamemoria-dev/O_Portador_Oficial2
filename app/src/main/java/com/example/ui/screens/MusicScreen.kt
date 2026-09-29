package com.example.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.QueueMusic
import androidx.compose.material.icons.filled.Equalizer
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FileOpen
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.MusicTrack
import com.example.ui.components.BookTopBar
import com.example.ui.components.GoldBadge
import com.example.ui.components.NextSectionButton
import com.example.ui.components.SectionHeader
import com.example.ui.player.MusicPlayerController
import com.example.ui.theme.AmberTertiary
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

@Composable
fun MusicScreen(
    onBack: () -> Unit,
    onNavigateBlog: () -> Unit = {}
) {
    val context = LocalContext.current
    val controller = remember { MusicPlayerController(context) }

    // Clean up when leaving screen
    DisposableEffect(Unit) {
        onDispose {
            controller.release()
        }
    }

    // Audio file picker
    val audioPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            val displayName = uri.lastPathSegment ?: "Faixa Importada"
            controller.addCustomTrack(uri, displayName)
        }
    }

    val currentTrack = controller.currentTrack

    Scaffold(
        topBar = {
            BookTopBar(
                title = "Músicas do Universo",
                onBack = onBack,
                shareContent = "Trilha Sonora Oficial de O Portador da Memória: Entre a Luz e a Escuridão."
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 36.dp)
        ) {
            // Player Hero Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .testTag("music_player_deck"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, GoldDark)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Disc Art with animated rotation
                        RotatingDiscCover(isPlaying = controller.isPlaying)

                        Spacer(modifier = Modifier.height(16.dp))

                        // Track details
                        currentTrack?.let { track ->
                            GoldBadge(text = track.actConnection)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = track.title,
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextParchment
                                ),
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = track.subtitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MistSecondary
                                ),
                                textAlign = TextAlign.Center
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        // Animated Visualizer
                        WaveformVisualizer(isPlaying = controller.isPlaying)

                        Spacer(modifier = Modifier.height(10.dp))

                        // Timeline Slider
                        val currentMs = controller.currentPositionMs.toFloat()
                        val totalMs = controller.durationMs.coerceAtLeast(1L).toFloat()
                        val progressFraction = (currentMs / totalMs).coerceIn(0f, 1f)

                        Slider(
                            value = progressFraction,
                            onValueChange = { fraction ->
                                val target = (fraction * totalMs).toLong()
                                controller.seekTo(target)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("music_timeline_slider"),
                            colors = SliderDefaults.colors(
                                thumbColor = GoldPrimary,
                                activeTrackColor = GoldPrimary,
                                inactiveTrackColor = CanvasBorder
                            )
                        )

                        // Timestamps
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = formatTime(controller.currentPositionMs),
                                style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                            )
                            Text(
                                text = currentTrack?.durationFormatted ?: formatTime(controller.durationMs),
                                style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Playback Controls Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Shuffle
                            IconButton(
                                onClick = { controller.toggleShuffle() },
                                modifier = Modifier.testTag("btn_shuffle")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Shuffle,
                                    contentDescription = "Aleatório",
                                    tint = if (controller.isShuffle) GoldPrimary else TextMuted
                                )
                            }

                            // Previous
                            IconButton(
                                onClick = { controller.previousTrack() },
                                modifier = Modifier.testTag("btn_prev_track")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SkipPrevious,
                                    contentDescription = "Música Anterior",
                                    tint = GoldLight,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // Play / Pause (Large Primary Button)
                            FilledIconButton(
                                onClick = { controller.togglePlayPause() },
                                modifier = Modifier
                                    .size(60.dp)
                                    .testTag("btn_play_pause"),
                                colors = IconButtonDefaults.filledIconButtonColors(
                                    containerColor = GoldPrimary,
                                    contentColor = CanvasDeep
                                )
                            ) {
                                Icon(
                                    imageVector = if (controller.isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                    contentDescription = if (controller.isPlaying) "Pausar" else "Reproduzir",
                                    modifier = Modifier.size(36.dp)
                                )
                            }

                            // Stop
                            IconButton(
                                onClick = { controller.stop() },
                                modifier = Modifier.testTag("btn_stop")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Stop,
                                    contentDescription = "Parar",
                                    tint = GoldLight,
                                    modifier = Modifier.size(28.dp)
                                )
                            }

                            // Next
                            IconButton(
                                onClick = { controller.nextTrack() },
                                modifier = Modifier.testTag("btn_next_track")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SkipNext,
                                    contentDescription = "Próxima Música",
                                    tint = GoldLight,
                                    modifier = Modifier.size(32.dp)
                                )
                            }

                            // Repeat
                            IconButton(
                                onClick = { controller.toggleRepeat() },
                                modifier = Modifier.testTag("btn_repeat")
                            ) {
                                Icon(
                                    imageVector = if (controller.isRepeatOne) Icons.Default.RepeatOne else Icons.Default.Repeat,
                                    contentDescription = "Repetir",
                                    tint = if (controller.isRepeatOne) GoldPrimary else TextMuted
                                )
                            }
                        }
                    }
                }
            }

            // Current Track Description
            currentTrack?.let { track ->
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CanvasCard),
                        border = BorderStroke(1.dp, CanvasBorder)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Conceito & Ambientação",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight
                                    )
                                )
                                GoldBadge(text = track.mood, isSecondary = true)
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = track.description,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextParchment,
                                    lineHeight = 18.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Fonte: ${track.source}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MistSecondary
                                )
                            )
                        }
                    }
                }
            }

            // Track List Header
            item {
                SectionHeader(
                    title = "Trilhas do Universo",
                    subtitle = "Toque em qualquer faixa para ouvir e mergulhar na atmosfera narrativa",
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 14.dp)
                )
            }

            // Playlist Items
            items(controller.playlist.size) { index ->
                val track = controller.playlist[index]
                val isSelected = controller.currentTrackIndex == index
                val isCurrentlyPlaying = isSelected && controller.isPlaying

                TrackPlaylistItem(
                    track = track,
                    index = index + 1,
                    isSelected = isSelected,
                    isPlaying = isCurrentlyPlaying,
                    onClick = { controller.playTrackAt(index) }
                )
            }

            // Import Custom Audio Option
            item {
                Spacer(modifier = Modifier.height(14.dp))
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, CanvasBorder)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FileOpen,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Adicionar Arquivo de Áudio Local",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Deseja reproduzir outros arquivos de áudio (.mp3 ou .wav)? Importe diretamente do armazenamento do dispositivo.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextMuted,
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        OutlinedButton(
                            onClick = { audioPickerLauncher.launch("audio/*") },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                            border = BorderStroke(1.dp, GoldPrimary),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("btn_import_audio")
                        ) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.QueueMusic, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Selecionar Arquivo (.mp3 / .wav)")
                        }
                    }
                }
            }

            // Atalho para a Próxima Seção
            item {
                NextSectionButton(
                    sectionTitle = "Reflexões & Diário",
                    icon = Icons.Default.Explore,
                    onClick = onNavigateBlog,
                    testTag = "music_btn_next_blog"
                )
            }
        }
    }
}

@Composable
fun RotatingDiscCover(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "disc_rotation")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation_angle"
    )

    val currentRotation = if (isPlaying) rotation else 0f

    Box(
        modifier = Modifier
            .size(170.dp)
            .clip(CircleShape)
            .background(CanvasDeep)
            .border(3.dp, GoldPrimary, CircleShape)
            .border(6.dp, CanvasBorder, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon_symbol),
            contentDescription = "Vinil Rúnico do Portador",
            modifier = Modifier
                .fillMaxSize()
                .rotate(currentRotation),
            contentScale = ContentScale.Crop
        )
        // Center pin
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(GoldPrimary)
                .border(2.dp, CanvasDeep, CircleShape)
        )
    }
}

@Composable
fun WaveformVisualizer(isPlaying: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "bars_anim")
    val heights = List(16) { idx ->
        val duration = 400 + (idx % 5) * 120
        infiniteTransition.animateFloat(
            initialValue = 4f,
            targetValue = if (isPlaying) (10f + (idx * 3 % 26f)) else 6f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = duration, easing = FastOutSlowInEasing),
                repeatMode = RepeatMode.Reverse
            ),
            label = "bar_$idx"
        ).value
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        heights.forEach { h ->
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(h.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(GoldLight, GoldPrimary)
                        )
                    )
            )
        }
    }
}

@Composable
fun TrackPlaylistItem(
    track: MusicTrack,
    index: Int,
    isSelected: Boolean,
    isPlaying: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable(onClick = onClick)
            .testTag("track_item_$index"),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) GoldContainer else CanvasCard
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) GoldPrimary else CanvasBorder
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (isSelected) GoldPrimary else CanvasDeep)
                    .border(1.dp, if (isSelected) GoldLight else CanvasBorder, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isPlaying) {
                    Icon(
                        imageVector = Icons.Default.Equalizer,
                        contentDescription = "Tocando",
                        tint = CanvasDeep,
                        modifier = Modifier.size(18.dp)
                    )
                } else {
                    Text(
                        text = "$index",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) CanvasDeep else GoldLight
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) GoldLight else TextParchment
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )
                    if (track.rawResName in listOf("music_memoria1", "music_memoria2", "music_jardim", "music_batalha")) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Surface(
                            shape = RoundedCornerShape(3.dp),
                            color = GoldContainer.copy(alpha = 0.8f)
                        ) {
                            Text(
                                text = "HQ",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GoldLight,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                            )
                        }
                    }
                }
                Text(
                    text = track.actConnection,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isSelected) AmberTertiary else MistSecondary
                    )
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = track.durationFormatted,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = if (isSelected) GoldLight else TextMuted
                    )
                )
                Text(
                    text = track.mood.substringBefore(","),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = TextMuted,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}

private fun formatTime(millis: Long): String {
    val totalSeconds = (millis / 1000).coerceAtLeast(0)
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return "%02d:%02d".format(minutes, seconds)
}
