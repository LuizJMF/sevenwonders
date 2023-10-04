package com.gmail.luizjmfilho.sevenwonders.data

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

    @Test
    fun onAddPlayer_whenNameIsEmptyAndNicknameIsNot_thenIsNameErrorEmpty() = runTest {
        whenever(dao.numberOfPlayersWithThisNickname("Zinho"))
            .thenReturn(0)

        val addPlayerResult = repository.addPlayer("","Zinho")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, null), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNicknameIsEmptyAndNameIsNot_thenIsNicknameErrorEmpty() = runTest {
        whenever(dao.numberOfPlayersWithThisName("Luiz"))
            .thenReturn(0)

        val addPlayerResult = repository.addPlayer("Luiz","")

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
        whenever(dao.numberOfPlayersWithThisName("Luiz"))
            .thenReturn(1)

        val addPlayerResult = repository.addPlayer("Luiz","")

        assertEquals(AddPlayerResult(NameOrNicknameError.Exists, NameOrNicknameError.Empty), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNicknameExists_thenIsNicknameErrorExist() = runTest {
        whenever(dao.numberOfPlayersWithThisNickname("Zinho"))
            .thenReturn(1)

        val addPlayerResult = repository.addPlayer("","Zinho")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, NameOrNicknameError.Exists), addPlayerResult)
    }

    @Test
    fun onAddPlayer_whenNameAndNicknameExists_thenIsErrorsExist() = runTest {
        whenever(dao.numberOfPlayersWithThisNickname("Zinho"))
            .thenReturn(1)

        val addPlayerResult = repository.addPlayer("","Zinho")

        assertEquals(AddPlayerResult(NameOrNicknameError.Empty, NameOrNicknameError.Exists), addPlayerResult)
    }

    @Test
    fun onAddPlayer_happyPath() = runTest {
        whenever(dao.numberOfPlayersWithThisName("Luiz"))
            .thenReturn(0)
        whenever(dao.numberOfPlayersWithThisNickname("Zinho"))
            .thenReturn(0)


        val addPlayerResult = repository.addPlayer("Luiz","Zinho")

        assertNull(addPlayerResult)
    }
}