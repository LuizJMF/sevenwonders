package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gmail.luizjmfilho.sevenwonders.data.NewGameRepository
import com.gmail.luizjmfilho.sevenwonders.data.getSevenWondersDatabaseInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewGameViewModel(
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

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val context = get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY)!!
                val database = getSevenWondersDatabaseInstance(context)
                val dao = database.personDao()
                val repository = NewGameRepository(dao)
                NewGameViewModel(repository)
            }
        }
    }

}