package com.example.ui.screens

import android.content.Context
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.FormatSize
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookReadingRepository
import com.example.data.ChapterReading
import com.example.ui.components.shareText
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

private const val PREFS_READING = "reading_preferences"
private const val KEY_FONT_SIZE = "reader_font_size_sp"
private const val DEFAULT_FONT_SIZE = 17.5f

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookReaderScreen(
    chapterNumber: Int,
    onBack: () -> Unit,
    onNavigateChapter: (Int) -> Unit,
    onNavigateList: () -> Unit
) {
    val context = LocalContext.current
    val sharedPrefs = remember { context.getSharedPreferences(PREFS_READING, Context.MODE_PRIVATE) }

    // Carrega o capítulo localmente dos assets
    val chapter: ChapterReading? = remember(chapterNumber) {
        BookReadingRepository.loadChapter(context, chapterNumber)
    }

    // Controle de tamanho de fonte (afeta apenas a tela de leitura)
    var fontSize by remember {
        mutableFloatStateOf(sharedPrefs.getFloat(KEY_FONT_SIZE, DEFAULT_FONT_SIZE))
    }

    var showControls by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()

    // Restaura progresso de leitura
    LaunchedEffect(chapterNumber) {
        val savedIndex = sharedPrefs.getInt("ch_${chapterNumber}_index", 0)
        val savedOffset = sharedPrefs.getInt("ch_${chapterNumber}_offset", 0)
        if (savedIndex > 0 || savedOffset > 0) {
            listState.scrollToItem(savedIndex, savedOffset)
        }
    }

    // Salva progresso ao sair
    DisposableEffect(chapterNumber) {
        onDispose {
            sharedPrefs.edit()
                .putInt("ch_${chapterNumber}_index", listState.firstVisibleItemIndex)
                .putInt("ch_${chapterNumber}_offset", listState.firstVisibleItemScrollOffset)
                .putFloat(KEY_FONT_SIZE, fontSize)
                .apply()
        }
    }

    // Quebra do texto canônico em parágrafos literais
    val paragraphs = remember(chapter?.content) {
        chapter?.content
            ?.split(Regex("\r?\n\r?\n"))
            ?.map { it.trim() }
            ?.filter { it.isNotEmpty() }
            ?: emptyList()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Capítulo $chapterNumber",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("reader_back_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = GoldLight
                        )
                    }
                },
                actions = {
                    // Botão para exibir controles de tipografia
                    IconButton(
                        onClick = { showControls = !showControls },
                        modifier = Modifier.testTag("reader_toggle_font_controls")
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatSize,
                            contentDescription = "Ajustar tipografia",
                            tint = if (showControls) GoldPrimary else GoldLight
                        )
                    }

                    // Botão de compartilhamento canônico (Regra 15)
                    IconButton(
                        onClick = {
                            val title = chapter?.title ?: "Capítulo $chapterNumber"
                            shareText(
                                context = context,
                                title = "O Portador da Memória — Capítulo $chapterNumber: $title",
                                content = "Estou lendo o Capítulo $chapterNumber ($title) do livro O Portador da Memória: Entre a Luz e a Escuridão, de Júlio César Rodrigues."
                            )
                        },
                        modifier = Modifier.testTag("reader_share_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "Compartilhar",
                            tint = GoldLight
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CanvasDeep
                )
            )
        },
        containerColor = CanvasDeep
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Barra de controles de leitura expansível
            if (showControls) {
                ReadingControlsBar(
                    fontSize = fontSize,
                    onIncrease = {
                        if (fontSize < 26f) {
                            fontSize += 1.5f
                            sharedPrefs.edit().putFloat(KEY_FONT_SIZE, fontSize).apply()
                        }
                    },
                    onDecrease = {
                        if (fontSize > 13f) {
                            fontSize -= 1.5f
                            sharedPrefs.edit().putFloat(KEY_FONT_SIZE, fontSize).apply()
                        }
                    },
                    onReset = {
                        fontSize = DEFAULT_FONT_SIZE
                        sharedPrefs.edit().putFloat(KEY_FONT_SIZE, fontSize).apply()
                    }
                )
            }

            if (chapter == null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Capítulo não encontrado.",
                        color = TextMuted,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                // Conteúdo de leitura contínuo - Sem cards para parágrafos
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .testTag("reader_content_list"),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 28.dp)
                ) {
                    // Cabeçalho canônico do capítulo
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "CAPÍTULO $chapterNumber",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = GoldPrimary,
                                    letterSpacing = 3.sp,
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = chapter.title,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    color = GoldLight,
                                    fontFamily = FontFamily.Serif,
                                    fontWeight = FontWeight.Bold,
                                    lineHeight = 32.sp
                                ),
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(2.dp)
                                    .background(GoldDark)
                            )
                        }
                    }

                    // Parágrafos integrais e originais do livro (texto fluido de literatura)
                    itemsIndexed(paragraphs) { _, paragraph ->
                        Text(
                            text = paragraph,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = FontFamily.Serif,
                                fontSize = fontSize.sp,
                                lineHeight = (fontSize * 1.68f).sp,
                                color = TextParchment,
                                fontWeight = FontWeight.Normal
                            ),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 18.dp)
                        )
                    }

                    // Navegação de fim de capítulo (Regra 14)
                    item {
                        ChapterEndNavigation(
                            currentChapter = chapterNumber,
                            onNavigateChapter = onNavigateChapter,
                            onNavigateList = onNavigateList
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ReadingControlsBar(
    fontSize: Float,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onReset: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = CanvasSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tamanho do texto",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextMuted
                )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onDecrease,
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("btn_font_decrease"),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder)
                ) {
                    Text(
                        text = "A-",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                OutlinedButton(
                    onClick = onReset,
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("btn_font_reset"),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder)
                ) {
                    Text(
                        text = "A",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                OutlinedButton(
                    onClick = onIncrease,
                    modifier = Modifier
                        .size(38.dp)
                        .testTag("btn_font_increase"),
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder)
                ) {
                    Text(
                        text = "A+",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun ChapterEndNavigation(
    currentChapter: Int,
    onNavigateChapter: (Int) -> Unit,
    onNavigateList: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp, bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .width(48.dp)
                .height(1.dp)
                .background(CanvasBorder)
        )
        Spacer(modifier = Modifier.height(28.dp))

        when (currentChapter) {
            1 -> {
                // No final do Capítulo 1: "Próximo capítulo"
                Button(
                    onClick = { onNavigateChapter(2) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                        .testTag("btn_next_chapter"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = CanvasDeep
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Próximo capítulo",
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            2 -> {
                // No final do Capítulo 2: "Capítulo anterior" e "Próximo capítulo"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigateChapter(1) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("btn_prev_chapter"),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GoldLight
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Capítulo anterior",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Button(
                        onClick = { onNavigateChapter(3) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("btn_next_chapter"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = "Próximo capítulo",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }
            3 -> {
                // No final do Capítulo 3: "Capítulo anterior" e "Voltar para Leitura"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { onNavigateChapter(2) },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("btn_prev_chapter"),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = GoldLight
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CanvasBorder),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Capítulo anterior",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                    }

                    Button(
                        onClick = onNavigateList,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("btn_back_to_reading"),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.List,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Voltar para Leitura",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
            else -> {
                // Suporte genérico para futuros capítulos 4 a 32
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (currentChapter > 1) {
                        OutlinedButton(
                            onClick = { onNavigateChapter(currentChapter - 1) },
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text("Capítulo anterior")
                        }
                    }
                    Button(
                        onClick = onNavigateList,
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Voltar para Leitura")
                    }
                }
            }
        }
    }
}
