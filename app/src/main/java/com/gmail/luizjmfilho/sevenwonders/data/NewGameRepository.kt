package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Person

class NewGameRepository(private val personDao: PersonDao) {

    suspend fun readPlayerWithoutActivePlayers(activePlayersList: List<String>): List<Person> {
        return personDao.readPlayerExcept(activePlayersList)
    }
}