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

        assertEquals(listOf("","","","","","",""), state.playerNames)
        assertEquals(ActivePlayersNumber.Three, state.activePlayersNumber)
        assertEquals(emptyList<Person>(), state.availablePlayersList)
        assertFalse(state.isAdvanceAndAddPlayerButtonsEnable)
    }

    @Test
    fun onUpdatePlayer_WhenChooseSomeone_ThenTheyGoToTheRightPlace() {
        viewModel.updatePlayer(2, anna.name)

        val state = viewModel.uiState.value
        assertEquals(anna.name, state.playerNames[2])
    }

    @Test
    fun onNewGameAddPlayer_WhenAddSomeone_ThenAnEmptyPlaceComes() {
        viewModel.updatePlayer(0, luiz.name)
        viewModel.updatePlayer(1, anna.name)
        viewModel.updatePlayer(2, cristian.name)
        viewModel.newGameAddPlayer()

        val state = viewModel.uiState.value
        assertEquals(ActivePlayersNumber.Four, state.activePlayersNumber)
        assertEquals("", state.playerNames[3])
    }

    @Test
    fun onNewGameRemovePlayer_WhenRemoveSomeone_ThenAPlaceIsExcluded() {
        viewModel.updatePlayer(0, luiz.name)
        viewModel.updatePlayer(1, anna.name)
        viewModel.updatePlayer(2, cristian.name)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(3, gian.name)
        viewModel.newGameRemovePlayer()

        val state = viewModel.uiState.value
        assertEquals(ActivePlayersNumber.Three, state.activePlayersNumber)
        assertEquals("", state.playerNames[3])
    }

    @Test
    fun whenAll3AvailablePlacesWithNickname_ThenAddAndAdvanceButtonAreClickable() {
        viewModel.updatePlayer(0, luiz.name)
        viewModel.updatePlayer(1, anna.name)
        viewModel.updatePlayer(2, cristian.name)

        val state = viewModel.uiState.value
        assertTrue(state.isAdvanceAndAddPlayerButtonsEnable)

    }

    @Test
    fun whenAll5AvailablePlacesWithNickname_ThenAddAndAdvanceButtonAreClickable() {
        viewModel.updatePlayer(0, luiz.name)
        viewModel.updatePlayer(1, anna.name)
        viewModel.updatePlayer(2, cristian.name)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(3, gian.name)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(4, ivana.name)

        val state = viewModel.uiState.value
        assertTrue(state.isAdvanceAndAddPlayerButtonsEnable)

    }

    @Test
    fun whenNotAll3AvailablePlacesAreFilled_ThenAddAndAdvanceButtonAreNotClickable() {
        viewModel.updatePlayer(0, luiz.name)
        viewModel.updatePlayer(1, anna.name)

        val state = viewModel.uiState.value
        assertFalse(state.isAdvanceAndAddPlayerButtonsEnable)
    }

    @Test
    fun whenNotAll5AvailablePlacesAreFilled_ThenAddAndAdvanceButtonAreNotClickable() {
        viewModel.updatePlayer(0, luiz.name)
        viewModel.updatePlayer(1, anna.name)
        viewModel.updatePlayer(2, cristian.name)
        viewModel.newGameAddPlayer()
        viewModel.updatePlayer(3, gian.name)
        viewModel.newGameAddPlayer()

        val state = viewModel.uiState.value
        assertFalse(state.isAdvanceAndAddPlayerButtonsEnable)
    }

    @Test
    fun onUpdateAvailablePlayersList_ThenItCallTheCorrectFunction() = runTest {
        whenever(repository.readPlayerWithoutActivePlayers(listOf(cristian.name)))
            .thenReturn(listOf(luiz, anna))

        viewModel.updatePlayer(0, cristian.name)
        viewModel.updateAvailablePlayersList()

        val state = viewModel.uiState.value
        assertEquals(listOf(luiz, anna), state.availablePlayersList)
    }
}