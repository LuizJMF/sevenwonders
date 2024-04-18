package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.model.Person
import com.gmail.luizjmfilho.sevenwonders.ui.ResultadoDaConsultaSQLBestWonder
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val matchDao: MatchDao,
    private val personDao: PersonDao,
) {

    suspend fun getBestScoreList(): List<Match>? {
        return matchDao.getBestScoreList()
    }

    suspend fun getWorstScoreList(): List<Match>? {
        return matchDao.getWorstScoreList()
    }

    suspend fun getBestScoresPerPlayerList(): List<Match>? {
        return matchDao.getBestScoresPerPlayerList()
    }

    suspend fun getWorstScoresPerPlayerList(): List<Match>? {
        return matchDao.getWorstScoresPerPlayerList()
    }

    suspend fun getAverageWinnerScore(): Int? {
        return matchDao.getAverageWinnerScore()
    }

    suspend fun getAverageScorePerPlayer(): List<Pair<String, Int>>? {
        return matchDao.getAverageScorePerPlayer()?.map { it ->
            it.nickname to it.score
        }
    }

    suspend fun selectAllMatches(): List<Match>? {
        return matchDao.selectAllMatches()
    }

    suspend fun getBlueRecordsList(): List<Match>? {
        return matchDao.getBlueRecordsList()
    }

    suspend fun getYellowRecordsList(): List<Match>? {
        return matchDao.getYellowRecordsList()
    }

    suspend fun getGreenRecordsList(): List<Match>? {
        return matchDao.getGreenRecordsList()
    }

    suspend fun getPurpleRecordsList(): List<Match>? {
        return matchDao.getPurpleRecordsList()
    }

    suspend fun getBestWondersList(): List<ResultadoDaConsultaSQLBestWonder>? {
        return matchDao.getBestWonder()
    }

    suspend fun readPlayer(): List<String> {
        return personDao.readPlayer()
    }

}