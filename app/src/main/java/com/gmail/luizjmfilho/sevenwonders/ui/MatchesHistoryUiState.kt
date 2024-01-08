package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Match

data class MatchesHistoryUiState(
    val playerInfoList: List<Match> = emptyList(),
    val matchQuantity: Int = 0,
)

enum class VisualizationMode {
    GeneralInfo,
    DetailsInfo,
}