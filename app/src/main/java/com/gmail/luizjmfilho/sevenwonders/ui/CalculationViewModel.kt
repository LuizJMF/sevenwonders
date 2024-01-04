package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.data.CalculationRepository
import com.gmail.luizjmfilho.sevenwonders.model.Match
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
    private val calculationRepository: CalculationRepository,
) : ViewModel() {



    private val _playerDetailsOfPreviousScreen: List<PlayerDetails> = savedStateHandle.get<String>("playerDetailsList")!!.split(";").map {
        val list = it.split(",")
        PlayerDetails(
            nickname = list[0].drop(23),
            wonder = Wonders.valueOf(list[1].drop(8)),
            wonderSide = WonderSide.valueOf(list[2].drop(12).dropLast(1)),
        )
    }
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
                    coinQuantityList = List(_playerDetailsOfPreviousScreen.size) { 0 },
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
                    PointCategory.Coin -> currentState.coinScoreList.toMutableList() // em teoria esse caso ñ ocorrerá, pois na Coin não tem esse botão, e sim o "calcular".
                    PointCategory.War -> currentState.warScoreList.toMutableList()
                    PointCategory.BlueCard -> currentState.blueCardScoreList.toMutableList()
                    PointCategory.YellowCard -> currentState.yellowCardScoreList.toMutableList()
                    PointCategory.GreenCard -> currentState.greenCardScoreList.toMutableList() // em teoria esse caso ñ ocorrerá, pois na GreenCard não tem esse botão, e sim o "calcular".
                    PointCategory.PurpleCard -> currentState.purpleCardScoreList.toMutableList()
                }
                val totalScoreList = currentState.totalScoreList.toMutableList()

                if (currentState.currentCategory != PointCategory.War) {
                    if (searchedList[index] > 0) {
                        searchedList[index]--
                        totalScoreList[index]--
                    }
                } else {
                    if (searchedList[index] > -6) {
                        searchedList[index]--
                        totalScoreList[index]--
                    }
                }



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
                    PointCategory.Coin -> currentState.coinScoreList.toMutableList() // em teoria esse caso ñ ocorrerá, pois na Coin não tem esse botão, e sim o "calcular".
                    PointCategory.War -> currentState.warScoreList.toMutableList()
                    PointCategory.BlueCard -> currentState.blueCardScoreList.toMutableList()
                    PointCategory.YellowCard -> currentState.yellowCardScoreList.toMutableList()
                    PointCategory.GreenCard -> currentState.greenCardScoreList.toMutableList() // em teoria esse caso ñ ocorrerá, pois na GreenCard não tem esse botão, e sim o "calcular".
                    PointCategory.PurpleCard -> currentState.purpleCardScoreList.toMutableList()
                }
                val totalScoreList = currentState.totalScoreList.toMutableList()

                if (currentState.currentCategory != PointCategory.War) {
                    searchedList[index] += 1
                    totalScoreList[index] += 1
                } else {
                    if (searchedList[index] < 18) {
                        searchedList[index] += 1
                        totalScoreList[index] += 1
                    }
                }


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

    fun onShowCoinGrid(index: Int, previousCoinQuantity: Int, previousCoinScore: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newCoinQuantityList = currentState.coinQuantityList.toMutableList()
                newCoinQuantityList[index] = when (previousCoinQuantity) {
                    in 0..2 -> if (previousCoinScore == 0) newCoinQuantityList[index] else 0
                    in 3..5 -> if (previousCoinScore == 1) newCoinQuantityList[index] else 0
                    in 6..8 -> if (previousCoinScore == 2) newCoinQuantityList[index] else 0
                    in 9..11 -> if (previousCoinScore == 3) newCoinQuantityList[index] else 0
                    in 12..14 -> if (previousCoinScore == 4) newCoinQuantityList[index] else 0
                    in 15..17 -> if (previousCoinScore == 5) newCoinQuantityList[index] else 0
                    in 18..20 -> if (previousCoinScore == 6) newCoinQuantityList[index] else 0
                    in 21..23 -> if (previousCoinScore == 7) newCoinQuantityList[index] else 0
                    in 24..26 -> if (previousCoinScore == 8) newCoinQuantityList[index] else 0
                    in 27..29 -> if (previousCoinScore == 9) newCoinQuantityList[index] else 0
                    in 30..32 -> if (previousCoinScore == 10) newCoinQuantityList[index] else 0
                    in 33..35 -> if (previousCoinScore == 11) newCoinQuantityList[index] else 0
                    in 36..38 -> if (previousCoinScore == 12) newCoinQuantityList[index] else 0
                    in 39..41 -> if (previousCoinScore == 13) newCoinQuantityList[index] else 0
                    in 42..44 -> if (previousCoinScore == 14) newCoinQuantityList[index] else 0
                    in 45..47 -> if (previousCoinScore == 15) newCoinQuantityList[index] else 0
                    in 48..50 -> if (previousCoinScore == 16) newCoinQuantityList[index] else 0
                    else -> 0
                }

                currentState.copy(
                    subScreen = CalculationSubScreen.CoinGrid,
                    coinQuantityList = newCoinQuantityList
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

    fun onMinusOneCoinQuantity(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newList = currentState.coinQuantityList.toMutableList()
                if (newList[index] > 0) {
                    newList[index]--
                }

                currentState.copy(
                    coinQuantityList = newList
                )
            }
        }
    }

    fun onPlusOneCoinQuantity(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newList = currentState.coinQuantityList.toMutableList()
                newList[index]++
                currentState.copy(
                    coinQuantityList = newList
                )
            }
        }
    }

    fun onPlusTwoCoinsQuantity(index: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newList = currentState.coinQuantityList.toMutableList()
                newList[index] += 2
                currentState.copy(
                    coinQuantityList = newList
                )
            }
        }
    }

    fun onCoinGridConfirm(playerNickname: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val newCoinScoreList = currentState.coinScoreList.toMutableList()
                val newTotalScoreList = currentState.totalScoreList.toMutableList()
                val previousCoinScore = currentState.coinScoreList[currentState.playersList.indexOf(playerNickname)]
                val coinsQuantity = currentState.coinQuantityList[currentState.playersList.indexOf(playerNickname)]
                val coinsPoint = coinsQuantity/3

                newCoinScoreList[currentState.playersList.indexOf(playerNickname)] = coinsPoint
                newTotalScoreList[currentState.playersList.indexOf(playerNickname)] = newTotalScoreList[currentState.playersList.indexOf(playerNickname)] - previousCoinScore + coinsPoint

                currentState.copy(
                    coinScoreList = newCoinScoreList,
                    totalScoreList = newTotalScoreList,
                    subScreen = CalculationSubScreen.ParcialGrid
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

    fun addPlayerMatchInfo(dateAndTime: String,) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val matchId = if (calculationRepository.getLastMatchId() == null) 1 else calculationRepository.getLastMatchId()!! + 1

                val nicknameList = currentState.playersList
                val totalScoreList = currentState.totalScoreList
                val coinQuantityList = currentState.coinQuantityList

                val sortedNicknameList = nicknameList
                    .zip(totalScoreList)
                    .zip(coinQuantityList)
                    .sortedWith(compareByDescending<Pair<Pair<String, Int>, Int>> {
                        it.first.second
                    }.thenByDescending {
                        it.second
                    })
                    .map { it.first.first }

                for (i in 0..< currentState.playersList.size) {
                    val match = Match(
                        matchId = matchId,
                        position = sortedNicknameList.indexOf(currentState.playersList[i]) + 1,
                        dataAndTime = dateAndTime,
                        nickname = _playerDetailsOfPreviousScreen.map { it.nickname }[i],
                        wonder = _playerDetailsOfPreviousScreen.map { it.wonder }[i]!!,
                        wonderSide = _playerDetailsOfPreviousScreen.map { it.wonderSide }[i]!!,
                        totalScore = currentState.totalScoreList[i],
                        wonderBoardScore = currentState.wonderBoardScoreList[i],
                        coinScore = currentState.coinScoreList[i],
                        coinQuantity = currentState.coinQuantityList[i],
                        warScore = currentState.warScoreList[i],
                        blueCardScore = currentState.blueCardScoreList[i],
                        yellowCardScore = currentState.yellowCardScoreList[i],
                        greenCardScore = currentState.greenCardScoreList[i],
                        purpleCardScore = currentState.purpleCardScoreList[i],
                    )
                    calculationRepository.addPlayerMatchInfo(match)
                }
                currentState.copy()
            }
        }
    }

    fun deleteMatch() {
        viewModelScope.launch {
            _uiState.update { currentState ->
                calculationRepository.deleteLastMatch()
                currentState.copy()
            }
        }
    }

}