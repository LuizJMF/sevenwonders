package com.gmail.luizjmfilho.sevenwonders.data

import javax.inject.Inject

class MatchCountRepository @Inject constructor(
    private val matchDao: MatchDao,
) {
    suspend fun getNumberOfMatches(): Int {
        return matchDao.getNumberOfMatches()
    }

}