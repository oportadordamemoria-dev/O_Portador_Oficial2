package com.example.ui.screens.puzzle

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.data.puzzle.PuzzleDifficulty
import com.example.data.puzzle.PuzzleImage
import com.example.data.puzzle.PuzzlePiece
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary

/**
 * Tabuleiro principal do Puzzle da Memória.
 * Garante recorte proporcional (sem esticar ou achatar a imagem)
 * e interação suave tanto por toque A+B quanto por arrastar.
 */
@Composable
fun PuzzleBoardView(
    puzzleImage: PuzzleImage,
    difficulty: PuzzleDifficulty,
    pieces: List<PuzzlePiece>,
    selectedPieceIndex: Int?,
    recentlyCorrectPieceId: Int?,
    isSolved: Boolean,
    onPieceClicked: (Int) -> Unit,
    onSwapPieces: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    // Carrega o Bitmap oficial da imagem em memória
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

    if (imageBitmap == null || rawBitmap == null) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .background(CanvasCard),
            contentAlignment = Alignment.Center
        ) {
            Text("Carregando memória...", color = GoldLight)
        }
        return
    }

    // Calcula recorte central quadrado 1:1 rigoroso
    val bw = rawBitmap.width
    val bh = rawBitmap.height
    val cropDimension = minOf(bw, bh)
    val cropX = (bw - cropDimension) / 2
    val cropY = (bh - cropDimension) / 2

    val gridSize = difficulty.gridSize

    // Animação de transição suave quando o puzzle é resolvido:
    // O espaçamento e cantos arredondados entre peças se fundem na imagem íntegra
    val tileSpacing by animateDpAsState(
        targetValue = if (isSolved) 0.dp else 2.5.dp,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "tile_spacing"
    )

    val tileCornerRadius by animateDpAsState(
        targetValue = if (isSolved) 0.dp else 4.dp,
        animationSpec = tween(durationMillis = 700, easing = FastOutSlowInEasing),
        label = "tile_corner"
    )

    // Moldura do tabuleiro
    Card(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .shadow(if (isSolved) 16.dp else 8.dp, RoundedCornerShape(12.dp))
            .border(
                BorderStroke(
                    if (isSolved) 2.5.dp else 1.5.dp,
                    if (isSolved) GoldLight else CanvasBorder
                ),
                RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CanvasDeep)
    ) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(4.dp)
                .testTag("puzzle_board_container")
        ) {
            val boardSizePx = with(LocalDensity.current) { maxWidth.toPx() }
            val tileSizePx = boardSizePx / gridSize

            // Renderiza as peças dispostas no grid
            pieces.forEachIndexed { index, piece ->
                val isSelected = selectedPieceIndex == index
                val isJustCorrect = recentlyCorrectPieceId == piece.id

                // Cálculo do retângulo de recorte original da peça no bitmap
                val (srcOffset, srcSize) = remember(piece.originalRow, piece.originalCol, cropDimension, gridSize) {
                    calculatePieceSrcRect(
                        cropX = cropX,
                        cropY = cropY,
                        cropSize = cropDimension,
                        gridSize = gridSize,
                        originalRow = piece.originalRow,
                        originalCol = piece.originalCol
                    )
                }

                val row = piece.currentRow
                val col = piece.currentCol

                val leftOffsetDp = maxWidth * (col.toFloat() / gridSize)
                val topOffsetDp = maxHeight * (row.toFloat() / gridSize)
                val pieceWidthDp = maxWidth / gridSize
                val pieceHeightDp = maxHeight / gridSize

                Box(
                    modifier = Modifier
                        .size(pieceWidthDp, pieceHeightDp)
                        .graphicsLayer {
                            translationX = leftOffsetDp.toPx()
                            translationY = topOffsetDp.toPx()
                            if (isSelected) {
                                scaleX = 1.05f
                                scaleY = 1.05f
                            }
                        }
                        .zIndex(if (isSelected) 10f else if (isJustCorrect) 5f else 1f)
                        .padding(tileSpacing)
                        .clip(RoundedCornerShape(tileCornerRadius))
                        .clickable(enabled = !isSolved) {
                            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                            onPieceClicked(index)
                        }
                        .testTag("puzzle_piece_${row}_${col}")
                ) {
                    // Desenho da fatia exata da imagem sem distorções
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        drawImage(
                            image = imageBitmap,
                            srcOffset = srcOffset,
                            srcSize = srcSize,
                            dstOffset = IntOffset.Zero,
                            dstSize = IntSize(size.width.toInt(), size.height.toInt()),
                            filterQuality = FilterQuality.High
                        )
                    }

                    // Destaque de Seleção (Borda dourada suave e discreta - Requisito 9)
                    if (isSelected && !isSolved) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    BorderStroke(2.5.dp, GoldLight),
                                    RoundedCornerShape(tileCornerRadius)
                                )
                                .background(GoldPrimary.copy(alpha = 0.15f))
                        )
                    }

                    // Brilho de encaixe correto (0.5 segundo ao acertar a posição - Requisito 10)
                    if (isJustCorrect && !isSolved) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .border(
                                    BorderStroke(2.dp, GoldLight),
                                    RoundedCornerShape(tileCornerRadius)
                                )
                                .background(GoldLight.copy(alpha = 0.35f))
                        )
                    }
                }
            }

            // Brilho dourado de conclusão suave sobre a imagem unificada (Requisito 14)
            if (isSolved) {
                val shimmerAlpha by animateFloatAsState(
                    targetValue = 0.15f,
                    animationSpec = tween(1200, easing = LinearEasing),
                    label = "shimmer_alpha"
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(GoldLight.copy(alpha = shimmerAlpha))
                )
            }
        }
    }
}

/**
 * Função de recorte pixel-a-pixel:
 * Garante que a soma das fatias cubra exatamente a totalidade do crop original sem sobras ou lacunas.
 */
private fun calculatePieceSrcRect(
    cropX: Int,
    cropY: Int,
    cropSize: Int,
    gridSize: Int,
    originalRow: Int,
    originalCol: Int
): Pair<IntOffset, IntSize> {
    val baseW = cropSize / gridSize
    val baseH = cropSize / gridSize
    val remW = cropSize % gridSize
    val remH = cropSize % gridSize

    val startX = cropX + originalCol * baseW + minOf(originalCol, remW)
    val startY = cropY + originalRow * baseH + minOf(originalRow, remH)
    val width = baseW + if (originalCol < remW) 1 else 0
    val height = baseH + if (originalRow < remH) 1 else 0

    return Pair(IntOffset(startX, startY), IntSize(width, height))
}
