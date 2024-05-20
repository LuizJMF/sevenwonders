package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.data.PlayersListRepository
import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val playersListRepository: PlayersListRepository,
    savedStateHandle: SavedStateHandle,
    firebaseAnalytics: FirebaseAnalytics,
) : TrackedScreenViewModel(firebaseAnalytics, "PlayersList")  {

    private val originalInfo = savedStateHandle.get<String>("info")!!.split(",")
    private val playerIndexBeingSelected = originalInfo[0]
    private val activePlayersList = if (originalInfo.size == 1) emptyList() else originalInfo.subList(1,originalInfo.size)
    private val _uiState = MutableStateFlow(PlayersListUiState())
    val uiState: StateFlow<PlayersListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    playersList = playersListRepository.readPlayer() - activePlayersList.filter { it != "" }.toSet(),
                )
            }
        }
    }

    fun onAddPlayer() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val addPlayerResult = playersListRepository.addPlayer(currentState.playerBeingAdded.lowercase().replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() })
                if (addPlayerResult == null) {
                    currentState.copy(
                        playerBeingAdded = "",
                        playersList = playersListRepository.readPlayer() - activePlayersList.filter { it != "" }.toSet(),
                        nameError = null,
                        isDialogShown = false
                    )
                } else {
                    currentState.copy(
                        nameError = addPlayerResult.nameError,
                        isDialogShown = true
                    )
                }
            }
        }
    }

    fun onTypeNewPlayer(playerName: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newName = if (playerName.length <= 10) playerName else currentState.playerBeingAdded
                currentState.copy(
                    playerBeingAdded = newName,
                )
            }
        }
    }

    fun getPlayerIndexBeingSelected() : String {
        return playerIndexBeingSelected
    }

    fun getActivePlayersList() : List<String> {
        return activePlayersList
    }

    fun onFloatingButtonClick() {
        _uiState.update {  currentState ->
            currentState.copy(
                isDialogShown = true,
                playerBeingAdded = ""
            )
        }
    }

    fun onDismissDialog() {
        _uiState.update {  currentState ->
            currentState.copy(
                isDialogShown = false
            )
        }
    }

    fun onDeletePlayer(name: String) {
        viewModelScope.launch {
            playersListRepository.deletePlayer(name)
            _uiState.update { currentState ->
                currentState.copy(
                    playersList = playersListRepository.readPlayer() - activePlayersList.filter { it != "" }.toSet(),
                )
            }
        }
    }

}

