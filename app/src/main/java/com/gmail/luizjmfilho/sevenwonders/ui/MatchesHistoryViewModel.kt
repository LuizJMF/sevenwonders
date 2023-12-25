package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gmail.luizjmfilho.sevenwonders.data.MatchesHistoryRepository
import com.gmail.luizjmfilho.sevenwonders.model.Match
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesHistoryViewModel @Inject constructor(
    private val matchesHistoryRepository: MatchesHistoryRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(MatchesHistoryUiState(emptyList(), 1))
    val uiState: StateFlow<MatchesHistoryUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    matchQuantity = if (matchesHistoryRepository.getLastMatchId() == null) 0 else matchesHistoryRepository.getLastMatchId()!!,
                    playerInfoList = if (matchesHistoryRepository.selectAllMatches() == null) emptyList() else matchesHistoryRepository.selectAllMatches()!!,
                )
            }
        }
    }

    fun onDeleteMatchWhoseIdIs(idMatch: Int) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                matchesHistoryRepository.deleteMatchWhoseIdIs(idMatch)
                matchesHistoryRepository.updateAllMatchesId(idMatch)
                currentState.copy(
                    matchQuantity = if (matchesHistoryRepository.getLastMatchId() == null) 0 else matchesHistoryRepository.getLastMatchId()!!,
                    playerInfoList = if (matchesHistoryRepository.selectAllMatches() == null) emptyList() else matchesHistoryRepository.selectAllMatches()!!,
                )
            }
        }
    }
}