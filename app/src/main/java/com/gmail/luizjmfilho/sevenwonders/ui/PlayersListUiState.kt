package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Person

data class PlayersListUiState(
    val nickname: String = "",
    val playersList: List<Person> = listOf(),
    val nicknameError: NameOrNicknameError? = null
)

enum class NameOrNicknameError {
    Empty,
    Exists,
}
