package com.example.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ShoppingBag
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
import com.example.ui.components.BookTopBar
import com.example.ui.components.GoldBadge
import com.example.ui.components.LiteraryQuoteCard
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
fun AuthorScreen(
    onBack: () -> Unit,
    onNavigateHome: () -> Unit = {}
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            BookTopBar(
                title = "O Autor & Adquira o Livro",
                onBack = onBack,
                shareContent = "Conheça Júlio César Rodrigues, autor de O Portador da Memória: Entre a Luz e a Escuridão."
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
            // Author Profile Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, GoldDark)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(96.dp)
                                .clip(CircleShape)
                                .background(GoldContainer)
                                .border(2.dp, GoldPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.app_icon_symbol),
                                contentDescription = "Selo do Autor",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = BookUniverse.AUTHOR_NAME,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextParchment
                            ),
                            textAlign = TextAlign.Center
                        )
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(top = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = null,
                                tint = MistSecondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = BookUniverse.AUTHOR_LOCATION,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = MistSecondary
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        GoldBadge(text = "Autor & Criador do Universo")

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Escritor catarinense, natural de Blumenau, Júlio César Rodrigues tece em O Portador da Memória uma narrativa que une o cotidiano chuvoso do Vale do Itajaí aos abismos filosóficos da memória humana e do esquecimento.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextParchment,
                                lineHeight = 22.sp,
                                textAlign = TextAlign.Center
                            )
                        )

                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = {
                                val url = "https://instagram.com/juliocesarrodrigues195"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            },
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight),
                            border = BorderStroke(1.dp, GoldDark),
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.testTag("btn_author_instagram")
                        ) {
                            Icon(
                                imageVector = Icons.Default.CameraAlt,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Instagram: ${BookUniverse.AUTHOR_INSTAGRAM}",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }

            // Literary Vision Card
            item {
                Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 6.dp)) {
                    LiteraryQuoteCard(
                        quote = "Escrever sobre a memória é, antes de tudo, prestar reverência àqueles cujas vozes foram engolidas pelo silêncio dos tempos.",
                        author = BookUniverse.AUTHOR_NAME
                    )
                }
            }

            // Elegant Book Acquisition Section (Following strict rules)
            item {
                SectionHeader(
                    title = "Sua Jornada Começa Aqui",
                    subtitle = "Algumas histórias são lidas. Outras precisam ser descobertas.",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 14.dp)
                )
            }

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 6.dp)
                        .testTag("book_acquisition_card"),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = CanvasCard),
                    border = BorderStroke(1.dp, CanvasBorder)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MenuBook,
                                contentDescription = null,
                                tint = GoldPrimary,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "Livro Físico Oficial",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight
                                    )
                                )
                                Text(
                                    text = "Volume 1 — 32 Capítulos | Edição Impressa",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = MistSecondary
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "A obra está disponível para aquisição em formato impresso com produção e distribuição sob demanda através da livraria UICLAP, com entrega para todo o território nacional.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = TextParchment,
                                lineHeight = 22.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(18.dp))
                        Button(
                            onClick = {
                                val url = BookUniverse.BOOK_STORE_URL
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("btn_acquire_book"),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = GoldPrimary,
                                contentColor = CanvasDeep
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingBag,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Adquirir na Livraria UICLAP",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            // Atalho para Voltar ao Início do Universo
            item {
                NextSectionButton(
                    sectionTitle = "Voltar ao Início do Universo",
                    icon = Icons.Default.Home,
                    onClick = onNavigateHome,
                    testTag = "author_btn_next_home"
                )
            }
        }
    }
}
