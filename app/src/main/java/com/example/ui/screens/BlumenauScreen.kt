package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.AccountBalance
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Water
import androidx.compose.material.icons.filled.WbCloudy
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
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

data class BlumenauLandmark(
    val id: String,
    val title: String,
    val roleInBook: String,
    val description: String,
    val icon: ImageVector,
    val imageRes: Int
)

val blumenauLandmarks = listOf(
    BlumenauLandmark(
        id = "catedral",
        title = "Catedral São Paulo Apóstolo",
        roleInBook = "Referência real que atravessa a narrativa",
        description = "A Catedral São Paulo Apóstolo é uma referência real de Blumenau que também atravessa a fronteira entre realidade e fantasia no romance. Sua torre surge entre as copas do Jardim, aproximando o universo cotidiano de Jônatas do mundo que ele descobriu.",
        icon = Icons.Default.AccountBalance,
        imageRes = R.drawable.blumenau_catedral
    ),
    BlumenauLandmark(
        id = "rio",
        title = "Rio Itajaí-Açu",
        roleInBook = "O rio da cidade e a presença da água na narrativa",
        description = "O Rio Itajaí-Açu faz parte da Blumenau real onde começa e termina a jornada de Jônatas. A água, a chuva e o rio também possuem forte presença simbólica na narrativa, aproximando o cotidiano da cidade das fronteiras do universo do esquecimento.",
        icon = Icons.Default.Water,
        imageRes = R.drawable.blumenau_rio
    ),
    BlumenauLandmark(
        id = "ruas",
        title = "Ruas e a Chuva",
        roleInBook = "O cotidiano onde tudo começa",
        description = "O cotidiano de Jônatas começa em uma Blumenau real, marcada pela chuva, pelas ruas molhadas e pelo ritmo silencioso da cidade. É nesse cenário aparentemente comum que surge o chamado que o conduz para além da realidade conhecida.",
        icon = Icons.Default.WbCloudy,
        imageRes = R.drawable.blumenau_ruas
    )
)

@Composable
fun BlumenauScreen(
    onBack: () -> Unit,
    onNavigateMusic: () -> Unit = {},
    onNavigateMemoryGame: () -> Unit = {},
    onNavigatePuzzleGame: () -> Unit = {},
    onSelectLandmark: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            BookTopBar(
                title = "Blumenau & O Cotidiano",
                onBack = onBack,
                shareContent = "Conheça Blumenau, a cidade real de onde parte a jornada de Jônatas em O Portador da Memória por Júlio César Rodrigues."
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
            // Hero Image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.blumenau_art),
                        contentDescription = "Catedral São Paulo Apóstolo e Blumenau",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        CanvasDeep.copy(alpha = 0.5f),
                                        CanvasDeep
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        GoldBadge(text = "Cenário Real")
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Blumenau, Santa Catarina",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextParchment
                            )
                        )
                        Text(
                            text = "O mundo real e ponto de partida da jornada",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = GoldLight
                            )
                        )
                    }
                }
            }

            // Introduction Text
            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Blumenau é o ponto de partida de Jônatas. É na cidade real, sob a chuva de uma noite aparentemente comum, que sua rotina começa a se romper. Entre ruas molhadas e uma passagem estreita entre os prédios, Jônatas atravessa a fronteira para a Floresta Oblivionis e descobre um mundo marcado pelo esquecimento, pela memória e pela esperança.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment,
                            lineHeight = 24.sp
                        )
                    )
                }
            }

            // Landmarks Header
            item {
                SectionHeader(
                    title = "Blumenau na Narrativa",
                    subtitle = "A cidade real onde começa a jornada de Jônatas",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            // Landmarks
            items(blumenauLandmarks.size) { index ->
                val landmark = blumenauLandmarks[index]
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onSelectLandmark(landmark.id) }
                        .testTag("landmark_card_$index"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, CanvasBorder)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(38.dp)
                                .clip(CircleShape)
                                .background(GoldContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = landmark.icon,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Text(
                            text = landmark.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Ver detalhes de ${landmark.title}",
                            tint = GoldDark,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }

            // Blumenau e o Autor
            item {
                Spacer(modifier = Modifier.height(14.dp))
                SectionHeader(
                    title = "Blumenau e o Autor",
                    subtitle = "A relação biográfica com a cidade natal",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .testTag("author_blumenau_card"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, CanvasBorder)
                ) {
                    Column {
                        Image(
                            painter = painterResource(id = R.drawable.blumenau_autor),
                            contentDescription = "Júlio César Rodrigues em Blumenau",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(210.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "Júlio César Rodrigues nasceu e cresceu em Blumenau. Com trajetória profissional na área de Tecnologia da Informação e longa atuação na catequese de adultos, é o autor de O Portador da Memória: Entre a Luz e a Escuridão.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = TextParchment,
                                    lineHeight = 20.sp
                                )
                            )
                        }
                    }
                }
            }

            // Atalhos para as Próximas Seções
            item {
                NextSectionButton(
                    sectionTitle = "Desafio da Memória",
                    icon = Icons.Default.Psychology,
                    onClick = onNavigateMemoryGame,
                    testTag = "blumenau_btn_next_memory_game"
                )
                Spacer(modifier = Modifier.height(10.dp))
                NextSectionButton(
                    sectionTitle = "Puzzle da Memória",
                    icon = Icons.Default.Extension,
                    onClick = onNavigatePuzzleGame,
                    testTag = "blumenau_btn_next_puzzle_game"
                )
                Spacer(modifier = Modifier.height(10.dp))
                NextSectionButton(
                    sectionTitle = "Músicas do Universo",
                    icon = Icons.Default.MusicNote,
                    onClick = onNavigateMusic,
                    testTag = "blumenau_btn_next_music"
                )
            }
        }
    }
}

@Composable
fun BlumenauLandmarkDetailScreen(
    landmarkId: String,
    onBack: () -> Unit
) {
    val landmark = blumenauLandmarks.find { it.id == landmarkId } ?: blumenauLandmarks.first()

    Scaffold(
        topBar = {
            BookTopBar(
                title = landmark.title,
                onBack = onBack,
                shareContent = "${landmark.title}\n\n${landmark.roleInBook}\n\n${landmark.description}"
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
            // Hero Image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(260.dp)
                ) {
                    Image(
                        painter = painterResource(id = landmark.imageRes),
                        contentDescription = landmark.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        CanvasDeep.copy(alpha = 0.5f),
                                        CanvasDeep
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        GoldBadge(text = "Blumenau na Narrativa")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = landmark.title,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextParchment
                            )
                        )
                    }
                }
            }

            // Subtitle & Description Card
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CanvasCard),
                        border = BorderStroke(1.dp, CanvasBorder)
                    ) {
                        Column(modifier = Modifier.padding(18.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(GoldContainer),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = landmark.icon,
                                        contentDescription = null,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = landmark.roleInBook,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = GoldLight
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                text = landmark.description,
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = TextParchment,
                                    lineHeight = 24.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}
