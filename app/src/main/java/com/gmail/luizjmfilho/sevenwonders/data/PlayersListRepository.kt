package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Person
import com.gmail.luizjmfilho.sevenwonders.ui.NameOrNicknameError
import javax.inject.Inject

data class AddPlayerResult(
    val nameError: NameOrNicknameError?,
    val nicknameError: NameOrNicknameError?,
)

class PlayersListRepository @Inject constructor (private val personDao: PersonDao) {

    suspend fun addPlayer(name: String, nickname: String): AddPlayerResult? {
        val nameWithoutSpace = name.trim()
        val nameError: NameOrNicknameError? = if (nameWithoutSpace == "") {
            NameOrNicknameError.Empty
        } else if (personDao.numberOfPlayersWithThisName(nameWithoutSpace) > 0) {
            NameOrNicknameError.Exists
        } else {
            null
        }

        val nicknameWithoutSpace = nickname.trim()
        val nicknameError: NameOrNicknameError? = if (nicknameWithoutSpace == "") {
            NameOrNicknameError.Empty
        } else if (personDao.numberOfPlayersWithThisNickname(nicknameWithoutSpace) > 0) {
            NameOrNicknameError.Exists
        } else {
            null
        }

        if (nameError == null && nicknameError == null) {
            personDao.addPlayer(Person(nameWithoutSpace, nicknameWithoutSpace))
            return null
        } else {
            return AddPlayerResult(
                nameError = nameError,
                nicknameError = nicknameError
            )
        }
    }

    suspend fun deletePlayer(name: String) {
        personDao.deletePlayer(name)
    }

    suspend fun readPlayer(): List<Person> {
        return personDao.readPlayer()
    }
}