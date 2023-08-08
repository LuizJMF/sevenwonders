package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import com.gmail.luizjmfilho.sevenwonders.data.ActivePlayersNumber
import com.gmail.luizjmfilho.sevenwonders.data.NewGameUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewGameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState: StateFlow<NewGameUiState> = _uiState.asStateFlow()

    fun updatePlayer (playerPosition: Int, playerName: String){
        _uiState.update { currentState ->
            val listaMutavel = currentState.activePlayersList.toMutableList()
            listaMutavel[playerPosition] = playerName
            currentState.copy(
                activePlayersList = listaMutavel
            )
        }
    }

    fun newGameAddPlayer () {
        _uiState.update { currentState ->
            val newPlayersQuantity = ActivePlayersNumber.values()[currentState.activePlayersNumber.ordinal + 1]
            currentState.copy(
                activePlayersNumber = newPlayersQuantity
            )
        }
    }

    fun newGameRemovePlayer () {
        _uiState.update { currentState ->
            val newPlayersQuantity = ActivePlayersNumber.values()[currentState.activePlayersNumber.ordinal - 1]
            val listaMutavel = currentState.activePlayersList.toMutableList()
            listaMutavel[currentState.activePlayersNumber.numValue - 1] = ""
            currentState.copy(
                activePlayersList = listaMutavel,
                activePlayersNumber = newPlayersQuantity
            )
        }
    }

}