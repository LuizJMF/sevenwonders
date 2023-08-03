package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import com.gmail.luizjmfilho.sevenwonders.data.NameError
import com.gmail.luizjmfilho.sevenwonders.data.NicknameError
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
                nome = novoNome,
                nameError = null,
            )
        }
    }

    fun updateNickname(novoApelido: String){
        _uiState.update { currentState ->
            currentState.copy(
                apelido = novoApelido,
            )
        }
    }

    fun cancelarAddJogador(){
        _uiState.update { currentState ->
            currentState.copy(
                nome = "",
                apelido = "",
                nameError = null,
                nicknameError = null,
            )
        }
    }

    fun addJogador () {
        _uiState.update { currentState ->
            val nomeSemEspaço = currentState.nome.trim()
            val nameError: NameError? = if (nomeSemEspaço == "") {
                NameError.Empty
            } else if (currentState.listaDeJogadores.any{ cadaPessoa ->
                    cadaPessoa.nome.equals(nomeSemEspaço, true)
                }) {
                NameError.Exists
            } else {
                null
            }

            val apelidoSemEspaço = currentState.apelido.trim()
            val nicknameError: NicknameError? = if (apelidoSemEspaço == "") {
                NicknameError.Empty
            } else if (currentState.listaDeJogadores.any{ cadaPessoa ->
                    cadaPessoa.apelido.equals(apelidoSemEspaço, true)
                }) {
                NicknameError.Exists
            } else {
                null
            }

            if (nameError == null && nicknameError == null) {
                val lista = currentState.listaDeJogadores + Pessoa(nomeSemEspaço, apelidoSemEspaço)
                currentState.copy(
                    nome = "",
                    apelido = "",
                    listaDeJogadores = lista.sortedBy {
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

    fun apagarJogador (nome: String, apelido: String) {
        _uiState.update { currentState ->
            currentState.copy(
                listaDeJogadores = currentState.listaDeJogadores - Pessoa(nome, apelido)
            )
        }
    }

}

