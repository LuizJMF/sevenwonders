package com.gmail.luizjmfilho.sevenwonders.ui

import com.google.firebase.analytics.FirebaseAnalytics
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ScienceSimulatorViewModel @Inject constructor(
    firebaseAnalytics: FirebaseAnalytics,
): TrackedScreenViewModel(firebaseAnalytics, "ScienceSimulator") {

    private val _uiState = MutableStateFlow(ScienceSimulatorUiState())
    val uiState = _uiState.asStateFlow()

    fun onScienceCountChange(symbol: ScienceSymbol, count: Int) {
        if (count < 0) {
            return
        }

        when (symbol) {
            ScienceSymbol.Compass -> onCompassCountChange(count)
            ScienceSymbol.Stone -> onStoneCountChange(count)
            ScienceSymbol.Gear -> onGearCountChange(count)
        }
    }

    private fun onCompassCountChange(count: Int) {
        val newScore = calculateScore(compassCount = count)

        _uiState.update { currentState ->
            currentState.copy(
                compassCount = count,
                score = newScore,
            )
        }
    }

    private fun onStoneCountChange(count: Int) {
        val newScore = calculateScore(stoneCount = count)

        _uiState.update { currentState ->
            currentState.copy(
                stoneCount = count,
                score = newScore,
            )
        }
    }

    private fun onGearCountChange(count: Int) {
        val newScore = calculateScore(gearCount = count)

        _uiState.update { currentState ->
            currentState.copy(
                gearCount = count,
                score = newScore,
            )
        }
    }

    private fun calculateScore(
        compassCount: Int = _uiState.value.compassCount,
        stoneCount: Int = _uiState.value.stoneCount,
        gearCount: Int = _uiState.value.gearCount,
    ): Int {
        val compassScore = compassCount * compassCount
        val stoneStore = stoneCount * stoneCount
        val gearScore = gearCount * gearCount
        val combinationScore =
            when {
                compassCount == 0 || stoneCount == 0 || gearCount == 0 -> 0
                compassCount >= 5 && stoneCount >= 5 && gearCount >= 5 -> 35
                compassCount >= 4 && stoneCount >= 4 && gearCount >= 4 -> 28
                compassCount >= 3 && stoneCount >= 3 && gearCount >= 3 -> 21
                compassCount >= 2 && stoneCount >= 2 && gearCount >= 2 -> 14
                else -> 7
            }

        return compassScore + stoneStore + gearScore + combinationScore
    }
}