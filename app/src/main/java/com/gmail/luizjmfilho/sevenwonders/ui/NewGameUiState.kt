package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Person

data class NewGameUiState(
    val activePlayersList: List<String> = List(7) {
        ""
    },
    val availablePlayersList: List<Person> = listOf(),
    val activePlayersNumber: ActivePlayersNumber = ActivePlayersNumber.Three,
    val isAdvanceAndAddPlayerButtonsEnable: Boolean = false,
)

enum class ActivePlayersNumber {
    Three,
    Four,
    Five,
    Six,
    Seven;

    val numValue: Int = ordinal + 3
}
