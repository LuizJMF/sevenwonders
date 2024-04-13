package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Person
import com.gmail.luizjmfilho.sevenwonders.ui.NameOrNicknameError
import javax.inject.Inject

data class AddPlayerResult(
    val nameError: NameOrNicknameError?,
)

class PlayersListRepository @Inject constructor (private val personDao: PersonDao) {

    suspend fun addPlayer(playerName: String): AddPlayerResult? {

        val nameWithoutSpace = playerName.trim()
        val nameError: NameOrNicknameError? = if (nameWithoutSpace == "") {
            NameOrNicknameError.Empty
        } else if (personDao.numberOfPlayersWithThisName(nameWithoutSpace) > 0) {
            NameOrNicknameError.Exists
        } else {
            null
        }

        if (nameError == null) {
            personDao.addPlayer(Person(name = nameWithoutSpace))
            return null
        } else {
            return AddPlayerResult(
                nameError = nameError
            )
        }
    }

    suspend fun readPlayer(): List<String> {
        return personDao.readPlayer()
    }

    suspend fun deletePlayer(playerName: String) {
        personDao.deletePlayer(playerName)
    }
}