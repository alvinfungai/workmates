package com.alvinfungai.workmates.presentation.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(

    primary = TrustGreen,
    onPrimary = Color.White,

    secondary = TealAccent,
    onSecondary = Color.White,

    background = BackgroundLight,
    onBackground = TextPrimary,

    surface = SurfaceLight,
    onSurface = TextPrimary,

    surfaceVariant = SurfaceVariantLight,
    error = ErrorRed
)


private val DarkColors = darkColorScheme(

    primary = TrustGreenLight,
    onPrimary = Color.Black,

    secondary = TealAccentLight,
    onSecondary = Color.Black,

    background = DarkBackground,
    onBackground = DarkTextPrimary,

    surface = DarkSurface,
    onSurface = DarkTextPrimary,

    surfaceVariant = DarkSurfaceVariant,
    error = ErrorRed
)


@Composable
fun WorkmatesTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}