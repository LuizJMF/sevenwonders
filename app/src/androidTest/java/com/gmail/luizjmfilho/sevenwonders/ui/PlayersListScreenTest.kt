package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.model.Person
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class PlayersListScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val robot = PlayersListScreenRobot(rule)

    private fun launchScreen (
        onBackClick: () -> Unit = {},
        playersListUiState: PlayersListUiState = PlayersListUiState(),
        onNicknameChange: (String) -> Unit = {},
        deletePlayer: (String) -> Unit = {},
        cancelAddPlayer: () -> Unit = {},
        onConfirmClicked: () -> Unit = {},
    ) {
        rule.setContent {
            PlayersListScreenSecundaria(
                onBackClick = onBackClick,
                playersListUiState = playersListUiState,
                onNicknameChange = onNicknameChange,
                deletePlayer = deletePlayer,
                cancelAddPlayer = cancelAddPlayer,
                onConfirmClicked = onConfirmClicked,
                windowWidthSizeClass = WindowWidthSizeClass.Compact
            )
        }
    }

    @Test
    fun initialState() {
        launchScreen()

        with(robot) {
            assertPlayersListIsEmpty()
            assertAddPlayerWindowIsNotExpanded()
        }

        // aqui tbm é importante testar se os botões Add Jogador e Apagar Jogador começam roxo,
        // mas farei no futuro, pq é mais difícil de implementar.
    }

    @Test
    fun onBackButtonClick() {
        val sevenWondersRobot = SevenWondersRobot(rule)
        var backButtonClicked = false
        launchScreen(onBackClick = { backButtonClicked = true },)

        sevenWondersRobot.clickNavigationBackButton()
        assertTrue(backButtonClicked)
    }

    @Test
    fun whenPlayerListIsNotEmpty_thenPlayersAreDisplayed() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                playersList = listOf(
                    Person(luiz.name),
                    Person(cristian.name)
                )
            ),
        )

        with(robot) {
            assertPlayerNicknameInTheListIs(luiz.name, 0)
            assertPlayerNicknameInTheListIs(cristian.name, 1)
        }
    }

    @Test
    fun whenITypeOnNicknameTextField_thenOnNicknameChangeIsCalled() {
        var expectedNickname = ""
        launchScreen(onNicknameChange = { expectedNickname = it },)

        with(robot) {
            clickAddPlayerButton()
            typeNickname(luiz.name)
        }

        assertEquals(luiz.name, expectedNickname)
    }

    @Test
    fun whenIDeleteSomeone_thenTheCorrectPersonIsDeleted() {
        var expectedName = ""
        launchScreen(
            deletePlayer = { name ->
                expectedName = name
            },
            playersListUiState = PlayersListUiState(
                playersList = listOf(
                    Person(luiz.name),
                    Person(cristian.name)
                )
            )
        )

        with(robot) {
            clickDeletePlayerButton()
            clickIconDelete(1)
        }

        assertEquals(cristian.name, expectedName)
    }

    @Test
    fun whenIClickCancelAddPlayer_thenCancelAddPlayerIsCalled() {
        var cancelAddPlayerWasCalled = false
        launchScreen(cancelAddPlayer = { cancelAddPlayerWasCalled = true })

        with(robot) {
            clickAddPlayerButton()
            clickCancelAddPlayerButton()
        }


        assertTrue(cancelAddPlayerWasCalled)
    }

    @Test
    fun whenIClickCancelAddPlayer_thenAddPlayerWindowDisappears() {
        launchScreen()

        with(robot) {
            clickAddPlayerButton()
            clickCancelAddPlayerButton()
        }


        robot.assertAddPlayerWindowIsNotExpanded()

        //falta testar tbm aqui no futuro se qnd eu clico aqui o botão de Add Jogador fica roxo.
    }

    @Test
    fun whenAddPlayerWindowIsOpenedAndIClickAddPlayerButton_thenAddPlayerWindowDisappears() {
        launchScreen()

        with(robot) {
            clickAddPlayerButton()
            clickAddPlayerButton()
        }


        robot.assertAddPlayerWindowIsNotExpanded()

        //falta testar tbm aqui no futuro se qnd eu clico aqui o botão de Add Jogador fica roxo.
    }

    @Test
    fun whenIClickAddPlayerButton_thenAddPlayerWindowAppears() {
        launchScreen()

        robot.clickAddPlayerButton()

        robot.assertAddPlayerWindowIsExpanded()

        //falta testar tbm aqui no futuro se qnd eu clico aqui o botão de Add Jogador fica cinza.
    }

    @Test
    fun whenIClickDeletePlayerButtonAndDeleteIconsAreAlreadyShown_thenDeleteIconsDisappear() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                playersList = listOf(
                    Person(luiz.name)
                )
            )
        )

        with(robot) {
            clickDeletePlayerButton()
            clickDeletePlayerButton()
        }

        robot.assertDeleteIconsAreNotShown()

        //falta testar tbm aqui no futuro se qnd eu clico aqui o botão de Delete Jogador fica roxo.
    }

    @Test
    fun whenIClickDeletePlayerButtonAndDeleteIconsAreNotShown_thenDeleteIconsAppear() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                playersList = listOf(
                    Person(luiz.name)
                )
            )
        )

        robot.clickDeletePlayerButton()

        robot.assertDeleteIconsAreShown()

        //falta testar tbm aqui no futuro se qnd eu clico aqui o botão de Delete Jogador fica cinza.
    }

    @Test
    fun whenIClickConfirmAddPlayerButton_thenOnConfirmClickedIsCalled() {
        var confirmAddPlayerWasCalled = false
        launchScreen(onConfirmClicked = { confirmAddPlayerWasCalled = true })

        with(robot) {
            clickAddPlayerButton()
            clickConfirmAddPlayerButton()
        }


        assertEquals(true, confirmAddPlayerWasCalled)
    }

    @Test
    fun whenNicknameHasAString_thenNicknameFieldShowsIt() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                nickname = luiz.name
            )
        )

        robot.clickAddPlayerButton()

        robot.assertNicknameTextFieldHasNickname(luiz.name)
    }

    @Test
    fun whenNicknameErrorIsEmpty_thenEmptyErrorAppears() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                nicknameError = NameOrNicknameError.Empty
            )
        )

        robot.clickAddPlayerButton()

        robot.assertEmptyErrorMessageIsShownInNicknameTextField()
    }

    @Test
    fun whenNicknameErrorIsExists_thenNicknameExistsErrorAppears() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                nicknameError = NameOrNicknameError.Exists
            )
        )

        robot.clickAddPlayerButton()

        robot.assertNicknameExistsErrorMessageIsShown()
    }

}