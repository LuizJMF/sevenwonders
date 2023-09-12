package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.gmail.luizjmfilho.sevenwonders.model.Pessoa

@Dao
interface PlayersListDao {

    @Insert
    suspend fun addPlayer(person: Pessoa)

    @Delete
    suspend fun deletePlayer(person: Pessoa)

    @Query("SELECT * FROM Pessoa ORDER BY name")
    suspend fun readPlayer(): List<Pessoa>

    @Query("SELECT COUNT(name) FROM Pessoa WHERE name = :searchedName")
    suspend fun verifyIfNameExists(searchedName: String): Int

    @Query("SELECT COUNT(nickname) FROM Pessoa WHERE name = :searchedNickname")
    suspend fun verifyIfNicknameExists(searchedNickname: String): Int
}