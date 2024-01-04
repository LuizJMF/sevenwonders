package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.PlayerDetails

data class MatchDetailsUiState(
    val creationMethod: CreationMethod? = null,
    val matchPlayersDetails: List<PlayerDetails> = listOf(),
    val availableWondersList: List<Wonders?> = Wonders.values().toList(),
    val isAdvanceButtonEnabled: Boolean = false,
)

enum class CreationMethod {
    AllRaffle,
    RafflePositionChooseWonder,
    ChoosePositionRaffleWonder,
    AllChoose,
}

enum class RaffleOrChoose {
    Raffle,
    Choose,
}

enum class WonderSide {
    Day,
    Night,
}

enum class Wonders {
    ALEXANDRIA,
    BABYLON,
    EPHESOS,
    GIZAH,
    HALIKARNASSOS,
    OLYMPIA,
    RHODOS,
}