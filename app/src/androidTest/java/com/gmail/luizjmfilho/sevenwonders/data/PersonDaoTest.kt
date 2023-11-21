package com.gmail.luizjmfilho.sevenwonders.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.gmail.luizjmfilho.sevenwonders.TestData
import com.gmail.luizjmfilho.sevenwonders.TestData.anna
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.model.Person
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class PersonDaoTest {

    private lateinit var dao: PersonDao

    @Before
    fun beforeTests() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val database = Room.inMemoryDatabaseBuilder(context, SevenWondersDatabase::class.java).build()
        dao = database.personDao()
    }

    @Test
    fun onAddPlayer_WhenInsertAPerson_HappyPath() = runTest {
        dao.addPlayer(luiz)

        val currentList = dao.readPlayer()

        assertTrue(listOf(luiz) == currentList)
    }

    @Test
    fun onDeletePlayer_WhenDeleteAPerson_HappyPath() = runTest {
        dao.addPlayer(luiz)
        dao.deletePlayer(luiz.nickname)

        val currentList = dao.readPlayer()

        assertEquals(emptyList<Person>(), currentList)
    }

    @Test
    fun onReadPlayer_WhenAddTwoPlayersOrMore_ThenListIsAlphabeticallyOrdered() = runTest {
        dao.addPlayer(luiz)
        dao.addPlayer(anna)
        dao.addPlayer(cristian)

        val currentList = dao.readPlayer()
        assertTrue(listOf(anna, cristian, luiz) == currentList)
    }

    @Test
    fun onNumberOfPlayersWithThisNickname_WhenNicknameHaveDifferentCases_ThenItCountsJustOne() = runTest {
        dao.addPlayer(luiz)
        val numberOfPlayers = dao.numberOfPlayersWithThisNickname(luiz.nickname.uppercase())

        assertEquals(1, numberOfPlayers)
    }

    @Test
    fun onNumberOfPlayersWithThisNickname_WhenNoPlayers_ThenReturnZero() = runTest {
        val numberOfPlayers = dao.numberOfPlayersWithThisNickname(luiz.nickname)
        assertEquals(0, numberOfPlayers)
    }

    @Test
    fun onNumberOfPlayersWithThisNickname_WhenOnePlayerWithThatNickname_ThenReturnsOne() = runTest {
        dao.addPlayer(luiz)
        dao.addPlayer(cristian)
        val numberOfPlayers = dao.numberOfPlayersWithThisNickname(luiz.nickname)
        assertEquals(1, numberOfPlayers)
    }

    @Test
    fun onNumberOfPlayersWithThisNickname_WhenTwoPlayersWithThatNickname_ThenReturnsTwo() = runTest {
        dao.addPlayer(anna)
        dao.addPlayer(anna.copy(id = 5))
        dao.addPlayer(cristian)
        val numberOfPlayers = dao.numberOfPlayersWithThisNickname(anna.nickname)
        assertEquals(2, numberOfPlayers)
    }

    @Test
    fun onReadPlayerExcept_WhenFilteringJustOnePerson_ThenReturnsFilteredAndOrderedList() = runTest {
        dao.addPlayer(cristian)
        dao.addPlayer(anna)
        dao.addPlayer(luiz)

        val filteredList = dao.readPlayerExcept(listOf(luiz.nickname))

        assertTrue(listOf(anna, cristian) == filteredList)
    }

    @Test
    fun onReadPlayerExcept_WhenFilteringNoOne_ThenReturnsFilteredAndOrderedList() = runTest {
        dao.addPlayer(cristian)
        dao.addPlayer(anna)
        dao.addPlayer(luiz)

        val filteredList = dao.readPlayerExcept(emptyList())

        assertTrue(listOf(anna, cristian, luiz) == filteredList)
    }

    @Test
    fun onReadPlayerExcept_WhenFilteringEveryone_ThenReturnsEmptyList() = runTest {
        dao.addPlayer(cristian)
        dao.addPlayer(anna)
        dao.addPlayer(luiz)

        val filteredList = dao.readPlayerExcept(listOf(luiz.nickname, anna.nickname, cristian.nickname))

        assertTrue(filteredList.isEmpty())
    }
}