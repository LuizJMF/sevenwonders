package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ScienceSimulatorViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(ScienceSimulatorUiState())
    val uiState = _uiState.asStateFlow()

    fun onQuantityChange(symbol: ScienceSymbol, quantity: Int) {
        if (quantity < 0) {
            return
        }

        when (symbol) {
            ScienceSymbol.Compass -> onCompassQuantityChange(quantity)
            ScienceSymbol.Stone -> onStoneQuantityChange(quantity)
            ScienceSymbol.Gear -> onGearQuantityChange(quantity)
        }
    }

    private fun onCompassQuantityChange(quantity: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                compassQuantity = quantity,
                totalScore = calculateTotalScore(compassQuantity = quantity),
            )
        }
    }

    private fun onStoneQuantityChange(quantity: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                stoneQuantity = quantity,
                totalScore = calculateTotalScore(stoneQuantity = quantity),
            )
        }
    }

    private fun onGearQuantityChange(quantity: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                gearQuantity = quantity,
                totalScore = calculateTotalScore(gearQuantity = quantity),
            )
        }
    }

    private fun calculateTotalScore(
        compassQuantity: Int = _uiState.value.compassQuantity,
        stoneQuantity: Int = _uiState.value.stoneQuantity,
        gearQuantity: Int = _uiState.value.gearQuantity,
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