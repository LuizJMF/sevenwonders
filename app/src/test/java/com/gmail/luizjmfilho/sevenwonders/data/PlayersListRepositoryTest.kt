package com.gmail.luizjmfilho.sevenwonders.data

import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.ui.NameOrNicknameError
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PlayersListRepositoryTest {

    private lateinit var dao: PersonDao
    private lateinit var repository: PlayersListRepository

    @Before
    fun beforeTests() {
        dao = mock()
        repository = PlayersListRepository(dao)
    }

    private suspend fun mockNumberOfPlayersWithThisNickname(searchedNickname: String, result: Int) {
        whenever(dao.numberOfPlayersWithThisNickname(searchedNickname))
            .thenReturn(result)
    }

    private suspend fun mockNumberOfPlayersWithThisName(searchedName: String, result: Int) {
        whenever(dao.numberOfPlayersWithThisName(searchedName))
            .thenReturn(result)
    }

    @Test
    fun onAddPlayer_whenNameIsEmptyAndNicknameIsNot_thenIsNameErrorEmpty() = runTest {
        mockNumberOfPlayersWithThisNickname(luiz.nickname, 0)

        val addPlayerResult = repository.addPlayer("",luiz.nickname)

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, null), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNicknameIsEmptyAndNameIsNot_thenIsNicknameErrorEmpty() = runTest {
        mockNumberOfPlayersWithThisName(luiz.name, 0)

        val addPlayerResult = repository.addPlayer(luiz.name,"")

        assertEquals(AddPlayerResult(null, NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNameAndNicknameEmpty_thenIsErrorEmpty() = runTest {
        val addPlayerResult = repository.addPlayer("","")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenIsNameAndNicknameJustASpace_thenIsNameAndNicknameErrorEmpty() = runTest {
        val addPlayerResult = repository.addPlayer(" "," ")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenIsNameJustASpace_thenIsNameErrorEmpty() = runTest {
        val addPlayerResult = repository.addPlayer(" ","")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenIsNicknameJustASpace_thenIsNicknameErrorEmpty() = runTest {
        val addPlayerResult = repository.addPlayer(""," ")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, NameOrNicknameError.Empty), addPlayerResult)
    }


    @Test
    fun onAddPlayer_whenNameExists_thenIsNameErrorExist() = runTest {
        mockNumberOfPlayersWithThisName(luiz.name, 1)

        val addPlayerResult = repository.addPlayer(luiz.name,"")

        assertEquals(AddPlayerResult(NameOrNicknameError.Exists, NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNicknameExists_thenIsNicknameErrorExist() = runTest {
        mockNumberOfPlayersWithThisNickname(luiz.nickname, 1)

        val addPlayerResult = repository.addPlayer("",luiz.nickname)

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, NameOrNicknameError.Exists), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNameAndNicknameExists_thenIsErrorsExist() = runTest {
        mockNumberOfPlayersWithThisName(luiz.name, 1)
        mockNumberOfPlayersWithThisNickname(luiz.nickname, 1)

        val addPlayerResult = repository.addPlayer(luiz.name,luiz.nickname)

        assertEquals(AddPlayerResult(NameOrNicknameError.Exists, NameOrNicknameError.Exists), addPlayerResult)
    }

    @Test
    fun onAddPlayer_happyPath() = runTest {
        mockNumberOfPlayersWithThisName(luiz.name, 0)
        mockNumberOfPlayersWithThisNickname(luiz.nickname, 0)


        val addPlayerResult = repository.addPlayer(luiz.name,luiz.nickname)

        assertNull(addPlayerResult)
    }
}