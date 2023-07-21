package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable


@Composable
fun SevenWondersTheme(content: @Composable () -> Unit) {

    MaterialTheme(
        typography = Typography,
        content = content
    )
}
