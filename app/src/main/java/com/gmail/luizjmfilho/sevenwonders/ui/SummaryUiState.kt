package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Match

data class SummaryUiState(
    val matchList: List<Match> = emptyList(),
)