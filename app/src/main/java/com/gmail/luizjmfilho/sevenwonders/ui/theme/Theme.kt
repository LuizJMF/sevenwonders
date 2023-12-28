package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun getLightThemeColor(): ColorScheme {
    return MaterialTheme.colorScheme.copy(
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
    return MaterialTheme.colorScheme.copy(
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
        typography = Typography,
        content = content,
        colorScheme = colors
    )
}
