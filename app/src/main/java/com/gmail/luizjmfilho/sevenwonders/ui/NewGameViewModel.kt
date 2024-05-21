package com.gmail.luizjmfilho.sevenwonders.ui

import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    firebaseAnalytics: FirebaseAnalytics,
) : TrackedScreenViewModel(firebaseAnalytics, "NewGame") {

    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState: StateFlow<NewGameUiState> = _uiState.asStateFlow()

    fun setActivePlayersList(activePlayersListFromPlayersListScreen: List<String>) {
        _uiState.update { currentState ->
            val isAdvanceAndAddPlayerButtonsEnable =
                activePlayersListFromPlayersListScreen.take(currentState.activePlayersNumber.numValue).all {
                    it != ""
                }
            currentState.copy(
                activePlayersList = activePlayersListFromPlayersListScreen,
                isAdvanceAndAddPlayerButtonsEnable = isAdvanceAndAddPlayerButtonsEnable,
            )
        }
    }

    fun newGameAddPlayer() {
        _uiState.update { currentState ->
            val newPlayersQuantity = ActivePlayersNumber.entries[currentState.activePlayersNumber.ordinal + 1]
            currentState.copy(
                activePlayersNumber = newPlayersQuantity,
                isAdvanceAndAddPlayerButtonsEnable = false,
            )
        }
    }

    fun newGameRemovePlayer() {
        _uiState.update { currentState ->
            val newPlayersQuantity = ActivePlayersNumber.entries[currentState.activePlayersNumber.ordinal - 1]
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