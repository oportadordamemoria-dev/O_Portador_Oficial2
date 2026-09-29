package com.example.ui.screens.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.game.CardCategory
import com.example.data.game.MemoryCard
import com.example.ui.theme.AmberTertiary
import com.example.ui.theme.CanvasBorder
import com.example.ui.theme.CanvasCard
import com.example.ui.theme.CanvasDeep
import com.example.ui.theme.CanvasSurface
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.MistSecondary
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextParchment

@Composable
fun MemoryCardView(
    slot: CardSlot,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val rotation by animateFloatAsState(
        targetValue = if (slot.isFaceUp) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "card_flip_rotation"
    )

    val isFrontVisible = rotation > 90f

    val borderStroke = when {
        slot.isGlowActive -> BorderStroke(2.5.dp, GoldLight)
        slot.isMatched -> BorderStroke(1.5.dp, GoldPrimary.copy(alpha = 0.8f))
        slot.isMistActive -> BorderStroke(2.dp, Color(0xFFEF5350))
        slot.isSelected -> BorderStroke(2.dp, GoldLight)
        slot.isFaceUp -> BorderStroke(1.dp, CanvasBorder)
        else -> BorderStroke(1.dp, GoldDark.copy(alpha = 0.5f))
    }

    val shadowElevation = when {
        slot.isGlowActive -> 12.dp
        slot.isMatched -> 6.dp
        slot.isMistActive -> 6.dp
        slot.isFaceUp -> 4.dp
        else -> 2.dp
    }

    Box(
        modifier = modifier
            .aspectRatio(0.72f)
            .shadow(shadowElevation, RoundedCornerShape(12.dp))
            .clip(RoundedCornerShape(12.dp))
            .background(CanvasDeep)
            .border(borderStroke, RoundedCornerShape(12.dp))
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(color = GoldLight),
                enabled = enabled && !slot.isMatched,
                onClick = onClick
            )
            .semantics {
                contentDescription = if (slot.isFaceUp) {
                    "Carta ${slot.card.name}, ${slot.card.category.label}"
                } else {
                    "Carta de memória oculta"
                }
            }
            .testTag("memory_card_${slot.slotId}")
    ) {
        if (isFrontVisible) {
            // FRENTE DA CARTA (girada a 180 graus no eixo Y para ficar legível)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { rotationY = 180f }
            ) {
                CardFront(
                    card = slot.card,
                    isMatched = slot.isMatched,
                    isMistActive = slot.isMistActive
                )

                // Efeito sutil ao errar: a imagem permanece 100% visível, nítida e clara,
                // apenas com um indicador discreto no canto e borda temática
                if (slot.isMistActive) {
                    Surface(
                        color = Color(0xDDF44336),
                        shape = CircleShape,
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(5.dp)
                            .size(20.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Não formam par",
                                tint = Color.White,
                                modifier = Modifier.size(13.dp)
                            )
                        }
                    }
                }

                // Efeito de brilho dourado ao acertar
                if (slot.isGlowActive || slot.isMatched) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                Brush.radialGradient(
                                    colors = listOf(
                                        GoldLight.copy(alpha = 0.35f),
                                        Color.Transparent
                                    )
                                )
                            )
                    )
                    // Indicador sutil de memória preservada no canto superior
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "Memória Preservada",
                        tint = GoldLight,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(6.dp)
                            .size(18.dp)
                    )
                }
            }
        } else {
            // VERSO OFICIAL DA CARTA
            CardBack(isHovered = slot.isSelected)
        }
    }
}

/**
 * Frente da carta com imagem rica, título, categoria e símbolo sagrado.
 */
@Composable
private fun CardFront(
    card: MemoryCard,
    isMatched: Boolean,
    isMistActive: Boolean = false
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CanvasCard),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Área superior: Imagem do personagem, lugar ou conceito (nítida e sem escurecimento)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(CanvasDeep)
        ) {
            if (card.imageRes != null) {
                Image(
                    painter = painterResource(id = card.imageRes),
                    contentDescription = card.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                // Fallback elegante com símbolo místico
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                listOf(CanvasSurface, CanvasDeep)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = card.symbolIcon,
                        fontSize = 36.sp
                    )
                }
            }

            // Tag da Categoria no topo
            Surface(
                color = when (card.category) {
                    CardCategory.PERSONAGEM -> GoldDark.copy(alpha = 0.85f)
                    CardCategory.LUGAR -> Color(0xDD1B3B2B)
                    CardCategory.VALOR -> Color(0xDD3A2610)
                    CardCategory.MEMORIA -> Color(0xDD1E293B)
                },
                shape = RoundedCornerShape(bottomEnd = 6.dp),
                modifier = Modifier.align(Alignment.TopStart)
            ) {
                Text(
                    text = card.category.label.uppercase(),
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldLight,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.8.sp
                    ),
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }

            // Ícone do Símbolo no topo direito (oculto durante erro para dar espaço ao indicador de não-combinação)
            if (!isMistActive && !isMatched) {
                Surface(
                    color = Color(0xAA000000),
                    shape = CircleShape,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(20.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = card.symbolIcon,
                            fontSize = 11.sp
                        )
                    }
                }
            }
        }

        // Rodapé da carta com nome e breve descrição
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isMistActive) Color(0xFF261919) else CanvasCard)
                .padding(horizontal = 6.dp, vertical = 5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = card.name,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = when {
                        isMatched -> GoldLight
                        isMistActive -> Color(0xFFFFCDD2)
                        else -> TextParchment
                    },
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            if (card.shortDescription.isNotBlank()) {
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = card.shortDescription,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextMuted,
                        fontSize = 8.5.sp,
                        lineHeight = 10.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * Verso oficial das cartas do Desafio da Memória:
 * Fundo muito escuro + ornamentos dourados discretos + símbolo sagrado da memória no centro.
 */
@Composable
private fun CardBack(
    isHovered: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF141926),
                        Color(0xFF090C12),
                        Color(0xFF05070A)
                    )
                )
            )
            .padding(6.dp),
        contentAlignment = Alignment.Center
    ) {
        // Moldura interna dourada sutil
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(
                    BorderStroke(0.8.dp, GoldPrimary.copy(alpha = if (isHovered) 0.8f else 0.35f)),
                    RoundedCornerShape(8.dp)
                )
                .padding(4.dp)
        ) {
            // Cantoneiras douradas decorativas
            Text(
                text = "⌜",
                color = GoldLight.copy(alpha = 0.6f),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.TopStart)
            )
            Text(
                text = "⌝",
                color = GoldLight.copy(alpha = 0.6f),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            Text(
                text = "⌞",
                color = GoldLight.copy(alpha = 0.6f),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.BottomStart)
            )
            Text(
                text = "⌟",
                color = GoldLight.copy(alpha = 0.6f),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

        // Símbolo sagrado central: Folha da Memória / Fragmento de Luz Dourado
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Surface(
                shape = CircleShape,
                color = Color(0x33D4AF37),
                border = BorderStroke(1.dp, GoldLight.copy(alpha = 0.5f)),
                modifier = Modifier.size(38.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.AutoAwesome,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "✦",
                color = GoldLight.copy(alpha = 0.7f),
                fontSize = 10.sp
            )
        }
    }
}
