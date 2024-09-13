package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.data.StatsRepository
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.perf.ktx.performance
import com.google.firebase.perf.metrics.Trace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

inline fun <T> firebasePerformanceTrace(name: String, block: (Trace) -> T): T {
    val trace = Firebase.performance.newTrace(name)

    trace.start()
    val result = block(trace)
    trace.stop()

    return result
}

@HiltViewModel
class StatsViewModel @Inject constructor(
    private val statsRepository: StatsRepository,
    firebaseAnalytics: FirebaseAnalytics,
): TrackedScreenViewModel(firebaseAnalytics, "Stats") {

    private val _uiState = MutableStateFlow(StatsUiState())
    val uiState: StateFlow<StatsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            firebasePerformanceTrace("stats_calculation") { trace ->
                _uiState.update { currentState ->
                    val isDatabaseEmpty = (statsRepository.getAverageWinnerScore() == null)
                    val bestScoreList = if (isDatabaseEmpty) emptyList() else statsRepository.getBestScoreList()
                    val worstScoreList = if (isDatabaseEmpty) emptyList() else statsRepository.getWorstScoreList()
                    val bestScoresPerPlayerList = if (isDatabaseEmpty) emptyList() else statsRepository.getBestScoresPerPlayerList()?.sortedByDescending { it.totalScore }
                    val worstScoresPerPlayerList = if (isDatabaseEmpty) emptyList() else statsRepository.getWorstScoresPerPlayerList()?.sortedBy { it.totalScore }
                    val averageWinnerScore = if (isDatabaseEmpty) 0 else statsRepository.getAverageWinnerScore()
                    val averageScorePerPlayer = if (isDatabaseEmpty) emptyList() else statsRepository.getAverageScorePerPlayer()?.sortedByDescending { it.second }

                    val numberOfVictoriesPerPlayer = if (isDatabaseEmpty) mapOf() else statsRepository.selectAllMatches()?.filter { it.position == 1 }?.groupingBy { it.nickname }?.eachCount()
                    val mostAbsoluteChampionList = if (isDatabaseEmpty) emptyList() else numberOfVictoriesPerPlayer?.filter { it.value == numberOfVictoriesPerPlayer.values.maxOrNull() }?.toList()
                    val numberOfMatchesPerPlayer = if (isDatabaseEmpty) mapOf() else statsRepository.selectAllMatches()?.groupingBy { it.nickname }?.eachCount()
                    val ratioOfVictoriesPerPlayer = if (isDatabaseEmpty) mapOf() else numberOfVictoriesPerPlayer?.mapValues { (nickname, contagem) ->
                            val totalMatches = numberOfMatchesPerPlayer?.get(nickname) ?: 0
                            if (totalMatches > 0) {
                                (contagem.toDouble() / totalMatches) * 100
                            } else {
                                0.0
                            }
                        }?.mapValues { it.value.toInt() }

                    val blueList = if (isDatabaseEmpty) emptyList() else statsRepository.getBlueRecordsList()?.map { it.nickname to it.blueCardScore }
                    val yellowList = if (isDatabaseEmpty) emptyList() else statsRepository.getYellowRecordsList()?.map { it.nickname to it.yellowCardScore }
                    val greenList = if (isDatabaseEmpty) emptyList() else statsRepository.getGreenRecordsList()?.map { it.nickname to it.greenCardScore }
                    val purpleList = if (isDatabaseEmpty) emptyList() else statsRepository.getPurpleRecordsList()?.map { it.nickname to it.purpleCardScore }

                    val bestWondersList = if (isDatabaseEmpty) emptyList() else statsRepository.getBestWondersList()

                    currentState.copy(
                        isDatabaseEmpty = isDatabaseEmpty,
                        bestScoresList = bestScoreList!!,
                        worstScoresList = worstScoreList!!,
                        bestScoresPerPlayerList = bestScoresPerPlayerList!!,
                        worstScoresPerPlayerList = worstScoresPerPlayerList!!,
                        averageWinnerScore = averageWinnerScore!!,
                        averageScorePerPlayer = averageScorePerPlayer!!,
                        mostAbsoluteChampionList = mostAbsoluteChampionList!!,
                        mostRelativeChampionList = ratioOfVictoriesPerPlayer?.filter { it.value == ratioOfVictoriesPerPlayer.values.maxOrNull() }?.toList()!!,
                        allAbsoluteVictoriesList = numberOfVictoriesPerPlayer?.toList()?.sortedByDescending { it.second }!!,
                        allRelativeVictoriesList = ratioOfVictoriesPerPlayer.toList().sortedByDescending { it.second },
                        blueList = blueList!!,
                        yellowList = yellowList!!,
                        greenList = greenList!!,
                        purpleList = purpleList!!,
                        bestWondersList = bestWondersList!!,
                        isLoading = false
                    )
                }

                val matchCount = statsRepository.selectAllMatches()?.size ?: 0
                trace.putMetric("match_count", matchCount.toLong())
            }
        }
    }
}