package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.MainDispatcherRule
import com.gmail.luizjmfilho.sevenwonders.TestData.anna
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.gian
import com.gmail.luizjmfilho.sevenwonders.TestData.ivana
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.data.NewGameRepository
import com.gmail.luizjmfilho.sevenwonders.model.Person
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class NewGameViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: NewGameRepository
    private lateinit var viewModel: NewGameViewModel

    @Before
    fun beforeTests() {
        repository = mock()
        viewModel = NewGameViewModel(repository)

    }

    @Test
    fun initialState() {
        val state = viewModel.uiState.value

        assertEquals(listOf("","","","","","",""), state.activePlayersList)
        assertEquals(ActivePlayersNumber.Three, state.activePlayersNumber)
        assertEquals(emptyList<Person>(), state.availablePlayersList)
        assertFalse(state.isAdvanceAndAddPlayerButtonsEnable)
    }

    @Test
    fun onUpdatePlayer_WhenChooseSomeone_ThenTheyGoToTheRightPlace() {
        viewModel.updatePlayer(2, anna.nickname)

        val state = viewModel.uiState.value
        assertEquals(anna.nickname, state.activePlayersList[2])
    }

    @Test
    fun onNewGameAddPlayer_WhenAddSomeone_ThenAnEmptyPlaceComes() {
        viewModel.updatePlayer(0, luiz.nickname)
        viewModel.updatePlayer(1, anna.nickname)
        viewModel.updatePlayer(2, cristian.nickname)
        viewModel.newGameAddPlayer()

        val state = viewModel.uiState.value
        assertEquals(ActivePlayersNumber.Four, state.activePlayersNumber)
        assertEquals("", state.activePlayersList[3])
    }

    @Test
    fun onNewGameRemovePlayer_WhenRemoveSomeone_ThenAPlaceIsExcluded() {
        viewModel.updatePlayer(0, luiz.nickname)
        viewModel.updatePlayer(1, anna.nickname)
        viewModel.updatePlayer(2, cristian.nickname)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(3, gian.nickname)
        viewModel.newGameRemovePlayer()

        val state = viewModel.uiState.value
        assertEquals(ActivePlayersNumber.Three, state.activePlayersNumber)
        assertEquals("", state.activePlayersList[3])
    }

    @Test
    fun whenAll3AvailablePlacesWithNickname_ThenAddAndAdvanceButtonAreClickable() {
        viewModel.updatePlayer(0, luiz.nickname)
        viewModel.updatePlayer(1, anna.nickname)
        viewModel.updatePlayer(2, cristian.nickname)

        val state = viewModel.uiState.value
        assertTrue(state.isAdvanceAndAddPlayerButtonsEnable)

    }

    @Test
    fun whenAll5AvailablePlacesWithNickname_ThenAddAndAdvanceButtonAreClickable() {
        viewModel.updatePlayer(0, luiz.nickname)
        viewModel.updatePlayer(1, anna.nickname)
        viewModel.updatePlayer(2, cristian.nickname)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(3, gian.nickname)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(4, ivana.nickname)

        val state = viewModel.uiState.value
        assertTrue(state.isAdvanceAndAddPlayerButtonsEnable)

    }

    @Test
    fun whenNotAll3AvailablePlacesAreFilled_ThenAddAndAdvanceButtonAreNotClickable() {
        viewModel.updatePlayer(0, luiz.nickname)
        viewModel.updatePlayer(1, anna.nickname)

        val state = viewModel.uiState.value
        assertFalse(state.isAdvanceAndAddPlayerButtonsEnable)
    }

    @Test
    fun whenNotAll5AvailablePlacesAreFilled_ThenAddAndAdvanceButtonAreNotClickable() {
        viewModel.updatePlayer(0, luiz.nickname)
        viewModel.updatePlayer(1, anna.nickname)
        viewModel.updatePlayer(2, cristian.nickname)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(3, gian.nickname)
        viewModel.newGameAddPlayer()

        val state = viewModel.uiState.value
        assertFalse(state.isAdvanceAndAddPlayerButtonsEnable)
    }

    @Test
    fun onUpdateAvailablePlayersList_ThenItCallTheCorrectFunction() = runTest {
        whenever(repository.readPlayerWithoutActivePlayers(listOf(cristian.nickname)))
            .thenReturn(listOf(luiz, anna))

        viewModel.updatePlayer(0, cristian.nickname)
        viewModel.updateAvailablePlayersList()

        val state = viewModel.uiState.value
        assertEquals(listOf(luiz, anna), state.availablePlayersList)
    }
}