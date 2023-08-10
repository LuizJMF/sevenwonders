package com.gmail.luizjmfilho.sevenwonders.data

data class NewGameUiState(
    val activePlayersList: List<String> = List(7){
        ""
    },
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
