package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.sevenwonders.model.Match

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
}