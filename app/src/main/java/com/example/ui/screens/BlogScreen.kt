package com.example.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AuthorArticle
import com.example.data.BookUniverse
import com.example.ui.components.BookTopBar
import com.example.ui.components.GoldBadge
import com.example.ui.components.NextSectionButton
import com.example.ui.components.SectionHeader
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

@Composable
fun BlogScreen(
    onBack: () -> Unit,
    onSelectArticle: (String) -> Unit,
    onNavigateAuthor: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            BookTopBar(
                title = "Reflexões & Diário",
                onBack = onBack,
                shareContent = "Leia as reflexões autorais sobre o universo de O Portador da Memória por Júlio César Rodrigues."
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
                SectionHeader(
                    title = "Diário do Autor",
                    subtitle = "Ensaios, notas conceituais e reflexões sobre a memória por Júlio César Rodrigues",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }

            items(BookUniverse.articles.size) { index ->
                val article = BookUniverse.articles[index]
                ArticleItemCard(
                    article = article,
                    onClick = { onSelectArticle(article.id) },
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)
                )
            }

            // Atalho para a Próxima Seção
            item {
                NextSectionButton(
                    sectionTitle = "O Autor",
                    icon = Icons.Default.Person,
                    onClick = onNavigateAuthor,
                    testTag = "blog_btn_next_author"
                )
            }
        }
    }
}

@Composable
fun ArticleItemCard(
    article: AuthorArticle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .testTag("article_card_${article.id}"),
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
                GoldBadge(text = article.date)
                Icon(
                    imageVector = Icons.Default.EditNote,
                    contentDescription = null,
                    tint = GoldPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = GoldLight
                )
            )
            Text(
                text = article.subtitle,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MistSecondary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.excerpt,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextMuted,
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
                    text = "Ler ensaio completo",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = GoldDark,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ArticleDetailScreen(
    articleId: String,
    onBack: () -> Unit
) {
    val article = BookUniverse.articles.find { it.id == articleId } ?: BookUniverse.articles.first()

    Scaffold(
        topBar = {
            BookTopBar(
                title = article.title,
                onBack = onBack,
                shareContent = "${article.title}\n\n${article.content}\n\n— Júlio César Rodrigues"
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
                Column(modifier = Modifier.padding(20.dp)) {
                    GoldBadge(text = article.date)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article.title,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldLight
                        )
                    )
                    Text(
                        text = article.subtitle,
                        style = MaterialTheme.typography.titleSmall.copy(
                            color = MistSecondary
                        )
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Por Júlio César Rodrigues",
                        style = MaterialTheme.typography.labelSmall.copy(color = TextMuted)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = article.content,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = TextParchment,
                            lineHeight = 26.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "Tópicos Relacionados",
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
                        article.tags.forEach { tag ->
                            GoldBadge(text = "#$tag", isSecondary = true)
                        }
                    }
                }
            }
        }
    }
}
