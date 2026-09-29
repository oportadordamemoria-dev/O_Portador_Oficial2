package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Brightness4
import androidx.compose.material.icons.filled.Park
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.data.BookUniverse
import com.example.ui.components.BookTopBar
import com.example.ui.components.LiteraryQuoteCard
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

data class WorkPillar(
    val title: String,
    val subtitle: String,
    val text: String,
    val icon: ImageVector
)

@Composable
fun WorkScreen(
    onBack: () -> Unit,
    onNavigateChapters: () -> Unit,
    onNavigateJourney: () -> Unit
) {
    val pillars = listOf(
        WorkPillar(
            title = "Memória × Esquecimento",
            subtitle = "O conflito que move a narrativa",
            text = "Na Floresta Oblivionis, o esquecimento assume uma dimensão concreta: nomes são partidos, memórias desaparecem e identidades podem ser reduzidas a fragmentos. Ao longo de sua jornada, Jônatas descobre que lembrar também pode ser uma forma de resistência.",
            icon = Icons.Default.Brightness4
        ),
        WorkPillar(
            title = "Nome × Identidade",
            subtitle = "Ser lembrado é continuar existindo",
            text = "Na história, nomes e memórias estão profundamente ligados à identidade. A Árvore dos Nomes e o Livro dos Nomes Partidos revelam que aqueles que foram esquecidos ainda carregam histórias que podem ser reconhecidas e restauradas.",
            icon = Icons.Default.Person
        ),
        WorkPillar(
            title = "Luz × Escuridão",
            subtitle = "A batalha pela memória",
            text = "A luz e a escuridão se enfrentam pelo destino daqueles que vivem entre o Jardim das Crianças e a Floresta Oblivionis. Enquanto a luz protege e restaura, as forças do esquecimento avançam para consumir aquilo que ainda permanece.",
            icon = Icons.Default.Shield
        ),
        WorkPillar(
            title = "Jardim × Floresta",
            subtitle = "Dois espaços em conflito",
            text = "O Jardim das Crianças é um refúgio protegido pela luz. Além de sua fronteira está a Floresta Oblivionis, território dominado pelo esquecimento e pelas criaturas que ameaçam aqueles que ainda lutam para preservar suas memórias.",
            icon = Icons.Default.Park
        )
    )

    val fundamentalElements = listOf(
        WorkPillar(
            title = "O Livro dos Nomes Partidos",
            subtitle = "A memória daqueles que foram esquecidos",
            text = "Escondido nas profundezas da Floresta Oblivionis, o Livro guarda fragmentos de nomes e memórias de existências que foram esquecidas. Quando Jônatas o toca, algo desperta: nomes começam a retornar e uma antiga verdade vem à luz.",
            icon = Icons.Default.Bookmark
        )
    )

    Scaffold(
        topBar = {
            BookTopBar(
                title = "A Obra",
                onBack = onBack,
                shareContent = "Conheça os fundamentos literários de O Portador da Memória: Entre a Luz e a Escuridão por Júlio César Rodrigues."
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
            // Hero Banner Image
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_cover_art),
                        contentDescription = "O Portador da Memória Universo",
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
                                        CanvasDeep.copy(alpha = 0.6f),
                                        CanvasDeep
                                    )
                                )
                            )
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Text(
                            text = "ENTRE A LUZ E A ESCURIDÃO",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = GoldPrimary,
                                letterSpacing = 1.5.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "O Universo do Volume 1",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = TextParchment,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }

            // Overview Text
            item {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Em uma noite de chuva em Blumenau, Jônatas ouve o choro de uma criança e segue seu chamado por uma estreita passagem que o conduz à Floresta Oblivionis. Ali, descobre um mundo onde nomes, memórias e identidades podem ser esquecidos — e onde aqueles que foram abandonados ainda esperam ser lembrados.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment,
                            lineHeight = 24.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Entre a Floresta, o Jardim das Crianças e as forças que disputam suas fronteiras, Jônatas descobrirá que sua jornada não é apenas um resgate. É uma batalha pela memória, pela identidade e por aquilo que ainda pode ser restaurado.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment,
                            lineHeight = 24.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    LiteraryQuoteCard(
                        quote = "A guerra não era apenas pelas crianças, nem apenas pelo Jardim, nem apenas contra o esquecimento. Era uma batalha pela memória de tudo o que existiu, de tudo o que é e de tudo o que ainda poderia ser."
                    )
                }
            }

            // Section Header
            item {
                SectionHeader(
                    title = "Pilares Filosóficos da Saga",
                    subtitle = "Os conceitos centrais que atravessam a narrativa",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            // Pillars List
            items(pillars.size) { index ->
                val pillar = pillars[index]
                PillarCard(
                    pillar = pillar,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            // Elementos fundamentais do universo
            item {
                Spacer(modifier = Modifier.height(14.dp))
                SectionHeader(
                    title = "Elementos fundamentais do universo",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )
            }

            items(fundamentalElements.size) { index ->
                val element = fundamentalElements[index]
                PillarCard(
                    pillar = element,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            // Action Buttons
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                ) {
                    Button(
                        onClick = onNavigateJourney,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("work_btn_journey"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Conhecer a Jornada de Jônatas",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedButton(
                        onClick = onNavigateChapters,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("work_btn_chapters"),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GoldLight
                        ),
                        border = BorderStroke(1.dp, GoldDark),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = "Explorar os 32 Capítulos",
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PillarCard(
    pillar: WorkPillar,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(GoldContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = pillar.icon,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = pillar.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                    Text(
                        text = pillar.subtitle,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MistSecondary
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = pillar.text,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextParchment,
                    lineHeight = 22.sp
                )
            )
        }
    }
}
