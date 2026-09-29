package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldPrimary,
    onPrimary = CanvasDeep,
    primaryContainer = GoldContainer,
    onPrimaryContainer = OnGoldContainer,
    secondary = MistSecondary,
    onSecondary = CanvasDeep,
    secondaryContainer = MistContainer,
    onSecondaryContainer = OnMistContainer,
    tertiary = AmberTertiary,
    onTertiary = CanvasDeep,
    tertiaryContainer = AmberContainer,
    onTertiaryContainer = OnAmberContainer,
    background = CanvasDeep,
    onBackground = TextParchment,
    surface = CanvasSurface,
    onSurface = TextParchment,
    surfaceVariant = CanvasSurfaceVariant,
    onSurfaceVariant = TextMuted,
    outline = CanvasBorder,
    outlineVariant = Color(0xFF1E2838)
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}

