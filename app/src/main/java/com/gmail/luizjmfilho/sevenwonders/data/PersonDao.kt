package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.sevenwonders.model.Person

@Dao
interface PersonDao {

    @Insert
    suspend fun addPlayer(person: Person)

    @Query("DELETE FROM Person WHERE nickname = :nickname")
    suspend fun deletePlayer(nickname: String)

    @Query("SELECT * FROM Person ORDER BY nickname")
    suspend fun readPlayer(): List<Person>

    @Query("SELECT COUNT(nickname) FROM Person WHERE UPPER(nickname) = UPPER(:searchedNickname)")
    suspend fun numberOfPlayersWithThisNickname(searchedNickname: String): Int

    @Query("SELECT * FROM Person WHERE nickname NOT IN (:excludedNicknames) ORDER BY nickname")
    suspend fun readPlayerExcept(excludedNicknames: List<String>): List<Person>
}