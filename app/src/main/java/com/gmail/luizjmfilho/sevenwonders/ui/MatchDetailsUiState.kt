package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.PlayerDetail

data class MatchDetailsUiState(
    val creationMethod: CreationMethod? = null,
    val matchPlayersDetails: List<PlayerDetail> = listOf(),
    val availableWondersList: List<Wonders?> = Wonders.entries,
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