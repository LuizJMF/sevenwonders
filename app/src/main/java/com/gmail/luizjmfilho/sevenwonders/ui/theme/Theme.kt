package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun SevenWondersTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    MaterialTheme(
        typography = sevenWondersTypography,
        content = content,
        colorScheme = sevenWondersColorScheme(darkTheme),
    )
}
