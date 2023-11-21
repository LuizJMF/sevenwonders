package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gmail.luizjmfilho.sevenwonders.TestData.anna
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.model.PlayerDetails
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class MatchDetailsScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val robot = MatchDetailsScreenRobot(rule)



    private fun launchScreen(
        onBackClick: () -> Unit = {},
        onNextClick: () -> Unit = {},
        onConfirmClick: (RaffleOrChoose, RaffleOrChoose) -> Unit = {_, _ ->},
        onTrailingIconClick: (Int) -> Unit = {},
        onTextButtonClick: () -> Unit = {},
        onDialogConfirmClick: (Wonders, Int) -> Unit = {_, _ ->},
        onDeselectWonder: (Int) -> Unit = {},
        onMoveCardDown: (Int) -> Unit = {},
        matchDetailsUiState: MatchDetailsUiState = MatchDetailsUiState()
    ) {
        rule.setContent {
            MatchDetailsScreenSecundaria(
                onBackClick = onBackClick,
                onNextClick = onNextClick,
                onConfirmClick = onConfirmClick,
                onTrailingIconClick = onTrailingIconClick,
                onTextButtonClick = onTextButtonClick,
                onDialogConfirmClick = onDialogConfirmClick,
                onDeselectWonderClick = onDeselectWonder,
                onMoveCardDown = onMoveCardDown,
                matchDetailsUiState = matchDetailsUiState
            )
        }
    }

    @Test
    fun initialTest() {
        launchScreen()

        with(robot) {
            assertRaffleAndChooseBoxesAreShown()
            assertConfirmButtonIsDisabled()
            assertAdvanceButtonIsDisabled()
            assertRafflePositionRadioButtonIsNotSelected()
            assertChoosePositionRadioButtonIsNotSelected()
            assertRaffleWonderRadioButtonIsNotSelected()
            assertChooseWonderRadioButtonIsNotSelected()
        }
    }

    @Test
    fun onBackButtonClick() {
        val sevenWondersRobot = SevenWondersRobot(rule)
        var backButtonClicked = false
        launchScreen(
            onBackClick = { backButtonClicked = true }
        )

        sevenWondersRobot.clickNavigationBackButton()
        assertTrue(backButtonClicked)
    }

    @Test
    fun onConfirmButtonClick() {
        var positionMethod: RaffleOrChoose? = null
        var wonderMethod: RaffleOrChoose? = null
        launchScreen(
            onConfirmClick = { metodoPosicao, metodoMaravilha ->
                positionMethod = metodoPosicao
                wonderMethod = metodoMaravilha
            }

        )

        with(robot) {
            clickRafflePositionRadioButton()
            clickRaffleWonderRadioButton()
            clickConfirmButton()
        }

        assertEquals(RaffleOrChoose.Raffle, positionMethod)
        assertEquals(RaffleOrChoose.Raffle, wonderMethod)
    }

    @Test
    fun onTrailingIconClick() {
        var trailingIconClicked = false
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllRaffle,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.HALIKARNASSOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, Wonders.OLYMPIA, WonderSide.Day),
                    PlayerDetails(cristian.nickname, Wonders.RHODOS, WonderSide.Day)
                )
            ),
            onTrailingIconClick = { trailingIconClicked = true}
        )

        with(robot) {
            clickDayNightIconOnTheIndex(1)
        }

        assertTrue(trailingIconClicked)
    }

    @Test
    fun onTextButtonIconClick() {
        var chooseTextButtonClicked = false
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, null, null),
                    PlayerDetails(anna.nickname, null, null),
                    PlayerDetails(cristian.nickname, null, null)
                )
            ),
            onTextButtonClick = { chooseTextButtonClicked = true}
        )

        with(robot) {
            clickChooseTextButtonOnTheIndex(1)
        }

        assertTrue(chooseTextButtonClicked)
    }

    @Test
    fun onDialogConfirmClick() {
        var wonder: Wonders? = null
        var index: Int? = null
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, null, null),
                    PlayerDetails(anna.nickname, null, null),
                    PlayerDetails(cristian.nickname, null, null)
                )
            ),
            onDialogConfirmClick = { maravilha, posicao ->
                wonder = maravilha
                index = posicao
            }
        )

        with(robot) {
            clickChooseTextButtonOnTheIndex(2)
            selectWonder("RHÓDOS")
            clickWonderDialogConfirmButton()
        }

        assertEquals(Wonders.RHODOS, wonder)
        assertEquals(2, index)

    }

    @Test
    fun onDeselectWonderClick() {
        var deselectButtonClicked = false
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, null, null),
                    PlayerDetails(anna.nickname, null, null),
                    PlayerDetails(cristian.nickname, null, null)
                )
            ),
            onDeselectWonder = { deselectButtonClicked = true }
        )

        with(robot) {
            clickChooseTextButtonOnTheIndex(0)
            clickDeselectWonder()
        }

        assertTrue(deselectButtonClicked)
    }

    @Test
    fun whenClickExpandArrow_ThenRaffleAndChooseBoxDisappear() {
        launchScreen()

        robot.clickExpandLessArrow()

        robot.assertRaffleAndChooseBoxesAreNotShown()

        robot.clickExpandLessArrow()

        robot.assertRaffleAndChooseBoxesAreShown()

    }

    @Test
    fun whenAllRaffle_ThenMoveDownIconDoesNotExistsAndWonderNameIsNotClickable() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllRaffle,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, Wonders.OLYMPIA, WonderSide.Day),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, WonderSide.Day)
                )
            )
        )

        with(robot) {
            assertMoveDownCardIconDoesNotExists()
            assertWonderNameIsNotClickable("RHÓDOS")
        }
    }

    @Test
    fun whenAllChoose_ThenMoveDownIconExistsAndWonderNameIsClickable() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, null, WonderSide.Day),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, WonderSide.Day)
                )
            )
        )

        with(robot) {
            assertMoveDownCardIconExists()
            assertWonderNameIsClickable("RHÓDOS")
            assertWonderNameIsClickable("escolher")
        }
    }

    @Test
    fun whenChoosePositionRaffleWonder_ThenMoveDownIconExistsAndWonderNameIsNotClickable() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.ChoosePositionRaffleWonder,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, null, WonderSide.Day),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, WonderSide.Day)
                )
            )
        )

        with(robot) {
            assertMoveDownCardIconExists()
            assertWonderNameIsNotClickable("RHÓDOS")
        }
    }

    @Test
    fun whenRafflePositionChooseWonder_ThenMoveDownIconDoesNotExistsAndWonderNameIsClickable() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.RafflePositionChooseWonder,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, null, WonderSide.Day),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, WonderSide.Day)
                )
            )
        )

        with(robot) {
            assertMoveDownCardIconDoesNotExists()
            assertWonderNameIsClickable("RHÓDOS")
            assertWonderNameIsClickable("escolher")
        }
    }

    @Test
    fun whenWonderIsNull_ThenChooseTextAppears_AndViceVersa() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, null, null),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, WonderSide.Day)
                )
            )
        )

        with(robot) {
            clickExpandLessArrow()
            assertChooseTextAppears()
            assertWonderNameIsShown("RHÓDOS")
            assertWonderNameIsShown("GIZAH")
        }
    }

    @Test
    fun whenWonderSideIsNull_ThenDayNightIconDisappears() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, null),
                    PlayerDetails(anna.nickname, null, null),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, null)
                )
            )
        )

        robot.assertThereIsNoDayNightIconOnTheScreen()
    }

    @Test
    fun whenWonderSideIsNotNull_ThenDayNightIconAppears() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, null, WonderSide.Day),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, WonderSide.Day)
                )
            )
        )

        robot.assertThereIsDayNightIconOnTheScreen()
    }

    @Test
    fun whenThereIsNicknamesOnMatchPlayerDetails_ThenTheyAreShown() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, Wonders.RHODOS, WonderSide.Day),
                    PlayerDetails(anna.nickname, Wonders.HALIKARNASSOS, WonderSide.Day),
                    PlayerDetails(cristian.nickname, Wonders.GIZAH, WonderSide.Day)
                )
            )
        )

        with(robot) {
            assertThereIsPlayerWhoseNicknameIs(luiz.nickname)
            assertThereIsPlayerWhoseNicknameIs(anna.nickname)
            assertThereIsPlayerWhoseNicknameIs(cristian.nickname)
        }
    }

    @Test
    fun whenIsAdvanceButtonEnabledTrue_ThenItsEnabled() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                isAdvanceButtonEnabled = true
            )
        )

        robot.assertAdvanceButtonIsEnabled()
    }

    @Test
    fun whenAvailableWondersListHasNames_ThenTheyAreShown() {
        launchScreen(
            matchDetailsUiState = MatchDetailsUiState(
                creationMethod = CreationMethod.AllChoose,
                matchPlayersDetails = listOf(
                    PlayerDetails(luiz.nickname, null, null),
                    PlayerDetails(anna.nickname, null, null),
                    PlayerDetails(cristian.nickname, null, null)
                ),
                availableWondersList = listOf(
                    Wonders.HALIKARNASSOS,
                    Wonders.GIZAH
                )
            )
        )

        with(robot) {
            clickChooseTextButtonOnTheIndex(0)
            assertWonderNameIsShown("HALIKARNASSOS")
            assertWonderNameIsShown("GIZAH")
            assertWonderNameIsNotShown("RHÓDOS")
        }
    }

    @Test
    fun whenRadioButtonsAreNotSelected_ThenConfirmButtonIsDisabled() {
        launchScreen()

        with(robot) {
            clickRaffleWonderRadioButton()
            assertConfirmButtonIsDisabled()
            clickRafflePositionRadioButton()
            assertConfirmButtonIsEnabled()
        }
    }

}