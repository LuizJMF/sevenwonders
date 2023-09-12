package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Pessoa
import com.gmail.luizjmfilho.sevenwonders.ui.NameOrNicknameError

data class AddPlayerResult(
    val nameError: NameOrNicknameError?,
    val nicknameError: NameOrNicknameError?,
)

class PlayersListRepository(database: SevenWondersDatabase) {

    private val playersListDao: PlayersListDao = database.playersListDao()

    suspend fun addPlayer(name: String, nickname: String): AddPlayerResult? {
        val nameWithoutSpace = name.trim()
        val nameError: NameOrNicknameError? = if (nameWithoutSpace == "") {
            NameOrNicknameError.Empty
        } else if (playersListDao.verifyIfNameExists(nameWithoutSpace) > 0) {
            NameOrNicknameError.Exists
        } else {
            null
        }

        val nicknameWithoutSpace = nickname.trim()
        val nicknameError: NameOrNicknameError? = if (nicknameWithoutSpace == "") {
            NameOrNicknameError.Empty
        } else if (playersListDao.verifyIfNicknameExists(nicknameWithoutSpace) > 0) {
            NameOrNicknameError.Exists
        } else {
            null
        }

        if (nameError == null && nicknameError == null) {
            playersListDao.addPlayer(Pessoa(nameWithoutSpace, nicknameWithoutSpace))
            return null
        } else {
            return AddPlayerResult(
                nameError = nameError,
                nicknameError = nicknameError
            )
        }
    }

    suspend fun deletePlayer(name: String, nickname: String) {
        playersListDao.deletePlayer(Pessoa(name, nickname))
    }

    suspend fun readPlayer(): List<Pessoa> {
        return playersListDao.readPlayer()
    }
}