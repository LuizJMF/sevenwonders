package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Person

data class PlayersListUiState(
    val name: String = "",
    val nickname: String = "",
    val playersList: List<Person> = listOf(),
    val nameError: NameOrNicknameError? = null,
    val nicknameError: NameOrNicknameError? = null
)

enum class NameOrNicknameError {
    Empty,
    Exists,
}
