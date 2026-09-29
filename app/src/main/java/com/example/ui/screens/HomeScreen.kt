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
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Extension
import androidx.compose.material.icons.filled.Landscape
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import android.media.MediaPlayer
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.BookUniverse
import com.example.ui.components.LiteraryQuoteCard
import com.example.ui.components.SectionHeader
import com.example.ui.components.shareText
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

data class HubCategory(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val route: String,
    val testTag: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {
    val context = LocalContext.current

    val categories = listOf(
        HubCategory(
            title = "A Obra",
            subtitle = "O universo literário, princípios e a dualidade de luz e sombra",
            icon = Icons.AutoMirrored.Filled.MenuBook,
            route = "work",
            testTag = "nav_card_work"
        ),
        HubCategory(
            title = "A Jornada de Jônatas",
            subtitle = "As 6 etapas da travessia entre o cotidiano e a memória",
            icon = Icons.AutoMirrored.Filled.TrendingUp,
            route = "journey",
            testTag = "nav_card_journey"
        ),
        HubCategory(
            title = "Personagens",
            subtitle = "Jônatas, Rafael, Malach, Miriam e as presenças canônicas",
            icon = Icons.Default.Person,
            route = "characters",
            testTag = "nav_card_characters"
        ),
        HubCategory(
            title = "Lugares do Universo",
            subtitle = "Floresta Oblivionis, Jardim das Crianças e os limites sagrados",
            icon = Icons.Default.Landscape,
            route = "locations",
            testTag = "nav_card_locations"
        ),
        HubCategory(
            title = "Mapa do Universo",
            subtitle = "Cartografia mística interativa com visualização dos pontos-chave",
            icon = Icons.Default.Map,
            route = "map",
            testTag = "nav_card_map"
        ),
        HubCategory(
            title = "Os 5 Atos & 32 Capítulos",
            subtitle = "Exploração completa dos atos, sinopses e estrutura literária",
            icon = Icons.Default.AutoStories,
            route = "chapters",
            testTag = "nav_card_chapters"
        ),
        HubCategory(
            title = "Ler o Livro",
            subtitle = "Leitura canônica integral dos primeiros capítulos da obra oficial",
            icon = Icons.Default.Book,
            route = "reading_list",
            testTag = "nav_card_reading"
        ),
        HubCategory(
            title = "Pergunte à Memória",
            subtitle = "Um guia inteligente pelo universo de O Portador da Memória",
            icon = Icons.Default.AutoStories,
            route = "ask_memory",
            testTag = "nav_card_ask_memory"
        ),
        HubCategory(
            title = "Blumenau",
            subtitle = "A cidade real de Santa Catarina onde o extraordinário começa",
            icon = Icons.Default.LocationCity,
            route = "blumenau",
            testTag = "nav_card_blumenau"
        ),
        HubCategory(
            title = "Desafio da Memória",
            subtitle = "“Quanto você consegue lembrar antes que tudo seja esquecido?”",
            icon = Icons.Default.Psychology,
            route = "memory_game",
            testTag = "nav_card_memory_game"
        ),
        HubCategory(
            title = "Puzzle da Memória",
            subtitle = "“Reconstrua as imagens. Revele as memórias.”",
            icon = Icons.Default.Extension,
            route = "puzzle_game",
            testTag = "nav_card_puzzle_game"
        ),
        HubCategory(
            title = "Músicas do Universo",
            subtitle = "4 temas instrumentais originais com reprodutor integrado",
            icon = Icons.Default.MusicNote,
            route = "music",
            testTag = "nav_card_music"
        ),
        HubCategory(
            title = "Reflexões & Diário",
            subtitle = "Ensaios e anotações do autor sobre memória e esquecimento",
            icon = Icons.Default.Explore,
            route = "blog",
            testTag = "nav_card_blog"
        ),
        HubCategory(
            title = "O Autor",
            subtitle = "Júlio César Rodrigues, apresentação oficial e UICLAP",
            icon = Icons.Default.PersonOutline,
            route = "author",
            testTag = "nav_card_author"
        )
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isScrolledToPageTwo by remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }

    // Intro cover fade-in animation (3.5 seconds gradual reveal)
    val coverAlpha = remember { Animatable(0f) }
    val buttonAlpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        launch {
            coverAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 3500, easing = LinearOutSlowInEasing)
            )
        }
        launch {
            delay(1200)
            buttonAlpha.animateTo(
                targetValue = 1f,
                animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
            )
        }
    }

    // Intro music: Plays track 1 ("O Chamado do Guardião" / music_memoria1) from app start
    // until the track ends OR the user executes any action (tap, scroll, button click, navigation)
    val introPlayer = remember { mutableStateOf<MediaPlayer?>(null) }
    val hasInteracted = remember { mutableStateOf(false) }

    val stopIntroMusic: () -> Unit = remember {
        {
            if (!hasInteracted.value) {
                hasInteracted.value = true
                try {
                    introPlayer.value?.let { player ->
                        if (player.isPlaying) {
                            player.stop()
                        }
                        player.release()
                    }
                } catch (e: Exception) {
                    // Ignore
                }
                introPlayer.value = null
            }
        }
    }

    // Navigation wrapper to ensure intro music halts upon any section navigation
    val handleNavigate: (String) -> Unit = remember(onNavigate) {
        { route ->
            stopIntroMusic()
            onNavigate(route)
        }
    }

    // Release player when leaving HomeScreen
    DisposableEffect(Unit) {
        onDispose {
            stopIntroMusic()
        }
    }

    // Start playing track 1 automatically on initial app launch
    LaunchedEffect(Unit) {
        if (!hasInteracted.value) {
            try {
                val player = MediaPlayer.create(context, R.raw.music_memoria1)
                if (player != null) {
                    if (hasInteracted.value) {
                        player.release()
                    } else {
                        introPlayer.value = player
                        player.isLooping = false
                        player.setOnCompletionListener {
                            stopIntroMusic()
                        }
                        player.start()
                    }
                }
            } catch (e: Exception) {
                // Audio fallback
            }
        }
    }

    // Interruption on scroll action
    LaunchedEffect(listState.isScrollInProgress) {
        if (listState.isScrollInProgress) {
            stopIntroMusic()
        }
    }

    Scaffold(
        topBar = {
            if (isScrolledToPageTwo) {
                TopAppBar(
                    title = {
                        Text(
                            text = BookUniverse.BOOK_TITLE,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = GoldLight
                            )
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                stopIntroMusic()
                                shareText(
                                    context = context,
                                    title = "${BookUniverse.BOOK_TITLE}: ${BookUniverse.BOOK_SUBTITLE}",
                                    content = "${BookUniverse.CENTRAL_THEME_QUOTE}\n\nConheça o universo literário de ${BookUniverse.AUTHOR_NAME}."
                                )
                            },
                            modifier = Modifier.testTag("home_share_button")
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Compartilhar",
                                tint = GoldPrimary
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = CanvasDeep,
                        titleContentColor = TextParchment
                    )
                )
            }
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Página 1: Capa Inicial em Tela Cheia (apenas a imagem e o botão "Entrar no Universo" na parte inferior)
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .background(CanvasDeep)
                        .pointerInput(Unit) {
                            awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent(PointerEventPass.Initial)
                                    if (event.changes.any { it.pressed }) {
                                        stopIntroMusic()
                                    }
                                }
                            }
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_cover_art),
                        contentDescription = "Capa oficial de O Portador da Memória",
                        modifier = Modifier
                            .fillMaxSize()
                            .graphicsLayer { alpha = coverAlpha.value },
                        contentScale = ContentScale.Crop
                    )

                    // Sutil gradiente na base para destacar com elegância o botão de entrada
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .align(Alignment.BottomCenter)
                            .graphicsLayer { alpha = buttonAlpha.value }
                            .background(
                                Brush.verticalGradient(
                                    listOf(
                                        Color.Transparent,
                                        CanvasDeep.copy(alpha = 0.7f),
                                        CanvasDeep.copy(alpha = 0.95f)
                                    )
                                )
                            )
                    )

                    // Único elemento na tela de início: botão "Entrar no Universo" bem na parte inferior
                    Button(
                        onClick = {
                            stopIntroMusic()
                            coroutineScope.launch {
                                listState.animateScrollToItem(1)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter)
                            .padding(horizontal = 24.dp, vertical = 28.dp)
                            .height(56.dp)
                            .graphicsLayer { alpha = buttonAlpha.value }
                            .testTag("btn_entrar_universo"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(28.dp),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = "Entrar no Universo",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }
            }

            // Página 2: Início do Conteúdo do Universo
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 8.dp)
                ) {
                    Text(
                        text = BookUniverse.BOOK_TITLE.uppercase(),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 2.sp,
                            color = GoldLight
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = BookUniverse.BOOK_SUBTITLE,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic,
                            color = TextParchment
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Por ${BookUniverse.AUTHOR_NAME}",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = GoldPrimary,
                            fontWeight = FontWeight.Medium,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }

            // Spotlight: Apresentação da Obra & Sinopse Oficial (MainScreen)
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { handleNavigate("main_screen") }
                        .testTag("home_main_screen_banner"),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = GoldContainer.copy(alpha = 0.7f)),
                    border = BorderStroke(1.dp, GoldPrimary.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .background(GoldPrimary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.MenuBook,
                                contentDescription = null,
                                tint = CanvasDeep,
                                modifier = Modifier.size(26.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "O LIVRO & SINOPSIS OFICIAL",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GoldLight,
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.2.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Apresentação de O Portador da Memória",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    color = TextParchment,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "Título monumental, sinopse completa e pilares literários",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = TextMuted
                                )
                            )
                        }
                    }
                }
            }

            // Literary Theme Quote Card
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    LiteraryQuoteCard(
                        quote = BookUniverse.CENTRAL_THEME_QUOTE,
                        author = BookUniverse.BOOK_TITLE
                    )
                }
            }

            // Quick Stats / Universe Summary
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MiniBadgeInfo(
                        label = "Volume 1",
                        value = "Universo Canônico",
                        modifier = Modifier.weight(1f)
                    )
                    MiniBadgeInfo(
                        label = "5 Atos",
                        value = "32 Capítulos",
                        modifier = Modifier.weight(1f)
                    )
                    MiniBadgeInfo(
                        label = "Cenário Real",
                        value = "Blumenau / SC",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Section Header
            item {
                SectionHeader(
                    title = "Portal do Universo",
                    subtitle = "Navegue pelas áreas fundamentais da obra literária",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }

            // Navigation Hub Cards
            items(categories.size) { index ->
                val category = categories[index]
                HomeNavCard(
                    category = category,
                    onClick = { handleNavigate(category.route) },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }
        }
    }
}

@Composable
fun MiniBadgeInfo(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = GoldLight,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontSize = 10.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun HomeNavCard(
    category: HubCategory,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag(category.testTag),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(GoldContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = category.icon,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = TextParchment
                    )
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = category.subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        lineHeight = 18.sp
                    )
                )
            }
        }
    }
}
