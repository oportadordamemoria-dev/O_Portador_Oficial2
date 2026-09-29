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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BrightnessMedium
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BookUniverse
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.CanvasSurfaceVariant
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistSecondary
import com.example.ui.theme.OnGoldContainer
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment
import com.example.ui.theme.TextSubtle

/**
 * MainScreen Composable:
 * Exibe o título monumental da obra literária e uma sinopse enriquecida,
 * empregando a tipografia do Material 3 com hierarquia visual elegante,
 * detalhes ornamentais de inspiração editorial e estética escura com toques dourados.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onExploreUniverse: () -> Unit = {},
    onListenMusic: () -> Unit = {}
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = BookUniverse.BOOK_TITLE,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldLight,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            shareBookOverview(context)
                        },
                        modifier = Modifier.testTag("main_screen_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartilhar obra",
                            tint = GoldPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CanvasDeep,
                    titleContentColor = TextParchment
                )
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .testTag("main_screen_scroll_column"),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Hero Visual Canvas com Capa Artística e Gradiente Atmosférico
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(290.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.hero_cover_art),
                    contentDescription = "Capa oficial de ${BookUniverse.BOOK_TITLE}",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                // Overlay em degradê do profundo ao transparente para legibilidade
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    CanvasDeep.copy(alpha = 0.35f),
                                    CanvasDeep.copy(alpha = 0.70f),
                                    CanvasDeep
                                )
                            )
                        )
                )

                // Etiqueta literária e identidade no topo do Hero
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = GoldContainer.copy(alpha = 0.85f),
                        border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.4f)),
                        modifier = Modifier.padding(bottom = 12.dp)
                    ) {
                        Text(
                            text = "UNIVERSO LITERÁRIO CANÔNICO",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnGoldContainer,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp
                            ),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                        )
                    }

                    // TÍTULO DO LIVRO (Material 3 displayMedium/headlineLarge)
                    Text(
                        text = BookUniverse.BOOK_TITLE,
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp,
                            lineHeight = 36.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("main_screen_book_title")
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    // SUBTÍTULO DO LIVRO (Material 3 titleLarge itálico)
                    Text(
                        text = BookUniverse.BOOK_SUBTITLE,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = TextParchment,
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Normal,
                            letterSpacing = 0.5.sp
                        ),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.testTag("main_screen_book_subtitle")
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // AUTOR (Material 3 labelLarge)
                    Text(
                        text = "Por ${BookUniverse.AUTHOR_NAME} • ${BookUniverse.AUTHOR_LOCATION}",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MistSecondary,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 0.8.sp
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Divisor ornamental sutil
            OrnamentalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            // Card Editorial: BREVE SINOPSIS DO LIVRO
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .testTag("main_screen_synopsis_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CanvasCard
                ),
                border = BorderStroke(1.dp, CanvasBorder)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = GoldContainer,
                            border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = null,
                                    tint = GoldLight,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }

                        Column {
                            Text(
                                text = "SINOPSIS OFICIAL",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = GoldPrimary,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.5.sp
                                )
                            )
                            Text(
                                text = "A História",
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = TextParchment,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    HorizontalDivider(
                        color = CanvasBorder.copy(alpha = 0.6f),
                        thickness = 1.dp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Primeiro parágrafo da sinopse
                    Text(
                        text = "Em uma noite de chuva em Blumenau, Jônatas ouve o choro de uma criança e segue seu chamado por uma estreita passagem que o conduz à Floresta Oblivionis, um lugar onde vivem aqueles que foram esquecidos.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment,
                            lineHeight = 26.sp
                        ),
                        modifier = Modifier.testTag("main_screen_synopsis_text_1")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Segundo parágrafo da sinopse
                    Text(
                        text = "Guiado por Rafael, ele descobre o Jardim das Crianças e uma ameaça que cresce entre os dois mundos. Enquanto memórias são consumidas e nomes desaparecem, Jônatas encontra o Livro dos Nomes Partidos, a Árvore dos Nomes e os que ainda lutam para não serem apagados.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment.copy(alpha = 0.95f),
                            lineHeight = 26.sp
                        ),
                        modifier = Modifier.testTag("main_screen_synopsis_text_2")
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Terceiro parágrafo da sinopse
                    Text(
                        text = "À medida que sua própria história se entrelaça com a daqueles que encontrou, Jônatas precisa enfrentar o Esquecimento e decidir se a única resposta é destruir — ou se aquilo que foi perdido ainda pode ser lembrado e curado.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment.copy(alpha = 0.95f),
                            lineHeight = 26.sp
                        ),
                        modifier = Modifier.testTag("main_screen_synopsis_text_3")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // Quarto parágrafo em Negrito
                    Text(
                        text = "Uma jornada sobre memória, identidade, esperança e o valor de não abandonar aqueles que o mundo esqueceu.",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 26.sp
                        ),
                        modifier = Modifier.testTag("main_screen_synopsis_text_4")
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Cartão de Citação Filosófica / Tema Central
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .testTag("main_screen_quote_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = CanvasSurface
                ),
                border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.35f))
            ) {
                Row(
                    modifier = Modifier.padding(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    Icon(
                        imageVector = Icons.Default.FormatQuote,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(32.dp)
                    )

                    Column {
                        Text(
                            text = "\"${BookUniverse.CENTRAL_THEME_QUOTE}\"",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldLight,
                                fontStyle = FontStyle.Italic,
                                lineHeight = 24.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "— Júlio César Rodrigues, O Portador da Memória",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = TextMuted,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pilares Temáticos em Pílulas Visuais
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ThematicPillarChip(
                    title = "5 Atos",
                    subtitle = "32 Capítulos",
                    modifier = Modifier.weight(1f)
                )
                ThematicPillarChip(
                    title = "Dualidade",
                    subtitle = "Luz × Sombra",
                    modifier = Modifier.weight(1f)
                )
                ThematicPillarChip(
                    title = "Música",
                    subtitle = "4 Temas Oficiais",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Ações Principais do Usuário
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = onExploreUniverse,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("main_screen_explore_button"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = CanvasDeep
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.MenuBook,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Explorar Todo o Universo",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }

                OutlinedButton(
                    onClick = onListenMusic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("main_screen_music_button"),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f)),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = GoldLight
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.MusicNote,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Ouvir Trilha Sonora",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(36.dp))
        }
    }
}

/**
 * Pílula temática estilizada para os pilares do livro.
 */
@Composable
private fun ThematicPillarChip(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = CanvasSurfaceVariant,
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall.copy(
                    color = GoldLight,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontSize = 11.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Divisor com estética editorial e adorno central dourado.
 */
@Composable
private fun OrnamentalDivider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = CanvasBorder,
            thickness = 1.dp
        )
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .size(8.dp)
                .clip(CircleShape)
                .background(GoldPrimary)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = CanvasBorder,
            thickness = 1.dp
        )
    }
}

/**
 * Utilitário de compartilhamento do livro.
 */
private fun shareBookOverview(context: android.content.Context) {
    val sendIntent = android.content.Intent().apply {
        action = android.content.Intent.ACTION_SEND
        putExtra(
            android.content.Intent.EXTRA_TITLE,
            "${BookUniverse.BOOK_TITLE}: ${BookUniverse.BOOK_SUBTITLE}"
        )
        putExtra(
            android.content.Intent.EXTRA_TEXT,
            "${BookUniverse.BOOK_TITLE}: ${BookUniverse.BOOK_SUBTITLE}\nPor ${BookUniverse.AUTHOR_NAME}\n\n\"${BookUniverse.CENTRAL_THEME_QUOTE}\"\n\nConheça a sinopse e o universo literário completo no aplicativo oficial!"
        )
        type = "text/plain"
    }
    context.startActivity(android.content.Intent.createChooser(sendIntent, "Compartilhar O Portador da Memória"))
}
