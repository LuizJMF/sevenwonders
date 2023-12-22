package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Match
import javax.inject.Inject

class SummaryRepository @Inject constructor(private val matchDao: MatchDao) {

    suspend fun getCurrentMatchList(): List<Match> {
        return matchDao.getCurrentMatchList(matchDao.getLastMatchId()!!)
    }
}