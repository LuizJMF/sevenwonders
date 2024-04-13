package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScienceSimulatorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ScienceSimulatorUiState())
    val uiState = _uiState.asStateFlow()

    fun onDecreaseSymbol(symbol: ScienceSymbol) {
        _uiState.update {  currentState ->
            var value = 0
            when(symbol) {
                ScienceSymbol.Compass -> {
                    value = currentState.compassQuantity
                    if (currentState.compassQuantity != 0) --value
                    currentState.copy(
                        compassQuantity = value,
                        totalScore = calculateTotalPoints(
                            compassQuantity = value,
                            stoneQuantity = currentState.stoneQuantity,
                            gearQuantity = currentState.gearQuantity
                        )
                    )
                }
                ScienceSymbol.Stone -> {
                    value = currentState.stoneQuantity
                    if (currentState.stoneQuantity != 0) --value
                    currentState.copy(
                        stoneQuantity = value,
                        totalScore = calculateTotalPoints(
                            compassQuantity = currentState.compassQuantity,
                            stoneQuantity = value,
                            gearQuantity = currentState.gearQuantity
                        )
                    )
                }
                ScienceSymbol.Gear -> {
                    value = currentState.gearQuantity
                    if (currentState.gearQuantity != 0) --value
                    currentState.copy(
                        gearQuantity = value,
                        totalScore = calculateTotalPoints(
                            compassQuantity = currentState.compassQuantity,
                            stoneQuantity = currentState.stoneQuantity,
                            gearQuantity = value
                        )
                    )
                }
            }
        }
    }

    fun onIncreaseSymbol(symbol: ScienceSymbol) {

        _uiState.update {  currentState ->
            var value = 0
            when(symbol) {
                ScienceSymbol.Compass -> {
                    value = currentState.compassQuantity
                    currentState.copy(
                        compassQuantity = ++value,
                        totalScore = calculateTotalPoints(
                            compassQuantity = value,
                            stoneQuantity = currentState.stoneQuantity,
                            gearQuantity = currentState.gearQuantity
                        )
                    )
                }
                ScienceSymbol.Stone -> {
                    value = currentState.stoneQuantity
                    currentState.copy(
                        stoneQuantity = ++value,
                        totalScore = calculateTotalPoints(
                            compassQuantity = currentState.compassQuantity,
                            stoneQuantity = value,
                            gearQuantity = currentState.gearQuantity
                        )
                    )
                }
                ScienceSymbol.Gear -> {
                    value = currentState.gearQuantity
                    currentState.copy(
                        gearQuantity = ++value,
                        totalScore = calculateTotalPoints(
                            compassQuantity = currentState.compassQuantity,
                            stoneQuantity = currentState.stoneQuantity,
                            gearQuantity = value
                        )
                    )
                }
            }
        }
    }

    fun calculateTotalPoints(
        compassQuantity: Int,
        stoneQuantity: Int,
        gearQuantity: Int,
    ): Int {
        val compassQuantityPoints = compassQuantity * compassQuantity
        val stoneQuantityPoints = stoneQuantity * stoneQuantity
        val gearQuantityPoints = gearQuantity * gearQuantity
        val combinationPoints =
            if (compassQuantity == 0 || stoneQuantity == 0 || gearQuantity == 0) {
                0
            } else if (compassQuantity >= 5 && stoneQuantity >= 5 && gearQuantity >= 5) {
                35
            } else if (compassQuantity >= 4 && stoneQuantity >= 4 && gearQuantity >= 4) {
                28
            } else if (compassQuantity >= 3 && stoneQuantity >= 3 && gearQuantity >= 3) {
                21
            } else if (compassQuantity >= 2 && stoneQuantity >= 2 && gearQuantity >= 2) {
                14
            } else {
                7
            }

        return compassQuantityPoints + stoneQuantityPoints + gearQuantityPoints + combinationPoints
    }

}