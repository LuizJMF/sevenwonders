package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Person
import javax.inject.Inject

class NewGameRepository @Inject constructor(private val personDao: PersonDao) {

    suspend fun readPlayerWithoutActivePlayers(activePlayersList: List<String>): List<Person> {
        return personDao.readPlayerExcept(activePlayersList)
    }
}