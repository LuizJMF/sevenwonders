package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.sevenwonders.model.Person

@Dao
interface PersonDao {

    @Insert
    suspend fun addPlayer(person: Person)

    @Query("DELETE FROM Person WHERE name = :name")
    suspend fun deletePlayer(name: String)

    @Query("SELECT * FROM Person ORDER BY name")
    suspend fun readPlayer(): List<Person>

    @Query("SELECT COUNT(name) FROM Person WHERE name = :searchedName")
    suspend fun numberOfPlayersWithThisName(searchedName: String): Int

    @Query("SELECT COUNT(nickname) FROM Person WHERE nickname = :searchedNickname")
    suspend fun numberOfPlayersWithThisNickname(searchedNickname: String): Int

    @Query("SELECT * FROM Person WHERE nickname NOT IN (:excludedNicknames) ORDER BY name")
    suspend fun readPlayerExcept(excludedNicknames: List<String>): List<Person>
}