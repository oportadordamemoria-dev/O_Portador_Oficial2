package com.example.ui.screens.puzzle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.puzzle.PuzzleDifficulty
import com.example.data.puzzle.PuzzleImage
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PuzzleImageSelectorScreen(
    difficulty: PuzzleDifficulty,
    images: List<PuzzleImage>,
    onSelectImage: (PuzzleImage) -> Unit,
    onRerollImages: () -> Unit,
    onChangeDifficulty: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Puzzle da Memória",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = GoldLight,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Desafio ${difficulty.title} (${difficulty.dimensionLabel})",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MistLight
                            )
                        )
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier.testTag("puzzle_img_btn_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = GoldLight
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = onChangeDifficulty,
                        modifier = Modifier.testTag("puzzle_img_btn_change_diff")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Alterar Dificuldade",
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
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Título Oficial da Seção (Requisito 5)
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
                    text = "ESCOLHA UMA MEMÓRIA",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        color = TextParchment,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    text = "Três fragmentos foram revelados. Selecione qual deseja reconstruir:",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MistLight,
                        textAlign = TextAlign.Center
                    )
                )
            }

            // Exatamente 3 Grandes Miniaturas/Cards (Requisito 5)
            images.take(3).forEachIndexed { index, img ->
                MemoryImageCard(
                    image = img,
                    index = index + 1,
                    onChoose = { onSelectImage(img) }
                )
            }

            // Ações complementares (Sortear Outras & Trocar Dificuldade)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onRerollImages,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("puzzle_btn_reroll_images"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = GoldLight
                    ),
                    border = BorderStroke(1.dp, GoldDark)
                ) {
                    Icon(
                        imageVector = Icons.Default.Casino,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = GoldLight
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Sortear Outras",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }

                OutlinedButton(
                    onClick = onChangeDifficulty,
                    modifier = Modifier
                        .weight(1f)
                        .height(48.dp)
                        .testTag("puzzle_btn_change_diff_footer"),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = TextParchment
                    ),
                    border = BorderStroke(1.dp, CanvasBorder)
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = TextParchment
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Dificuldade",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun MemoryImageCard(
    image: PuzzleImage,
    index: Int,
    onChoose: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onChoose)
            .testTag("puzzle_card_memory_$index"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasCard),
        border = BorderStroke(1.dp, CanvasBorder),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Imagem Oficial em Grande Destaque
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
            ) {
                Image(
                    painter = painterResource(id = image.imageRes),
                    contentDescription = image.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradiente suave inferior para o texto
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    Color(0x33000000),
                                    CanvasCard.copy(alpha = 0.95f)
                                )
                            )
                        )
                )

                // Categoria no topo
                Surface(
                    shape = RoundedCornerShape(bottomEnd = 10.dp),
                    color = CanvasDeep.copy(alpha = 0.85f),
                    border = BorderStroke(0.5.dp, GoldDark.copy(alpha = 0.5f)),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    Text(
                        text = image.category,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldLight,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            // Informações e Botão "ESCOLHER"
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = image.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = TextParchment,
                                fontFamily = FontFamily.Serif,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        )

                        Text(
                            text = image.loreQuote,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MistLight,
                                fontStyle = FontStyle.Italic,
                                fontSize = 11.sp
                            ),
                            maxLines = 2
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    // Botão Oficial: ESCOLHER (Requisito 5)
                    Button(
                        onClick = onChoose,
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = GoldPrimary,
                            contentColor = CanvasDeep
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 3.dp),
                        modifier = Modifier.testTag("puzzle_btn_choose_$index")
                    ) {
                        Text(
                            text = "ESCOLHER",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }
            }
        }
    }
}
