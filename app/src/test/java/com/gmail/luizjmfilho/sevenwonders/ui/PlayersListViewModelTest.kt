package com.gmail.luizjmfilho.sevenwonders.ui

import com.gmail.luizjmfilho.sevenwonders.data.NameOrNicknameError
import com.gmail.luizjmfilho.sevenwonders.model.Pessoa
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class PlayersListViewModelTest {

    @Test
    fun initialState() {
        val viewModel = PlayersListViewModel()

        val initialState = viewModel.uiState.value
        assertEquals("", initialState.name)
        assertEquals("", initialState.nickname)
        assertEquals(listOf<Pessoa>(), initialState.playersList)
        assertNull(initialState.nameError)
        assertNull(initialState.nicknameError)
    }

    @Test
    fun onConfirmAddPlayerClick_whenNameAndNicknameEmpty_thenIsErrorEmpty() {
        val viewModel = PlayersListViewModel()

        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value
        assertEquals(NameOrNicknameError.Empty ,state.nameError)
        assertEquals(NameOrNicknameError.Empty ,state.nicknameError)
        assertEquals(listOf<Pessoa>(), state.playersList)
    }

    @Test
    fun onConfirmAddPlayerClick_whenNameIsEmptyAndNicknameIsNot_thenIsNameErrorEmpty() {
        val viewModel = PlayersListViewModel()

        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value
        assertEquals(NameOrNicknameError.Empty ,state.nameError)
        assertNull(state.nicknameError)
        assertEquals(listOf<Pessoa>(), state.playersList)
    }

    @Test
    fun onConfirmAddPlayerClick_whenNicknameIsEmptyAndNameIsNot_thenIsNicknameErrorEmpty() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value
        assertNull(state.nameError)
        assertEquals(NameOrNicknameError.Empty, state.nicknameError)
        assertEquals(listOf<Pessoa>(), state.playersList)
    }

    @Test
    fun onConfirmAddPlayerClick_whenIsNameJustASpace_thenIsNameErrorEmpty() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName(" ")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value
        assertEquals(NameOrNicknameError.Empty ,state.nameError)
    }

    @Test
    fun onConfirmAddPlayerClick_whenNameExists_thenIsNameErrorExist() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Outra Coisa")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value
        assertEquals(NameOrNicknameError.Exists ,state.nameError)
        assertNull(state.nicknameError)
    }

    @Test
    fun onConfirmAddPlayerClick_whenNicknameExists_thenIsNicknameErrorExist() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        viewModel.updateName("Outra coisa")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value
        assertNull(state.nameError)
        assertEquals(NameOrNicknameError.Exists, state.nicknameError)

    }

    @Test
    fun onConfirmAddPlayerClick_whenNameAndNicknameExists_thenIsErrorsExist() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value
        assertEquals(NameOrNicknameError.Exists ,state.nameError)
        assertEquals(NameOrNicknameError.Exists, state.nicknameError)
    }

    @Test
    fun onConfirmAddPlayerClick_whenNamesHaveDifferentCases_thenIsErrorNameExist() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        viewModel.updateName("LUIZ")
        viewModel.updateNickname("Outra coisa")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value

        assertEquals(NameOrNicknameError.Exists, state.nameError)
    }

    @Test
    fun onConfirmAddPlayerClick_whenNicknamesHaveDifferentCases_thenIsErrorNameExist() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()

        viewModel.updateName("Outra coisa")
        viewModel.updateNickname("ZINHO")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value

        assertEquals(NameOrNicknameError.Exists, state.nicknameError)
    }
    @Test
    fun onConfirmAddPlayerClick_happyPath() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()
        val state = viewModel.uiState.value

        assertNull(state.nameError)
        assertNull(state.nicknameError)
        assertEquals("", state.name)
        assertEquals("", state.nickname)
        assertEquals(Pessoa("Luiz", "Zinho"), state.playersList[0])
    }

    @Test
    fun onConfirmAddPlayerClick_whenAddTwoNamesOrMore_thenAlphabeticalOrderIsExpected() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()
        viewModel.updateName("Crístian Deives")
        viewModel.updateNickname("Deivinho")
        viewModel.onConfirmAddPlayerClick()

        val state = viewModel.uiState.value

        assertEquals(Pessoa("Crístian Deives", "Deivinho"), state.playersList[0])
    }


    @Test
    fun updateName_whenITypeSomething() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        val state = viewModel.uiState.value

        assertEquals("Luiz", state.name)
    }
    @Test
    fun updateNickname_whenITypeSomething() {
        val viewModel = PlayersListViewModel()

        viewModel.updateNickname("Zinho")
        val state = viewModel.uiState.value

        assertEquals("Zinho", state.nickname)
    }

    @Test
    fun cancelAddPlayer_whenIClickOnIt() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.cancelAddPlayer()
        val state = viewModel.uiState.value

        assertEquals("", state.name)
        assertEquals("", state.nickname)
    }

    @Test
    fun deletePlayer_whenIClickOnIt() {
        val viewModel = PlayersListViewModel()

        viewModel.updateName("Luiz")
        viewModel.updateNickname("Zinho")
        viewModel.onConfirmAddPlayerClick()
        viewModel.deletePlayer("Luiz","Zinho")

        val state = viewModel.uiState.value

        assertEquals(listOf<Pessoa>(), state.playersList)
    }



}