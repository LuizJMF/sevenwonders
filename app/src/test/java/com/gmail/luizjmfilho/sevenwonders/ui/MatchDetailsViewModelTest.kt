package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.lifecycle.SavedStateHandle
import com.gmail.luizjmfilho.sevenwonders.MainDispatcherRule
import com.gmail.luizjmfilho.sevenwonders.TestData.anna
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.gian
import com.gmail.luizjmfilho.sevenwonders.TestData.ivana
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.model.PlayerDetails
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MatchDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: MatchDetailsViewModel

    @Before
    fun beforeTests() {
        viewModel = MatchDetailsViewModel(SavedStateHandle(mapOf("playerNicknames" to "${luiz.name},${anna.name},${cristian.name},${gian.name},${ivana.name}")))
        
    }

    // COMO TESTAR QUE ALGO REALMENTE ESTÁ SENDO SORTEADO?
    // NÃO TO TESTANDO A FUNÇÃO DE MOVER OS CARDS, PQ SERÁ TEMPORÁRIA

    @Test
    fun initialState() {
        val state = viewModel.uiState.value

        assertEquals(emptyList<PlayerDetails>(), state.matchPlayersDetails)
        assertEquals(Wonders.values().toList(), state.availableWondersList)
        assertNull(state.creationMethod)
        assertFalse(state.isAdvanceButtonEnabled)
    }

    @Test
    fun onConfirmMethod_WhenAllRaffle() {
        viewModel.onConfirmMethod(RaffleOrChoose.Raffle, RaffleOrChoose.Raffle)

        val state = viewModel.uiState.value

        assertEquals(CreationMethod.AllRaffle, state.creationMethod)
        assertTrue(state.isAdvanceButtonEnabled)
        assertEquals(viewModel.playerNicknamesInThePassedOrder.size, state.matchPlayersDetails.size)
        assertTrue(state.matchPlayersDetails.map { it.nickname }.containsAll(viewModel.playerNicknamesInThePassedOrder))
        assertFalse(state.matchPlayersDetails.map{ it.wonder }.contains(null))
        assertEquals(List(viewModel.playerNicknamesInThePassedOrder.size) { WonderSide.Day }, state.matchPlayersDetails.map{ it.wonderSide })
    }

    @Test
    fun onConfirmMethod_WhenAllChoose() {
        viewModel.onConfirmMethod(RaffleOrChoose.Choose, RaffleOrChoose.Choose)

        val state = viewModel.uiState.value

        var numbersOfNoNullWonderElements: Int = 0
        state.matchPlayersDetails.map{ it.wonder }.forEach {
            if (it != null) {
                numbersOfNoNullWonderElements += 1
            }
        }

        var numbersOfNoNullWonderSideElements: Int = 0
        state.matchPlayersDetails.map{ it.wonder }.forEach {
            if (it != null) {
                numbersOfNoNullWonderSideElements += 1
            }
        }

        assertEquals(CreationMethod.AllChoose, state.creationMethod)
        assertFalse(state.isAdvanceButtonEnabled)
        assertEquals(viewModel.playerNicknamesInThePassedOrder.size, state.matchPlayersDetails.size)
        assertEquals(viewModel.playerNicknamesInThePassedOrder, state.matchPlayersDetails.map { it.nickname })
        assertTrue(numbersOfNoNullWonderElements == 0)
        assertTrue(numbersOfNoNullWonderSideElements == 0)
    }

    @Test
    fun onConfirmMethod_WhenRafflePositionChooseWonder() {
        viewModel.onConfirmMethod(RaffleOrChoose.Raffle, RaffleOrChoose.Choose)

        val state = viewModel.uiState.value

        var numbersOfNoNullWonderElements: Int = 0
        state.matchPlayersDetails.map{ it.wonder }.forEach {
            if (it != null) {
                numbersOfNoNullWonderElements += 1
            }
        }

        var numbersOfNoNullWonderSideElements: Int = 0
        state.matchPlayersDetails.map{ it.wonder }.forEach {
            if (it != null) {
                numbersOfNoNullWonderSideElements += 1
            }
        }

        assertEquals(CreationMethod.RafflePositionChooseWonder, state.creationMethod)
        assertFalse(state.isAdvanceButtonEnabled)
        assertEquals(viewModel.playerNicknamesInThePassedOrder.size, state.matchPlayersDetails.size)
        assertTrue(state.matchPlayersDetails.map { it.nickname }.containsAll(viewModel.playerNicknamesInThePassedOrder))
        assertTrue(numbersOfNoNullWonderElements == 0)
        assertTrue(numbersOfNoNullWonderSideElements == 0)
    }

    @Test
    fun onConfirmMethod_WhenChoosePositionRaffleWonder() {
        viewModel.onConfirmMethod(RaffleOrChoose.Choose, RaffleOrChoose.Raffle)

        val state = viewModel.uiState.value

        var numbersOfNoNullWonderElements: Int = 0
        state.matchPlayersDetails.map{ it.wonder }.forEach {
            if (it != null) {
                numbersOfNoNullWonderElements += 1
            }
        }

        var numbersOfNoNullWonderSideElements: Int = 0
        state.matchPlayersDetails.map{ it.wonder }.forEach {
            if (it != null) {
                numbersOfNoNullWonderSideElements += 1
            }
        }

        assertEquals(CreationMethod.ChoosePositionRaffleWonder, state.creationMethod)
        assertTrue(state.isAdvanceButtonEnabled)
        assertEquals(viewModel.playerNicknamesInThePassedOrder.size, state.matchPlayersDetails.size)
        assertEquals(viewModel.playerNicknamesInThePassedOrder, state.matchPlayersDetails.map { it.nickname })
        assertFalse(state.matchPlayersDetails.map{ it.wonder }.contains(null))
        assertEquals(List(viewModel.playerNicknamesInThePassedOrder.size) { WonderSide.Day }, state.matchPlayersDetails.map{ it.wonderSide })
    }

    @Test
    fun onWonderSideChange_WhenClickInDay_ThenItTurnsNightAndViceVersa() {
        viewModel.onConfirmMethod(RaffleOrChoose.Raffle, RaffleOrChoose.Raffle)
        val currentState = viewModel.uiState.value
        val index = 2
        assertEquals(WonderSide.Day, currentState.matchPlayersDetails[index].wonderSide)

        viewModel.onWonderSideChange(index)
        val newState1 = viewModel.uiState.value
        assertEquals(WonderSide.Night, newState1.matchPlayersDetails[index].wonderSide)

        viewModel.onWonderSideChange(index)
        val newState2 = viewModel.uiState.value
        assertEquals(WonderSide.Day, newState2.matchPlayersDetails[index].wonderSide)
    }

    @Test
    fun onChooseWonderClick_ThenAvailableWondersListIsRefreshed() {
        val wonderUsedAsExample = Wonders.OLYMPIA
        viewModel.onConfirmMethod(RaffleOrChoose.Raffle, RaffleOrChoose.Choose)
        val currentState = viewModel.uiState.value
        assertEquals(Wonders.values().toList(), currentState.availableWondersList)

        viewModel.onChooseWonderClick()
        val newState1 = viewModel.uiState.value
        assertEquals(Wonders.values().toList(), newState1.availableWondersList)

        viewModel.onSelectWonderInDialog(wonderUsedAsExample, 2)
        viewModel.onChooseWonderClick()
        val newState2 = viewModel.uiState.value
        assertEquals(Wonders.values().toList() - listOf(wonderUsedAsExample), newState2.availableWondersList)
    }

    @Test
    fun onSelectWonderInDialog_ThenItGoesToCorrectPlace() {
        val wonderUsedAsExample = Wonders.HALIKARNASSOS
        viewModel.onConfirmMethod(RaffleOrChoose.Raffle, RaffleOrChoose.Choose)
        viewModel.onChooseWonderClick()
        viewModel.onSelectWonderInDialog(wonderUsedAsExample, 3)
        val state = viewModel.uiState.value
        assertEquals(wonderUsedAsExample, state.matchPlayersDetails[3].wonder)
        assertNull(state.matchPlayersDetails[2].wonder)
    }

    @Test
    fun onDeselectWonder_ThenItDisappearsFromMatchPlayersDetailsAndReturnsToAvailableWondersList() {
        val wonderUsedAsExample = Wonders.HALIKARNASSOS
        viewModel.onConfirmMethod(RaffleOrChoose.Raffle, RaffleOrChoose.Choose)
        viewModel.onChooseWonderClick()
        viewModel.onSelectWonderInDialog(wonderUsedAsExample, 2)
        viewModel.onChooseWonderClick()
        val currentState = viewModel.uiState.value
        assertEquals(Wonders.values().toList() - listOf(wonderUsedAsExample), currentState.availableWondersList)
        viewModel.onDeselectWonder(2)
        val newState = viewModel.uiState.value
        assertNull(newState.matchPlayersDetails[2].wonder)
        viewModel.onChooseWonderClick()
        val newState2 = viewModel.uiState.value
        assertEquals(Wonders.values().toList(), newState2.availableWondersList)
    }

}