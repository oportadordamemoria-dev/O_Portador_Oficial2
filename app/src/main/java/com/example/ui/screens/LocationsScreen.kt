package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BookLocation
import com.example.data.BookUniverse
import com.example.ui.components.BookTopBar
import com.example.ui.components.GoldBadge
import com.example.ui.components.NextSectionButton
import com.example.ui.components.SectionHeader
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
fun LocationsScreen(
    onBack: () -> Unit,
    onSelectLocation: (String) -> Unit,
    onNavigateMap: () -> Unit
) {
    Scaffold(
        topBar = {
            BookTopBar(
                title = "Lugares do Universo",
                onBack = onBack,
                shareContent = "Descubra os 8 cenários canônicos de O Portador da Memória: Entre a Luz e a Escuridão."
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SectionHeader(
                        title = "Cenários do Volume 1",
                        subtitle = "Lugares que atravessam a jornada entre Blumenau, o Jardim e a Floresta",
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = onNavigateMap,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldContainer,
                            contentColor = GoldLight
                        ),
                        border = BorderStroke(1.dp, GoldDark),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.testTag("btn_open_interactive_map")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = GoldPrimary
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Ver Mapa", fontSize = 12.sp)
                    }
                }
            }

            items(BookUniverse.locations.size) { index ->
                val loc = BookUniverse.locations[index]
                LocationItemCard(
                    location = loc,
                    onClick = { onSelectLocation(loc.id) },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            // Atalho para a Próxima Seção
            item {
                NextSectionButton(
                    sectionTitle = "Mapa do Universo",
                    icon = Icons.Default.Map,
                    onClick = onNavigateMap,
                    testTag = "locations_btn_next_map"
                )
            }
        }
    }
}

@Composable
fun LocationItemCard(
    location: BookLocation,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("location_card_${location.id}"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Column {
            if (location.imageRes != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                ) {
                    Image(
                        painter = painterResource(id = location.imageRes),
                        contentDescription = location.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(Color.Transparent, CanvasCard)
                                )
                            )
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = location.name,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = location.realmType,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MistSecondary
                        )
                    )
                }
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Ver detalhes",
                    tint = GoldDark,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LocationDetailScreen(
    locationId: String,
    onBack: () -> Unit,
    onNavigateMap: () -> Unit
) {
    val location = BookUniverse.locations.find { 
        it.id == locationId || (locationId == "arvore_nao_chamados" && it.id == "arvore_nunca_chamados") 
    } ?: BookUniverse.locations.first()

    val subtitle = if (location.subpageSubtitle.isNotEmpty()) location.subpageSubtitle else location.realmType

    Scaffold(
        topBar = {
            BookTopBar(
                title = location.name,
                onBack = onBack,
                shareContent = "${location.name} — $subtitle\n\n${location.description}"
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                        .background(CanvasDeep),
                    contentAlignment = Alignment.Center
                ) {
                    if (location.imageRes != null) {
                        Image(
                            painter = painterResource(id = location.imageRes),
                            contentDescription = location.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(GoldContainer)
                                .border(1.dp, GoldDark, RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Landscape,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(54.dp)
                            )
                        }
                    }
                }
            }

            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    GoldBadge(text = subtitle)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = location.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MistSecondary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    SectionHeader(
                        title = "Crônica do Local",
                        subtitle = "Papel e presença na narrativa do Volume 1"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = location.description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = TextParchment,
                            lineHeight = 22.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Elementos e Características",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        location.keyFeatures.forEach { feature ->
                            GoldBadge(text = feature, isSecondary = true)
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = onNavigateMap,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("location_detail_btn_map"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Map, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Localizar no Mapa Interativo",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
