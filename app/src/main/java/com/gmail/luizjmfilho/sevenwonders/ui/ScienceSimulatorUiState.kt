package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.runtime.Immutable

@Immutable
data class ScienceSimulatorUiState(
    val compassCount: Int = 0,
    val stoneCount: Int = 0,
    val gearCount: Int = 0,
    val score: Int = 0,
)