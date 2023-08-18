package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import com.gmail.luizjmfilho.sevenwonders.data.NameOrNicknameError
import com.gmail.luizjmfilho.sevenwonders.data.PlayersListUiState
import com.gmail.luizjmfilho.sevenwonders.model.Pessoa
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PlayersListViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(PlayersListUiState())
    val uiState: StateFlow<PlayersListUiState> = _uiState.asStateFlow()

    fun updateName(novoNome: String){
        _uiState.update { currentState ->
            currentState.copy(
                name = novoNome,
                nameError = null,
            )
        }
    }

    fun updateNickname(novoApelido: String){
        _uiState.update { currentState ->
            currentState.copy(
                nickname = novoApelido,
            )
        }
    }

    fun cancelAddPlayer(){
        _uiState.update { currentState ->
            currentState.copy(
                name = "",
                nickname = "",
                nameError = null,
                nicknameError = null,
            )
        }
    }

    fun onConfirmAddPlayerClick () {
        _uiState.update { currentState ->
            val nomeSemEspaco = currentState.name.trim()
            val nameError: NameOrNicknameError? = if (nomeSemEspaco == "") {
                NameOrNicknameError.Empty
            } else if (currentState.playersList.any{ cadaPessoa ->
                    cadaPessoa.nome.equals(nomeSemEspaco, true)
                }) {
                NameOrNicknameError.Exists
            } else {
                null
            }

            val apelidoSemEspaco = currentState.nickname.trim()
            val nicknameError: NameOrNicknameError? = if (apelidoSemEspaco == "") {
                NameOrNicknameError.Empty
            } else if (currentState.playersList.any{ cadaPessoa ->
                    cadaPessoa.apelido.equals(apelidoSemEspaco, true)
                }) {
                NameOrNicknameError.Exists
            } else {
                null
            }

            if (nameError == null && nicknameError == null) {
                val lista = currentState.playersList + Pessoa(nomeSemEspaco, apelidoSemEspaco)
                currentState.copy(
                    name = "",
                    nickname = "",
                    playersList = lista.sortedBy {
                        it.nome
                    },
                    nameError = nameError,
                    nicknameError = nicknameError
                )
            } else {
                currentState.copy(
                    nameError = nameError,
                    nicknameError = nicknameError
                )
            }
        }
    }

    fun deletePlayer (nome: String, apelido: String) {
        _uiState.update { currentState ->
            currentState.copy(
                playersList = currentState.playersList - Pessoa(nome, apelido)
            )
        }
    }

}

