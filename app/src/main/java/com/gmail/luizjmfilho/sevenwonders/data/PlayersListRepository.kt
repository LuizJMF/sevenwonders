package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Person
import com.gmail.luizjmfilho.sevenwonders.ui.NameOrNicknameError
import javax.inject.Inject

data class AddPlayerResult(
    val nicknameError: NameOrNicknameError?,
)

class PlayersListRepository @Inject constructor (private val personDao: PersonDao) {

    suspend fun addPlayer(nickname: String): AddPlayerResult? {

        val nicknameWithoutSpace = nickname.trim()
        val nicknameError: NameOrNicknameError? = if (nicknameWithoutSpace == "") {
            NameOrNicknameError.Empty
        } else if (personDao.numberOfPlayersWithThisNickname(nicknameWithoutSpace) > 0) {
            NameOrNicknameError.Exists
        } else {
            null
        }

        if (nicknameError == null) {
            personDao.addPlayer(Person(nicknameWithoutSpace))
            return null
        } else {
            return AddPlayerResult(
                nicknameError = nicknameError
            )
        }
    }

    suspend fun deletePlayer(nickname: String) {
        personDao.deletePlayer(nickname)
    }

    suspend fun readPlayer(): List<Person> {
        return personDao.readPlayer()
    }
}