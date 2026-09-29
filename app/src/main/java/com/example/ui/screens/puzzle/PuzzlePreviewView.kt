package com.example.ui.screens.puzzle

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.puzzle.PuzzleDifficulty
import com.example.data.puzzle.PuzzleImage
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistLight
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

/**
 * Tela e Animação de Pré-visualização da Memória Escolhida.
 * - Exibe a imagem completa por ~3 segundos com enquadramento idêntico ao do quebra-cabeça.
 * - Linhas douradas surgem dividindo a imagem nas peças correspondentes à dificuldade.
 * - Em seguida, as peças se dispersam e o jogo tem início.
 */
@Composable
fun PuzzlePreviewView(
    puzzleImage: PuzzleImage,
    difficulty: PuzzleDifficulty,
    previewProgress: Float,
    onSkip: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val rawBitmap: Bitmap? = remember(puzzleImage.imageRes) {
        try {
            BitmapFactory.decodeResource(context.resources, puzzleImage.imageRes)
        } catch (e: Exception) {
            null
        }
    }
    val imageBitmap: ImageBitmap? = remember(rawBitmap) {
        rawBitmap?.asImageBitmap()
    }

    val gridSize = difficulty.gridSize

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Título e Frase Introdutória (Requisito 7 & 22)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(top = 8.dp)
        ) {
            Text(
                text = puzzleImage.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    color = TextParchment,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                ),
                textAlign = TextAlign.Center
            )

            Text(
                text = "“Observe bem esta memória...”",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = GoldLight,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.Serif,
                    fontSize = 15.sp
                ),
                textAlign = TextAlign.Center
            )
        }

        // Quadro da Imagem com Animação de Fragmentação
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(12.dp, RoundedCornerShape(12.dp))
                .border(BorderStroke(1.5.dp, GoldLight), RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .testTag("puzzle_preview_board"),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = CanvasCard)
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                if (imageBitmap != null && rawBitmap != null) {
                    val bw = rawBitmap.width
                    val bh = rawBitmap.height
                    val cropDimension = minOf(bw, bh)
                    val cropX = (bw - cropDimension) / 2
                    val cropY = (bh - cropDimension) / 2

                    // Desenho da imagem exatamente recortada como será fatiada
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawImage(
                            image = imageBitmap,
                            srcOffset = IntOffset(cropX, cropY),
                            srcSize = IntSize(cropDimension, cropDimension),
                            dstOffset = IntOffset.Zero,
                            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                            filterQuality = FilterQuality.High
                        )

                        // Animação das linhas divisórias surgindo na segunda metade da prévia
                        if (previewProgress > 0.5f) {
                            val linesAlpha = ((previewProgress - 0.5f) / 0.4f).coerceIn(0f, 1f)
                            val lineColor = GoldLight.copy(alpha = linesAlpha * 0.85f)
                            val stepX = size.width / gridSize
                            val stepY = size.height / gridSize

                            // Linhas verticais
                            for (col in 1 until gridSize) {
                                drawLine(
                                    color = lineColor,
                                    start = Offset(col * stepX, 0f),
                                    end = Offset(col * stepX, size.height),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                            // Linhas horizontais
                            for (row in 1 until gridSize) {
                                drawLine(
                                    color = lineColor,
                                    start = Offset(0f, row * stepY),
                                    end = Offset(size.width, row * stepY),
                                    strokeWidth = 2.dp.toPx()
                                )
                            }
                        }
                    }
                }
            }
        }

        // Barra de progresso e botão de pular
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LinearProgressIndicator(
                progress = { previewProgress },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = GoldLight,
                trackColor = CanvasBorder
            )

            TextButton(
                onClick = onSkip,
                colors = ButtonDefaults.textButtonColors(contentColor = GoldLight),
                modifier = Modifier.testTag("puzzle_btn_skip_preview")
            ) {
                Text(
                    text = "Montar agora →",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
