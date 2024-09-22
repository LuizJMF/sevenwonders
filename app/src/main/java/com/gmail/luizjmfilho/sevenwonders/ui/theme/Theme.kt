package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun getLightThemeColor(): ColorScheme {
    return lightColorScheme(
        surface = azulClarissimo,
        onSurface = azulEscuro,
        primary = azulEscuro,
        onPrimary = branco,
        secondary = azulVivo,
        tertiary = cinzaEscuro,
        background = branco,
        onBackground = preto,
    )
}

@Composable
fun getDarkThemeColor(): ColorScheme {
    return darkColorScheme(
        surface = roxoEscuro,
        onSurface = roxoClaro,
        primary = roxoClaro,
        onPrimary = preto,
        secondary = roxoClaro,
        tertiary = cinzaClaro,
        background = roxoEscuro,
        onBackground = branco
    )
}


@Composable
fun SevenWondersTheme(darkTheme: Boolean = isSystemInDarkTheme(),content: @Composable () -> Unit) {
    val colors = if (darkTheme) getDarkThemeColor() else getLightThemeColor()
    MaterialTheme(
        typography = sevenWondersTypography,
        content = content,
        colorScheme = colors
    )
}
