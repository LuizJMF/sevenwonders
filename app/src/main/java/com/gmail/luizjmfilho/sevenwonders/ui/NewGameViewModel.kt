package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.data.NewGameRepository
import com.gmail.luizjmfilho.sevenwonders.model.Person
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewGameViewModel @Inject constructor(
    private val newGameRepository: NewGameRepository,
    firebaseAnalytics: FirebaseAnalytics,
) : TrackedScreenViewModel(firebaseAnalytics, "NewGame") {

    private val _uiState = MutableStateFlow(NewGameUiState())
    val uiState: StateFlow<NewGameUiState> = _uiState.asStateFlow()

    private val persons = MutableList<Person?>( 7 ) { null }
    private var playerIndexThatGoesToPlayerListScreen: Int? = null

    fun setPlayerNames(selectedIdFromPlayerListScreen: Int) {
        val fixedPlayerIndex = playerIndexThatGoesToPlayerListScreen
        if (fixedPlayerIndex != null) {
            viewModelScope.launch {
                val playerSelected =
                    newGameRepository.getPlayerFromId(selectedIdFromPlayerListScreen)
                persons[fixedPlayerIndex] = playerSelected
                val newPlayerNames = persons.map { it?.name.orEmpty() }.toMutableList()
                newPlayerNames[fixedPlayerIndex] = playerSelected.name
                _uiState.update { currentState ->
                    currentState.copy(
                        playerNames = newPlayerNames,
                        isAdvanceAndAddPlayerButtonsEnable = newPlayerNames.take(currentState.activePlayersNumber.numValue)
                            .all { it != "" },
                    )
                }
            }
        }
    }

    fun saveIndexAndGetPlayerIds(playerIndexBeingSelected: Int): List<Int> {
        playerIndexThatGoesToPlayerListScreen = playerIndexBeingSelected
        return getPlayerIds()
    }

    fun getPlayerIds(): List<Int> {
        return persons.mapNotNull{
            it?.id
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
            val newActivePlayersList = currentState.playerNames.toMutableList()
            newActivePlayersList[currentState.activePlayersNumber.numValue - 1] = ""
            currentState.copy(
                playerNames = newActivePlayersList,
                activePlayersNumber = newPlayersQuantity,
                isAdvanceAndAddPlayerButtonsEnable = true,
            )
        }
    }
}