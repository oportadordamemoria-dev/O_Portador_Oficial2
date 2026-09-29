package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BookLocation
import com.example.data.BookUniverse
import com.example.ui.components.BookTopBar
import com.example.ui.components.GoldBadge
import com.example.ui.components.NextSectionButton
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractiveMapScreen(
    onBack: () -> Unit,
    onSelectLocation: (String) -> Unit,
    onNavigateChapters: () -> Unit = {}
) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var selectedLocation by remember { mutableStateOf<BookLocation?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val transformState = rememberTransformableState { zoomChange, panChange, _ ->
        scale = (scale * zoomChange).coerceIn(1f, 3.5f)
        val maxOffset = 300f * (scale - 1f)
        offset = Offset(
            x = (offset.x + panChange.x).coerceIn(-maxOffset, maxOffset),
            y = (offset.y + panChange.y).coerceIn(-maxOffset, maxOffset)
        )
    }

    Scaffold(
        topBar = {
            BookTopBar(
                title = "Mapa do Universo (Volume 1)",
                onBack = onBack,
                shareContent = "Explore o mapa dos 8 cenários canônicos de O Portador da Memória: Entre a Luz e a Escuridão."
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Legend Quick Filter
            Surface(
                color = CanvasCard,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(
                        text = "Toque em um local para posicionar:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = TextMuted,
                            fontSize = 11.sp
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
                    )
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)
                    ) {
                        items(BookUniverse.locations.size) { index ->
                            val loc = BookUniverse.locations[index]
                            Surface(
                                shape = RoundedCornerShape(16.dp),
                                color = if (selectedLocation?.id == loc.id) GoldContainer else CanvasDeep,
                                border = BorderStroke(1.dp, if (selectedLocation?.id == loc.id) GoldPrimary else CanvasBorder),
                                modifier = Modifier
                                    .clickable {
                                        selectedLocation = loc
                                    }
                                    .testTag("map_filter_${loc.id}")
                            ) {
                                Text(
                                    text = loc.name,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (selectedLocation?.id == loc.id) GoldLight else TextParchment,
                                        fontWeight = FontWeight.Medium
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Interactive Map Canvas Area
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clip(RoundedCornerShape(0.dp))
                    .background(Color(0xFF101216))
            ) {
                val boxWidth = maxWidth
                val boxHeight = maxHeight

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .graphicsLayer(
                            scaleX = scale,
                            scaleY = scale,
                            translationX = offset.x,
                            translationY = offset.y
                        )
                        .transformable(state = transformState)
                ) {
                    // Map Background Artwork
                    Image(
                        painter = painterResource(id = R.drawable.fantasy_map_art),
                        contentDescription = "Mapa Antigo de O Portador da Memória",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // Overlay pins for each canonical location
                    BookUniverse.locations.forEach { loc ->
                        val posX = (loc.mapCoordinatesX * boxWidth.value).dp
                        val posY = (loc.mapCoordinatesY * boxHeight.value).dp

                        Box(
                            modifier = Modifier
                                .offset {
                                    IntOffset(
                                        x = (loc.mapCoordinatesX * (boxWidth.toPx() - 36.dp.toPx())).roundToInt(),
                                        y = (loc.mapCoordinatesY * (boxHeight.toPx() - 36.dp.toPx())).roundToInt()
                                    )
                                }
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(GoldContainer.copy(alpha = 0.9f))
                                .border(1.5.dp, GoldPrimary, CircleShape)
                                .clickable {
                                    selectedLocation = loc
                                }
                                .testTag("map_pin_${loc.id}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Place,
                                contentDescription = loc.name,
                                tint = GoldLight,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                }

                // Controls overlay (Zoom in, Zoom out, Reset)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FloatingActionButton(
                        onClick = { scale = (scale + 0.5f).coerceAtMost(3.5f) },
                        containerColor = CanvasCard,
                        contentColor = GoldLight,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("map_btn_zoom_in")
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Aumentar zoom")
                    }
                    FloatingActionButton(
                        onClick = { scale = (scale - 0.5f).coerceAtLeast(1f) },
                        containerColor = CanvasCard,
                        contentColor = GoldLight,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("map_btn_zoom_out")
                    ) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Diminuir zoom")
                    }
                    FloatingActionButton(
                        onClick = {
                            scale = 1f
                            offset = Offset.Zero
                        },
                        containerColor = CanvasCard,
                        contentColor = GoldPrimary,
                        modifier = Modifier
                            .size(40.dp)
                            .testTag("map_btn_reset")
                    ) {
                        Icon(imageVector = Icons.Default.RestartAlt, contentDescription = "Redefinir mapa")
                    }
                }
            }

            // Atalho para a Próxima Seção
            NextSectionButton(
                sectionTitle = "Os 5 Atos & 32 Capítulos",
                icon = Icons.Default.AutoStories,
                onClick = onNavigateChapters,
                testTag = "map_btn_next_chapters"
            )
        }

        // Bottom Sheet for Selected Location
        if (selectedLocation != null) {
            val loc = selectedLocation!!
            ModalBottomSheet(
                onDismissRequest = { selectedLocation = null },
                sheetState = sheetState,
                containerColor = CanvasSurface
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp)
                        .padding(bottom = 24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            val subtitle = if (loc.subpageSubtitle.isNotEmpty()) loc.subpageSubtitle else loc.realmType
                            GoldBadge(text = subtitle)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = loc.name,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = GoldLight
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = loc.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextParchment,
                            lineHeight = 20.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val id = loc.id
                            selectedLocation = null
                            onSelectLocation(id)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("map_sheet_btn_details"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(text = "Ver Crônica Completa do Local", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
