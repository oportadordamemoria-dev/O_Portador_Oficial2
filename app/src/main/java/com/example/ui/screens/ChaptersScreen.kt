package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.BookChapter
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
fun ChaptersScreen(
    onBack: () -> Unit,
    onSelectChapter: (Int) -> Unit,
    onNavigateReadingList: () -> Unit = {}
) {
    var selectedAct by remember { mutableIntStateOf(0) } // 0 = Todos
    var searchQuery by remember { mutableStateOf("") }

    val actFilters = listOf("Todos", "Ato I", "Ato II", "Ato III", "Ato IV", "Ato V")

    val filteredChapters = BookUniverse.chapters.filter { chapter ->
        val matchesAct = if (selectedAct == 0) true else chapter.actNumber == selectedAct
        val matchesSearch = if (searchQuery.isBlank()) true else {
            chapter.title.contains(searchQuery, ignoreCase = true) ||
            chapter.synopsis.contains(searchQuery, ignoreCase = true) ||
            chapter.keywords.any { it.contains(searchQuery, ignoreCase = true) }
        }
        matchesAct && matchesSearch
    }

    Scaffold(
        topBar = {
            BookTopBar(
                title = "Os 5 Atos & 32 Capítulos",
                onBack = onBack,
                shareContent = "Navegue pelos 32 capítulos divididos nos 5 atos de O Portador da Memória: Entre a Luz e a Escuridão."
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Search Input
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("chapter_search_input"),
                placeholder = { Text("Buscar capítulo, palavra-chave...", color = TextMuted, fontSize = 14.sp) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = null, tint = GoldPrimary)
                },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = GoldPrimary,
                    unfocusedBorderColor = CanvasBorder,
                    focusedTextColor = TextParchment,
                    unfocusedTextColor = TextParchment,
                    focusedContainerColor = CanvasCard,
                    unfocusedContainerColor = CanvasCard
                ),
                shape = RoundedCornerShape(10.dp)
            )

            // Act Filter Tabs
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(horizontal = 16.dp)
            ) {
                items(actFilters.size) { index ->
                    val isSelected = selectedAct == index
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = if (isSelected) GoldContainer else CanvasCard,
                        border = BorderStroke(1.dp, if (isSelected) GoldPrimary else CanvasBorder),
                        modifier = Modifier
                            .clickable { selectedAct = index }
                            .testTag("act_tab_$index")
                    ) {
                        Text(
                            text = actFilters[index],
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) GoldLight else TextMuted
                            ),
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                        )
                    }
                }
            }

            // Chapter Count Summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredChapters.size} capítulos encontrados",
                    style = MaterialTheme.typography.labelSmall.copy(color = MistSecondary)
                )
                Text(
                    text = "Volume 1",
                    style = MaterialTheme.typography.labelSmall.copy(color = GoldDark)
                )
            }

            // Chapters List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(filteredChapters.size) { index ->
                    val chapter = filteredChapters[index]
                    ChapterCardItem(
                        chapter = chapter,
                        onOpen = { onSelectChapter(chapter.number) }
                    )
                }

                // Atalho para a Próxima Seção
                item {
                    NextSectionButton(
                        sectionTitle = "Ler o Livro",
                        icon = Icons.Default.Book,
                        onClick = onNavigateReadingList,
                        testTag = "chapters_btn_next_reading"
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterCardItem(
    chapter: BookChapter,
    onOpen: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { expanded = !expanded }
            .testTag("chapter_item_${chapter.number}"),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(GoldContainer)
                        .border(1.dp, GoldDark, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "${chapter.number}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextParchment
                        )
                    )
                    Text(
                        text = chapter.actName,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MistSecondary
                        )
                    )
                }
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = "Expandir",
                        tint = GoldPrimary
                    )
                }
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp)) {
                    Text(
                        text = chapter.synopsis,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextParchment,
                            lineHeight = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = chapter.actName,
                            style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                        )
                        OutlinedButton(
                            onClick = onOpen,
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                            border = BorderStroke(1.dp, GoldDark),
                            shape = RoundedCornerShape(6.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.testTag("chapter_btn_details_${chapter.number}")
                        ) {
                            Text("Ver Ficha", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChapterDetailScreen(
    chapterNumber: Int,
    onBack: () -> Unit,
    onNavigateChapter: (Int) -> Unit
) {
    val chapter = BookUniverse.chapters.find { it.number == chapterNumber } ?: BookUniverse.chapters.first()

    Scaffold(
        topBar = {
            BookTopBar(
                title = "Capítulo ${chapter.number}",
                onBack = onBack,
                shareContent = "Capítulo ${chapter.number}: ${chapter.title}\n(${chapter.actName})\n\n${chapter.synopsis}"
            )
        },
        containerColor = CanvasDeep
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
        ) {
            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                item {
                    GoldBadge(text = chapter.actName)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Capítulo ${chapter.number}",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = GoldLight,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = chapter.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextParchment
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    SectionHeader(
                        title = "Sinopse do Capítulo",
                        subtitle = "Visão geral narrativa (preservando o mistério sem spoilers)"
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = chapter.synopsis,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment,
                            lineHeight = 24.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Cenários do Capítulo",
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
                        chapter.locations.forEach { loc ->
                            GoldBadge(text = loc, isSecondary = true)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Personagens em Destaque",
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
                        chapter.characters.forEach { charName ->
                            GoldBadge(text = charName)
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Temas & Palavras-Chave",
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
                        chapter.keywords.forEach { kw ->
                            GoldBadge(text = "#$kw", isSecondary = true)
                        }
                    }
                }
            }

            // Prev / Next Navigation Footer
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { onNavigateChapter(chapter.number - 1) },
                    enabled = chapter.number > 1,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                    border = BorderStroke(1.dp, GoldDark),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("btn_prev_chapter")
                ) {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Anterior")
                }

                OutlinedButton(
                    onClick = { onNavigateChapter(chapter.number + 1) },
                    enabled = chapter.number < 32,
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                    border = BorderStroke(1.dp, GoldDark),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("btn_next_chapter")
                ) {
                    Text("Próximo")
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}
