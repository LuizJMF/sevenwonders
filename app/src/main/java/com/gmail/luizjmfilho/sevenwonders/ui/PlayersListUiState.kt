package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Person

data class PlayersListUiState(
    val playersList: List<String> = emptyList(),
    val playerBeingAdded: String = "",
    val nameError: NameOrNicknameError? = null,
    val isDialogShown: Boolean = false,
)

enum class NameOrNicknameError {
    Empty,
    Exists,
}