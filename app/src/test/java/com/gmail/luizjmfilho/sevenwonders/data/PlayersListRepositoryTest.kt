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

    @Test
    fun onAddPlayer_whenNicknameIsEmpty_thenNicknameErrorIsEmpty() = runTest {

        val addPlayerResult = repository.addPlayer("")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNicknameIsJustASpace_thenNicknameErrorIsEmpty() = runTest {
        val addPlayerResult = repository.addPlayer(" ")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNicknameExists_thenIsNicknameErrorExist() = runTest {
        mockNumberOfPlayersWithThisNickname(luiz.nickname, 1)

        val addPlayerResult = repository.addPlayer(luiz.nickname)

        assertEquals(AddPlayerResult(NameOrNicknameError.Exists), addPlayerResult)
    }

    @Test
    fun onAddPlayer_happyPath() = runTest {
        mockNumberOfPlayersWithThisNickname(luiz.nickname, 0)

        val addPlayerResult = repository.addPlayer(luiz.nickname)

        assertNull(addPlayerResult)
    }
}