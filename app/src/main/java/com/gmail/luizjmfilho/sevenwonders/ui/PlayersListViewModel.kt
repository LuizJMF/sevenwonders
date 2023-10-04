package com.gmail.luizjmfilho.sevenwonders.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gmail.luizjmfilho.sevenwonders.data.PersonDao
import com.gmail.luizjmfilho.sevenwonders.data.PlayersListRepository
import com.gmail.luizjmfilho.sevenwonders.data.getSevenWondersDatabaseInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PlayersListViewModel(
    private val playersListRepository: PlayersListRepository
) : ViewModel() {


    private val _uiState = MutableStateFlow(PlayersListUiState())
    val uiState: StateFlow<PlayersListUiState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(
                    playersList = playersListRepository.readPlayer(),
                )
            }
        }
    }

    fun updateName(novoNome: String){
        _uiState.update { currentState ->
            currentState.copy(
                name = novoNome,
                nameError = null,
            )
        }
    }

    fun updateNickname(novoApelido: String){
        _uiState.update { currentState ->
            currentState.copy(
                nickname = novoApelido,
            )
        }
    }

    fun cancelAddPlayer(){
        _uiState.update { currentState ->
            currentState.copy(
                name = "",
                nickname = "",
                nameError = null,
                nicknameError = null,
            )
        }
    }


    fun onConfirmAddPlayerClick () {
        viewModelScope.launch {
            _uiState.update { currentState ->
                val addPlayerResult = playersListRepository.addPlayer(currentState.name, currentState.nickname)
                if (addPlayerResult == null) {
                    currentState.copy(
                        name = "",
                        nickname = "",
                        playersList = playersListRepository.readPlayer(),
                        nameError = null,
                        nicknameError = null,
                    )
                } else {
                    currentState.copy(
                        nameError = addPlayerResult.nameError,
                        nicknameError = addPlayerResult.nicknameError
                    )
                }
            }
        }
    }

    fun deletePlayer (name: String) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                try {
                    playersListRepository.deletePlayer(name)
                    val x = playersListRepository.readPlayer()
                    currentState.copy(
                        playersList = x,
                    )
                } catch (e: Exception) {
                    println("Deu erro: ${e.message}")
                    currentState
                }
            }
        }
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                val context = get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY)!!
                val database = getSevenWondersDatabaseInstance(context)
                val dao: PersonDao = database.personDao()
                val repository = PlayersListRepository(dao)
                PlayersListViewModel(repository)
            }
        }
    }

}

