package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Match

data class StatsUiState(
    val isLoading: Boolean = true,
    val playersToBeFiltered: List<String> = emptyList(),
    val playersFiltered: List<String> = emptyList(),
    val isDropdownFilterShown: Boolean = false,
    val isDatabaseEmpty: Boolean = true,
    val bestScoresList: List<Match> = emptyList(),
    val worstScoresList: List<Match> = emptyList(),
    val bestScoresPerPlayerList: List<Match> = emptyList(),
    val worstScoresPerPlayerList: List<Match> = emptyList(),
    val averageWinnerScore: Int = 0,
    val averageScorePerPlayer: List<Pair<String, Int>> = emptyList(),
    val mostAbsoluteChampionList: List<Pair<String, Int>> = emptyList(),
    val mostRelativeChampionList: List<Pair<String, Int>> = emptyList(),
    val allAbsoluteVictoriesList: List<Pair<String, Int>> = emptyList(),
    val allRelativeVictoriesList: List<Pair<String, Int>> = emptyList(),
    val blueList: List<Pair<String, Int>> = emptyList(),
    val yellowList: List<Pair<String, Int>> = emptyList(),
    val greenList: List<Pair<String, Int>> = emptyList(),
    val purpleList: List<Pair<String, Int>> = emptyList(),
    val bestWondersList: List<ResultadoDaConsultaSQLBestWonder> = emptyList(),
)

data class ResultadoDaConsultaSQLAverageScorePerPlayer(
    val nickname: String,
    val score: Int,
)

data class ResultadoDaConsultaSQLBestWonder(
    val wonder: Wonders,
    val wonderSide: WonderSide,
    val times: Int,
)