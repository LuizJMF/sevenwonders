package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalViewConfiguration
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

private val longPressRepeatTimeout = 100.milliseconds

@Composable
fun Modifier.onLongClickRepeat(
    interactionSource: InteractionSource,
    action: () -> Unit,
): Modifier {
    val longPressTimeout = LocalViewConfiguration.current.longPressTimeoutMillis.milliseconds
    val isPressed by interactionSource.collectIsPressedAsState()

    LaunchedEffect(isPressed) {
        if (isPressed) {
            action()
            delay(longPressTimeout)

            while (true) {
                action()
                delay(longPressRepeatTimeout)
            }
        }
    }

    return this
}

