package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.ui.ResultadoDaConsultaSQLAverageScorePerPlayer
import com.gmail.luizjmfilho.sevenwonders.ui.ResultadoDaConsultaSQLBestWonder

@Dao
interface MatchDao {

    @Insert
    suspend fun addPlayer(playerMatchInfo: Match)

    @Query("SELECT MAX(matchId) FROM `Match`")
    suspend fun getLastMatchId(): Int?

    @Query("SELECT * FROM `Match` WHERE matchId = :matchId ORDER BY totalScore DESC, coinQuantity DESC")
    suspend fun getCurrentMatchList(matchId: Int): List<Match>

    @Query("DELETE FROM `Match` WHERE matchId = :matchId")
    suspend fun deleteMatchWhoseIdIs(matchId: Int)

    @Query("SELECT * FROM `Match`")
    suspend fun selectAllMatches(): List<Match>?

    @Query("UPDATE `Match` SET matchId = (matchId - 1) WHERE matchId > :idRemoved")
    suspend fun updateAllMatchesID(idRemoved: Int)

    @Query("SELECT * FROM `Match` WHERE totalScore = (SELECT MAX(totalScore) FROM `Match`) ORDER BY nickname ASC")
    suspend fun getBestScoreList(): List<Match>?

    @Query("SELECT * FROM `Match` WHERE totalScore = (SELECT MIN(totalScore) FROM `Match`) ORDER BY nickname ASC")
    suspend fun getWorstScoreList(): List<Match>?

    @Query("WITH cte as (SELECT * FROM `Match` ORDER BY totalScore ASC) SELECT * FROM cte GROUP BY nickname")
    suspend fun getBestScoresPerPlayerList(): List<Match>?

    @Query("WITH cte as (SELECT * FROM `Match` ORDER BY totalScore DESC) SELECT * FROM cte GROUP BY nickname")
    suspend fun getWorstScoresPerPlayerList(): List<Match>?

    @Query("SELECT CAST(AVG(totalScore) AS INT) FROM `Match` WHERE position = 1")
    suspend fun getAverageWinnerScore(): Int?

    @Query("SELECT nickname, CAST(AVG(totalScore) AS INT) AS score FROM `Match` GROUP BY nickname")
    suspend fun getAverageScorePerPlayer(): List<ResultadoDaConsultaSQLAverageScorePerPlayer>?

    @Query("SELECT * FROM `Match` WHERE blueCardScore = (SELECT MAX(blueCardScore) FROM `Match`)")
    suspend fun getBlueRecordsList(): List<Match>?

    @Query("SELECT * FROM `Match` WHERE yellowCardScore = (SELECT MAX(yellowCardScore) FROM `Match`)")
    suspend fun getYellowRecordsList(): List<Match>?

    @Query("SELECT * FROM `Match` WHERE greenCardScore = (SELECT MAX(greenCardScore) FROM `Match`)")
    suspend fun getGreenRecordsList(): List<Match>?

    @Query("SELECT * FROM `Match` WHERE purpleCardScore = (SELECT MAX(purpleCardScore) FROM `Match`)")
    suspend fun getPurpleRecordsList(): List<Match>?

    @Query("""
        SELECT * FROM (
            SELECT wonder, wonderSide, COUNT(*) AS times
            FROM `Match`
            WHERE position = 1
            GROUP BY wonder, wonderSide
            ORDER BY times DESC)
        WHERE times = (
            SELECT MAX(times) FROM (
                SELECT wonder, wonderSide, COUNT(*) AS times
                FROM `Match`
                WHERE position = 1
                GROUP BY wonder, wonderSide
                ORDER BY times DESC
            )
        )"""
    )
    suspend fun getBestWonder(): List<ResultadoDaConsultaSQLBestWonder>?
}