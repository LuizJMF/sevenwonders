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
class CalculationViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {



    private val _playerDetailsOfPreviousScreen: List<PlayerDetails> = savedStateHandle.get<String>("playerDetailsList")!!.split(";").map {
        val lista = it.split(",")
        PlayerDetails(
            nickname = lista[0].drop(23),
            wonder = Wonders.valueOf(lista[1].drop(8)),
            wonderSide = WonderSide.valueOf(lista[2].drop(12).dropLast(1)),
        )
    }
    val playerDetailsOfPreviousScreen = _playerDetailsOfPreviousScreen
    private val _uiState = MutableStateFlow(CalculationUiState())
    val uiState: StateFlow<CalculationUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    playersList = _playerDetailsOfPreviousScreen.map { it.nickname },
                    totalScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                    wonderBoardScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                    coinScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                    warScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                    blueCardScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                    yellowCardScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                    greenCardScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                    purpleCardScoreList = List(_playerDetailsOfPreviousScreen.size) { 0 },
                )
            }
        }
    }

    fun onPreviousCategory() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val previousCategoryValue = currentState.currentCategory.ordinal - 1
                val enumList = PointCategory.values()
                currentState.copy(
                    currentCategory = enumList[previousCategoryValue]
                )
            }
        }
    }

    fun onNextCategory() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val nextCategoryValue = currentState.currentCategory.ordinal + 1
                val enumList = PointCategory.values()
                currentState.copy(
                    currentCategory = enumList[nextCategoryValue]
                )
            }
        }
    }

    fun onMinusOnePointClick(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val searchedList = when(currentState.currentCategory) {
                    PointCategory.WonderBoard -> currentState.wonderBoardScoreList.toMutableList()
                    PointCategory.Coin -> currentState.coinScoreList.toMutableList()
                    PointCategory.War -> currentState.warScoreList.toMutableList()
                    PointCategory.BlueCard -> currentState.blueCardScoreList.toMutableList()
                    PointCategory.YellowCard -> currentState.yellowCardScoreList.toMutableList()
                    PointCategory.GreenCard -> currentState.greenCardScoreList.toMutableList()
                    PointCategory.PurpleCard -> currentState.purpleCardScoreList.toMutableList()
                }
                val totalScoreList = currentState.totalScoreList.toMutableList()

                searchedList[index]--
                totalScoreList[index]--

                when(currentState.currentCategory) {
                    PointCategory.WonderBoard -> { currentState.copy(wonderBoardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.Coin -> { currentState.copy(coinScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.War -> { currentState.copy(warScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.BlueCard -> { currentState.copy(blueCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.YellowCard -> { currentState.copy(yellowCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.GreenCard -> { currentState.copy(greenCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.PurpleCard -> { currentState.copy(purpleCardScoreList = searchedList, totalScoreList = totalScoreList)}

                }

            }
        }
    }

    fun onPlusTwoPointsClick(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val searchedList = when(currentState.currentCategory) {
                    PointCategory.WonderBoard -> currentState.wonderBoardScoreList.toMutableList()
                    PointCategory.Coin -> currentState.coinScoreList.toMutableList()
                    PointCategory.War -> currentState.warScoreList.toMutableList()
                    PointCategory.BlueCard -> currentState.blueCardScoreList.toMutableList()
                    PointCategory.YellowCard -> currentState.yellowCardScoreList.toMutableList()
                    PointCategory.GreenCard -> currentState.greenCardScoreList.toMutableList()
                    PointCategory.PurpleCard -> currentState.purpleCardScoreList.toMutableList()
                }
                val totalScoreList = currentState.totalScoreList.toMutableList()

                searchedList[index] += 2
                totalScoreList[index] += 2

                when(currentState.currentCategory) {
                    PointCategory.WonderBoard -> { currentState.copy(wonderBoardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.Coin -> { currentState.copy(coinScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.War -> { currentState.copy(warScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.BlueCard -> { currentState.copy(blueCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.YellowCard -> { currentState.copy(yellowCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.GreenCard -> { currentState.copy(greenCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.PurpleCard -> { currentState.copy(purpleCardScoreList = searchedList, totalScoreList = totalScoreList)}

                }

            }
        }
    }

    fun onPlusOnePointsClick(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val searchedList = when(currentState.currentCategory) {
                    PointCategory.WonderBoard -> currentState.wonderBoardScoreList.toMutableList()
                    PointCategory.Coin -> currentState.coinScoreList.toMutableList()
                    PointCategory.War -> currentState.warScoreList.toMutableList()
                    PointCategory.BlueCard -> currentState.blueCardScoreList.toMutableList()
                    PointCategory.YellowCard -> currentState.yellowCardScoreList.toMutableList()
                    PointCategory.GreenCard -> currentState.greenCardScoreList.toMutableList()
                    PointCategory.PurpleCard -> currentState.purpleCardScoreList.toMutableList()
                }
                val totalScoreList = currentState.totalScoreList.toMutableList()

                searchedList[index] += 1
                totalScoreList[index] += 1

                when(currentState.currentCategory) {
                    PointCategory.WonderBoard -> { currentState.copy(wonderBoardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.Coin -> { currentState.copy(coinScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.War -> { currentState.copy(warScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.BlueCard -> { currentState.copy(blueCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.YellowCard -> { currentState.copy(yellowCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.GreenCard -> { currentState.copy(greenCardScoreList = searchedList, totalScoreList = totalScoreList)}
                    PointCategory.PurpleCard -> { currentState.copy(purpleCardScoreList = searchedList, totalScoreList = totalScoreList)}

                }

            }
        }
    }

    fun onShowTotalGrid() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    subScreen = CalculationSubScreen.TotalGrid
                )
            }
        }
    }

    fun onShowPartialGrid() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    subScreen = CalculationSubScreen.ParcialGrid
                )
            }
        }
    }

    fun onShowSciendGrid() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    subScreen = CalculationSubScreen.ScienceGrid,
                    scienceSymbolsCurrentQuantityList = List(3) {0}
                )
            }
        }
    }

    fun onMinusOneScienceCard(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newList = currentState.scienceSymbolsCurrentQuantityList.toMutableList()
                newList[index] = if (newList[index] > 0) {
                    newList[index] - 1
                } else {
                    newList[index]
                }
                currentState.copy(
                    scienceSymbolsCurrentQuantityList = newList
                )
            }
        }
    }

    fun onPlusOneScienceCard(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newList = currentState.scienceSymbolsCurrentQuantityList.toMutableList()
                newList[index]++

                currentState.copy(
                    scienceSymbolsCurrentQuantityList = newList
                )
            }
        }
    }

    fun onScienceGridConfirm(name: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val compassQuantity = currentState.scienceSymbolsCurrentQuantityList[0]
                val stoneQuantity = currentState.scienceSymbolsCurrentQuantityList[1]
                val gearQuantity = currentState.scienceSymbolsCurrentQuantityList[2]

                val compassQuantityPoints = compassQuantity * compassQuantity
                val stoneQuantityPoints = stoneQuantity * stoneQuantity
                val gearQuantityPoints = gearQuantity * gearQuantity
                val combinationPoints = if (compassQuantity == 0 || stoneQuantity == 0 || gearQuantity == 0) {
                    0
                } else if (compassQuantity >= 5 && stoneQuantity >= 5 && gearQuantity >= 5 ) {
                    35
                } else if (compassQuantity >= 4 && stoneQuantity >= 4 && gearQuantity >= 4 ) {
                    28
                } else if (compassQuantity >= 3 && stoneQuantity >= 3 && gearQuantity >= 3 ) {
                    21
                } else if (compassQuantity >= 2 && stoneQuantity >= 2 && gearQuantity >= 2 ) {
                    14
                } else {
                    7
                }

                val sciencePoints = compassQuantityPoints + stoneQuantityPoints + gearQuantityPoints + combinationPoints

                val scienceList = currentState.greenCardScoreList.toMutableList()
                val previousSciencePoints = scienceList[currentState.playersList.indexOf(name)]
                    scienceList[currentState.playersList.indexOf(name)] = sciencePoints

                val totalList = currentState.totalScoreList.toMutableList()
                totalList[currentState.playersList.indexOf(name)] = (totalList[currentState.playersList.indexOf(name)] - previousSciencePoints + sciencePoints)

                currentState.copy(
                    greenCardScoreList = scienceList,
                    totalScoreList = totalList,
                    scienceSymbolsCurrentQuantityList = List(3) {0},
                    subScreen = CalculationSubScreen.ParcialGrid
                )
            }
        }
    }

}