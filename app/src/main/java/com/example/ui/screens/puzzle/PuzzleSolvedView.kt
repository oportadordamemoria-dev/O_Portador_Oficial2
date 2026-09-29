package com.example.ui.screens.puzzle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import com.example.ui.theme.GoldContainer
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

/**
 * Tela de Conclusão do Puzzle da Memória (Requisitos 15, 16, 17).
 * Exibe a imagem completa reconstituída, estatísticas e opções de continuação.
 */
@Composable
fun PuzzleSolvedView(
    puzzleImage: PuzzleImage,
    difficulty: PuzzleDifficulty,
    formattedTime: String,
    moveCount: Int,
    onAnotherPuzzle: () -> Unit,
    onPlayAgain: () -> Unit,
    onChangeDifficulty: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Título de Celebração (Requisito 15)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = GoldContainer,
                border = BorderStroke(1.dp, GoldLight),
                modifier = Modifier.size(42.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "MEMÓRIA RECONSTRUÍDA",
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = GoldLight,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                    textAlign = TextAlign.Center
                )
            )

            Text(
                text = "“Cada fragmento encontrou novamente o seu lugar.”",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MistLight,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                    textAlign = TextAlign.Center
                )
            )
        }

        // Imagem Completa em Grande Destaque (Requisito 15)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(16.dp, RoundedCornerShape(14.dp))
                .border(BorderStroke(2.dp, GoldLight), RoundedCornerShape(14.dp))
                .clip(RoundedCornerShape(14.dp))
                .testTag("puzzle_solved_hero_image"),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = CanvasCard)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = puzzleImage.imageRes),
                    contentDescription = puzzleImage.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Brilho dourado perimétrico
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(Color.Transparent, Color(0x33F3DE8A))
                            )
                        )
                )
            }
        }

        // Nome da Imagem e Categoria
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = puzzleImage.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = TextParchment,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = puzzleImage.category,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MistLight
                ),
                textAlign = TextAlign.Center
            )
        }

        // Painel de Estatísticas: Dificuldade, Tempo, Movimentos (Requisito 15)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                label = "DIFICULDADE",
                value = difficulty.title,
                detail = difficulty.badgeLabel,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "TEMPO",
                value = formattedTime,
                detail = "foco total",
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "MOVIMENTOS",
                value = "$moveCount",
                detail = "trocas",
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Botões de Ação Principais (Requisito 15, 16, 17)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // OUTRO PUZZLE (Mantém dificuldade, 3 novas imagens sorteadas)
            Button(
                onClick = onAnotherPuzzle,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("puzzle_btn_another"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = GoldPrimary,
                    contentColor = CanvasDeep
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Casino,
                    contentDescription = null,
                    tint = CanvasDeep,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "OUTRO PUZZLE",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }

            // JOGAR NOVAMENTE (Mesma imagem e dificuldade, novo embaralhamento)
            OutlinedButton(
                onClick = onPlayAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("puzzle_btn_replay"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = GoldLight
                ),
                border = BorderStroke(1.dp, GoldPrimary)
            ) {
                Icon(
                    imageVector = Icons.Default.Replay,
                    contentDescription = null,
                    tint = GoldLight,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "JOGAR NOVAMENTE",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Alterar Dificuldade
                TextButton(
                    onClick = onChangeDifficulty,
                    colors = ButtonDefaults.textButtonColors(contentColor = MistLight),
                    modifier = Modifier.testTag("puzzle_btn_change_diff_solved")
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MistLight
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Alterar Dificuldade",
                        style = MaterialTheme.typography.labelMedium
                    )
                }

                // VOLTAR (ao menu do Puzzle)
                TextButton(
                    onClick = onBack,
                    colors = ButtonDefaults.textButtonColors(contentColor = TextParchment),
                    modifier = Modifier.testTag("puzzle_btn_back_home")
                ) {
                    Text(
                        text = "Voltar ao Início",
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
private fun StatCard(
    label: String,
    value: String,
    detail: String,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = CanvasSurface,
        border = BorderStroke(1.dp, CanvasBorder)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontSize = 9.sp,
                    letterSpacing = 1.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = GoldLight,
                    fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
            )
            Text(
                text = detail,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = TextMuted,
                    fontSize = 9.sp
                )
            )
        }
    }
}
