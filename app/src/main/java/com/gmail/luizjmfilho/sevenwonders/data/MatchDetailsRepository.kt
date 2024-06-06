package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Person
import javax.inject.Inject


class MatchDetailsRepository @Inject constructor(private val personDao: PersonDao) {

    suspend fun getPersonsFromIds(ids: List<Int>): List<Person> {
        return personDao.getPersonsFromIds(ids)
    }

}