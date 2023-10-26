package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.data.NewGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val newGameRepository: NewGameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState: StateFlow<NewGameUiState> = _uiState.asStateFlow()

    fun updateAvailablePlayersList() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    availablePlayersList = newGameRepository.readPlayerWithoutActivePlayers(currentState.activePlayersList - listOf(""))
                )
            }
        }
    }

    fun updatePlayer(playerPosition: Int, playerNickname: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newActivePlayersList = currentState.activePlayersList.toMutableList()
                newActivePlayersList[playerPosition] = playerNickname
                val isAdvanceAndAddPlayerButtonsEnable =
                    newActivePlayersList.take(currentState.activePlayersNumber.numValue).all {
                        it != ""
                    }
                currentState.copy(
                    activePlayersList = newActivePlayersList,
                    isAdvanceAndAddPlayerButtonsEnable = isAdvanceAndAddPlayerButtonsEnable,
                )
            }
        }
    }

    fun newGameAddPlayer() {
        _uiState.update { currentState ->
            val newPlayersQuantity = ActivePlayersNumber.values()[currentState.activePlayersNumber.ordinal + 1]
            currentState.copy(
                activePlayersNumber = newPlayersQuantity,
                isAdvanceAndAddPlayerButtonsEnable = false,
            )
        }
    }

    fun newGameRemovePlayer() {
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