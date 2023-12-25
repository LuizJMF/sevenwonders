package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Match
import javax.inject.Inject

class MatchesHistoryRepository @Inject constructor(private val matchDao: MatchDao) {

    suspend fun selectAllMatches(): List<Match>? {
        return matchDao.selectAllMatches()
    }

    suspend fun getLastMatchId(): Int? {
        return matchDao.getLastMatchId()
    }

    suspend fun deleteMatchWhoseIdIs(idMatch: Int) {
        matchDao.deleteMatchWhoseIdIs(idMatch)
    }

    suspend fun updateAllMatchesId(idRemoved: Int) {
        matchDao.updateAllMatchesID(idRemoved)
    }
}