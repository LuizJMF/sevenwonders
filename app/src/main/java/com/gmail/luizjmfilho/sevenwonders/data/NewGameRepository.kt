package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Person

class NewGameRepository(database: SevenWondersDatabase) {

    private val personDao: PersonDao = database.personDao()

    suspend fun readPlayerWithoutActivePlayers(activePlayersList: List<String>): List<Person> {
        return personDao.readPlayerExcept(activePlayersList)
    }
}