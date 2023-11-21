package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.model.PlayerDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _playerNicknamesInThePassedOrder: List<String> = savedStateHandle.get<String>("playerNicknames")!!.split(",") - listOf("")
    val playerNicknamesInThePassedOrder = _playerNicknamesInThePassedOrder
    private val _uiState = MutableStateFlow(MatchDetailsUiState())
    val uiState: StateFlow<MatchDetailsUiState> = _uiState.asStateFlow()


    fun onConfirmMethod(positionMethod: RaffleOrChoose, wonderMethod: RaffleOrChoose) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val creationMethod = when (positionMethod) {
                    RaffleOrChoose.Raffle -> {
                        when (wonderMethod) {
                            RaffleOrChoose.Raffle -> CreationMethod.AllRaffle
                            RaffleOrChoose.Choose -> CreationMethod.RafflePositionChooseWonder
                        }
                    }
                    RaffleOrChoose.Choose -> {
                        when (wonderMethod) {
                            RaffleOrChoose.Raffle -> CreationMethod.ChoosePositionRaffleWonder
                            RaffleOrChoose.Choose -> CreationMethod.AllChoose
                        }
                    }
                }

                val playersNicknames = when (positionMethod) {
                    RaffleOrChoose.Choose -> _playerNicknamesInThePassedOrder
                    RaffleOrChoose.Raffle -> _playerNicknamesInThePassedOrder.shuffled()
                }

                val playersWonders = when (wonderMethod) {
                    RaffleOrChoose.Choose -> List(_playerNicknamesInThePassedOrder.size) { null }
                    RaffleOrChoose.Raffle -> Wonders.values().toList().shuffled().take(_playerNicknamesInThePassedOrder.size)
                }

                val matchPlayerDetails = List(_playerNicknamesInThePassedOrder.size) { index ->
                    val wonderSide = when (wonderMethod) {
                        RaffleOrChoose.Choose -> null
                        RaffleOrChoose.Raffle -> WonderSide.Day
                    }
                    PlayerDetails(playersNicknames[index], playersWonders[index], wonderSide)
                }

                val isAdvanceButtonEnabled = (wonderMethod == RaffleOrChoose.Raffle)

                currentState.copy(
                    creationMethod = creationMethod,
                    matchPlayersDetails = matchPlayerDetails,
                    isAdvanceButtonEnabled = isAdvanceButtonEnabled
                )
            }
        }
    }

    fun onWonderSideChange(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val otherSide = when (currentState.matchPlayersDetails[index].wonderSide) {
                    WonderSide.Day -> WonderSide.Night
                    WonderSide.Night -> WonderSide.Day
                    null -> null // esse caso nunca vai ocorrer, mas precisei tratá-lo pra não dar erro na variável newSide logo abaixo.
                }

                val refreshedList = currentState.matchPlayersDetails.toMutableList()
                val newSide = refreshedList[index].copy(wonderSide = otherSide)
                refreshedList[index] = newSide

                currentState.copy(
                    matchPlayersDetails = refreshedList
                )
            }
        }
    }

    fun onChooseWonderClick() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                var currentUsedWonders = currentState.matchPlayersDetails.map { playerDetails ->
                    playerDetails.wonder
                }

                currentUsedWonders = currentUsedWonders - listOf(null)

                val allWonders = Wonders.values().toList()

                val availableWondersList = allWonders - currentUsedWonders

                currentState.copy(
                    availableWondersList = availableWondersList
                )
            }
        }
    }

    fun onSelectWonderInDialog(wonder: Wonders, index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->

                val matchPlayersDetails = currentState.matchPlayersDetails.toMutableList()
                val newInfo = matchPlayersDetails[index].copy(
                    wonder = wonder,
                    wonderSide = WonderSide.Day)
                matchPlayersDetails[index] = newInfo

                val isAdvanceButtonEnabled = !matchPlayersDetails.map { it.wonder }.contains(null)

                currentState.copy(
                    matchPlayersDetails = matchPlayersDetails,
                    isAdvanceButtonEnabled = isAdvanceButtonEnabled
                )
            }
        }
    }

    fun onDeselectWonder(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->

                val matchPlayersDetails = currentState.matchPlayersDetails.toMutableList()
                val newInfo = matchPlayersDetails[index].copy(
                    wonder = null,
                    wonderSide = null)
                matchPlayersDetails[index] = newInfo

                currentState.copy(
                    matchPlayersDetails = matchPlayersDetails,
                    isAdvanceButtonEnabled = false
                )
            }
        }
    }

    fun onMoveCardDown(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val maxIndex = currentState.matchPlayersDetails.size - 1
                val matchPlayersDetails = currentState.matchPlayersDetails.toMutableList()
                val playerClicked = currentState.matchPlayersDetails[index]
                val playerBelow = if(index != maxIndex) {
                    currentState.matchPlayersDetails[index + 1]
                } else {
                    currentState.matchPlayersDetails[0]
                }

                matchPlayersDetails[index] = playerBelow
                if (index != maxIndex) {
                    matchPlayersDetails[index + 1] = playerClicked
                } else {
                    matchPlayersDetails[0] = playerClicked
                }

                currentState.copy(
                    matchPlayersDetails = matchPlayersDetails,
                )
            }
        }
    }
}