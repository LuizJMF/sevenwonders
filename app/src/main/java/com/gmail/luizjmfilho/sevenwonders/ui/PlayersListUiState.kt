package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.model.Pessoa

data class PlayersListUiState(
    val name: String = "",
    val nickname: String = "",
    val playersList: List<Pessoa> = listOf(),
    val nameError: NameOrNicknameError? = null,
    val nicknameError: NameOrNicknameError? = null
)

enum class NameOrNicknameError {
    Empty,
    Exists,
}
