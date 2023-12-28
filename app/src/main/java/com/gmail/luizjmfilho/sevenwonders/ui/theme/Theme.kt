package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun getColor(): ColorScheme {
    val lightColorPalette = MaterialTheme.colorScheme.copy(
        surface = azulClarissimo,
        onSurface = azulEscuro,
        primary = azulEscuro,
        onPrimary = branco,
        secondary = azulVivo,
        tertiary = cinzaEscuro,
        background = azulSuperClarissimo

    )
    return lightColorPalette
}


@Composable
fun SevenWondersTheme(content: @Composable () -> Unit) {
    val colors = getColor()

    MaterialTheme(
        typography = Typography,
        content = content,
        colorScheme = colors
    )
}
