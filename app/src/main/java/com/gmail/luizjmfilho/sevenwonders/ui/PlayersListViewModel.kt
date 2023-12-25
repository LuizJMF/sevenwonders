package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.data.PlayersListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val playersListRepository: PlayersListRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(PlayersListUiState())
    val uiState: StateFlow<PlayersListUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    playersList = playersListRepository.readPlayer(),
                )
            }
        }
    }

    fun updateNickname(novoApelido: String){
        _uiState.update { currentState ->
            val previousNickname = currentState.nickname
            val nickname = if (novoApelido.length <= 10) novoApelido else previousNickname
            currentState.copy(
                nickname = nickname,
            )
        }
    }

    fun cancelAddPlayer(){
        _uiState.update { currentState ->
            currentState.copy(
                nickname = "",
                nicknameError = null,
            )
        }
    }


    fun onConfirmAddPlayerClick () {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val addPlayerResult = playersListRepository.addPlayer(currentState.nickname)
                if (addPlayerResult == null) {
                    currentState.copy(
                        nickname = "",
                        playersList = playersListRepository.readPlayer(),
                        nicknameError = null,
                    )
                } else {
                    currentState.copy(
                        nicknameError = addPlayerResult.nicknameError
                    )
                }
            }
        }
    }

    fun deletePlayer (nickname: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                try {
                    playersListRepository.deletePlayer(nickname)
                    val x = playersListRepository.readPlayer()
                    currentState.copy(
                        playersList = x,
                    )
                } catch (e: Exception) {
                    println("Deu erro: ${e.message}")
                    currentState
                }
            }
        }
    }
}

