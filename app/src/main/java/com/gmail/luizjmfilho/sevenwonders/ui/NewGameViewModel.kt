package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewGameViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState: StateFlow<NewGameUiState> = _uiState.asStateFlow()

    fun updatePlayer (playerPosition: Int, playerName: String){
        _uiState.update { currentState ->
            val newActivePlayersList = currentState.activePlayersList.toMutableList()
            newActivePlayersList[playerPosition] = playerName
            val isAdvanceAndAddPlayerButtonsEnable = newActivePlayersList.take(currentState.activePlayersNumber.numValue).all {
                it != ""
            }
            currentState.copy(
                activePlayersList = newActivePlayersList,
                isAdvanceAndAddPlayerButtonsEnable = isAdvanceAndAddPlayerButtonsEnable,
            )
        }
    }

    fun newGameAddPlayer () {
        _uiState.update { currentState ->
            val newPlayersQuantity = ActivePlayersNumber.values()[currentState.activePlayersNumber.ordinal + 1]
            currentState.copy(
                activePlayersNumber = newPlayersQuantity,
                isAdvanceAndAddPlayerButtonsEnable = false,
            )
        }
    }

    fun newGameRemovePlayer () {
        _uiState.update { currentState ->
            val newPlayersQuantity = ActivePlayersNumber.values()[currentState.activePlayersNumber.ordinal - 1]
            val newActivePlayersList = currentState.activePlayersList.toMutableList()
            newActivePlayersList[currentState.activePlayersNumber.numValue - 1] = ""
            currentState.copy(
                activePlayersList = newActivePlayersList,
                activePlayersNumber = newPlayersQuantity,
                isAdvanceAndAddPlayerButtonsEnable = true,
            )
        }
    }

}