package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.model.Person
import javax.inject.Inject

class CalculationRepository @Inject constructor(
    private val matchDao: MatchDao,
    private val personDao: PersonDao,
) {

    suspend fun addPlayerMatchInfo(match: Match) {
        matchDao.addPlayer(match)
    }

    suspend fun getLastMatchId(): Int? {
        return matchDao.getLastMatchId()
    }

    suspend fun deleteLastMatch() {
        matchDao.deleteMatchWhoseIdIs(matchDao.getLastMatchId()!!)
    }

    suspend fun getPersonsFromIds(playerIds: List<Int>): List<Person> {
        return personDao.getPersonsFromIds(playerIds)
    }

}