package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
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
        onNameChange: (String) -> Unit = {},
        onNicknameChange: (String) -> Unit = {},
        deletePlayer: (String) -> Unit = {},
        cancelAddPlayer: () -> Unit = {},
        onConfirmClicked: () -> Unit = {},
    ) {
        rule.setContent {
            PlayersListScreenSecundaria(
                onBackClick = onBackClick,
                playersListUiState = playersListUiState,
                onNameChange = onNameChange,
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
    fun onBackClickButton() {
        val sevenWondersRobot = SevenWondersRobot(rule)
        var clicouNoBotao = false
        launchScreen(onBackClick = { clicouNoBotao = true },)

        sevenWondersRobot.clickNavigationBackButton()
        assertTrue(clicouNoBotao)
    }

    @Test
    fun whenPlayerListIsNotEmpty_thenPlayersAreDisplayed() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                playersList = listOf(
                    Person("Luiz Medeiros", "Zinho"),
                    Person("Crístian Deives", "Deivinho")
                )
            ),
        )

        with(robot) {
            assertPlayerNameInTheListIs("Luiz Medeiros", 0)
            assertPlayerNicknameInTheListIs("Zinho", 0)
            assertPlayerNameInTheListIs("Crístian Deives", 1)
            assertPlayerNicknameInTheListIs("Deivinho", 1)
        }
    }

    @Test
    fun whenITypeOnNameTextField_thenOnNameChangeIsCalled() {
        var expectedName = ""
        launchScreen(onNameChange = { expectedName = it },)

        with(robot) {
            clickAddPlayerButton()
            typeName("Luiz")
        }

        assertEquals("Luiz", expectedName)
    }
    @Test
    fun whenITypeOnNicknameTextField_thenOnNicknameChangeIsCalled() {
        var expectedNickname = ""
        launchScreen(onNicknameChange = { expectedNickname = it },)

        with(robot) {
            clickAddPlayerButton()
            typeNickname("Zinho")
        }

        assertEquals("Zinho", expectedNickname)
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
                    Person("Luiz", "Zinho"),
                    Person("Deives", "Deivinho")
                )
            )
        )

        with(robot) {
            clickDeletePlayerButton()
            clickIconDelete(1)
        }

        assertEquals("Deives", expectedName)
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
                    Person("Luiz","Zinho")
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
                    Person("Luiz","Zinho")
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
    fun whenNameHasAString_thenNameFieldShowsIt() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                name = "Luiz"
            )
        )

        robot.clickAddPlayerButton()

        robot.assertNameTextFieldHasName("Luiz")
    }

    @Test
    fun whenNicknameHasAString_thenNicknameFieldShowsIt() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                nickname = "Zinho"
            )
        )

        robot.clickAddPlayerButton()

        robot.assertNicknameTextFieldHasNickname("Zinho")
    }

    @Test
    fun whenNameErrorIsEmpty_thenEmptyErrorAppears() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                nameError = NameOrNicknameError.Empty
            )
        )

        robot.clickAddPlayerButton()

        robot.assertEmptyErrorMessageIsShownInNameTextField()
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
    fun whenNameErrorIsExists_thenNameExistsErrorAppears() {
        launchScreen(
            playersListUiState = PlayersListUiState(
                nameError = NameOrNicknameError.Exists
            )
        )

        robot.clickAddPlayerButton()

        robot.assertNameExistsErrorMessageIsShown()
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