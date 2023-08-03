package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Pessoa

data class PlayersListUiState(
    val nome: String = "",
    val apelido: String = "",
    val listaDeJogadores: List<Pessoa> = listOf(),
    val nameError: NameError? = null,
    val nicknameError: NicknameError? = null
)

enum class NameError {
    Empty,
    Exists,
}

enum class NicknameError {
    Empty,
    Exists,
}
