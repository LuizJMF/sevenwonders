package com.gmail.luizjmfilho.sevenwonders.ui

data class PlayersListUiState(
    val playerNames: List<String> = emptyList(),
    val newPlayerName: String = "",
    val newPlayerNameError: NameOrNicknameError? = null,
    val isNewPlayerDialogShown: Boolean = false,
)

enum class NameOrNicknameError {
    Empty,
    Exists,
}