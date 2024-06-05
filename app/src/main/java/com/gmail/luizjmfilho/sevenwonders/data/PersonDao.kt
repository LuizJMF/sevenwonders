package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.sevenwonders.model.Person

@Dao
interface PersonDao {

    @Insert
    suspend fun addPlayer(person: Person)

    @Query("DELETE FROM Person WHERE id = :playerId")
    suspend fun deletePlayer(playerId: Int)

    @Query("SELECT * FROM Person ORDER BY name")
    suspend fun selectAllPlayer(): List<Person>

    @Query("SELECT COUNT(name) FROM Person WHERE UPPER(name) = UPPER(:searchedNickname)")
    suspend fun numberOfPlayersWithThisName(searchedNickname: String): Int

    @Query("SELECT * FROM Person WHERE name NOT IN (:excludedNicknames) ORDER BY name")
    suspend fun readPlayerExcept(excludedNicknames: List<String>): List<Person>

    @Query("SELECT * FROM Person ORDER BY name")
    suspend fun readPlayer(): List<Person>

    @Query("SELECT * FROM Person WHERE id = :playerId")
    suspend fun getPlayerNameFromId(playerId: Int): Person
}