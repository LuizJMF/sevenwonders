package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gmail.luizjmfilho.sevenwonders.SevenWondersNavHost
import com.gmail.luizjmfilho.sevenwonders.TestData.anna
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@Ignore("O banco de dados de um teste estava interferindo nos demais")
class SevenWondersIntegrationTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val sevenWondersRobot = SevenWondersRobot(rule)
    private val homeScreenRobot = HomeScreenRobot(rule)
    private val playersListScreenRobot = PlayersListScreenRobot(rule)
    private val newGameScreenRobot = NewGameScreenRobot(rule)


    private fun launchScreen() {
        rule.setContent {
            SevenWondersTheme {
                SevenWondersNavHost(
                    windowWidthSizeClass = WindowWidthSizeClass.Compact
                )
            }
        }
    }


    @Test
    fun sevenWondersNavigation() {
        launchScreen()


        with(homeScreenRobot) {
            assertHomeScreenIsCurrentScreen()
            clickPlayersListButton()
        }
        playersListScreenRobot.assertPlayersListScreenIsCurrentScreen()
        sevenWondersRobot.clickNavigationBackButton()
        with(homeScreenRobot) {
            assertHomeScreenIsCurrentScreen()
            clickNewGameButton()
        }
        newGameScreenRobot.assertNewGameScreenIsCurrentScreen()
        sevenWondersRobot.clickNavigationBackButton()
        homeScreenRobot.assertHomeScreenIsCurrentScreen()
    }

    @Test
    fun whenPlayersListIsEmpty_ThenMessageShowsItInNewGameAvailablePlayersList() {
        launchScreen()

        homeScreenRobot.clickNewGameButton()
        with(newGameScreenRobot) {
            clickChoosePlayerButton(0)
            assertTextForEmptyAvailablePlayersListIsShown()
        }
    }

    @Test
    fun whenPlayersListHasPlayersRegistered_ThenTheyAreShownInNewGameAvailablePlayersList() {
        launchScreen()

        homeScreenRobot.clickPlayersListButton()
        with(playersListScreenRobot) {
            clickAddPlayerButton()
            typeName(luiz.name)
            typeNickname(luiz.nickname)
            clickConfirmAddPlayerButton()
            typeName(cristian.name)
            typeNickname(cristian.nickname)
            clickConfirmAddPlayerButton()
        }
        sevenWondersRobot.clickNavigationBackButton()
        homeScreenRobot.clickNewGameButton()
        with(newGameScreenRobot) {
            clickChoosePlayerButton(0)
            assertThereIsAPlayerInTheScreenWithThisName(luiz.name)
            assertThereIsAPlayerInTheScreenWithThisName(cristian.name)
            assertThereIsNoPlayerInTheScreenWithThisName(anna.name)
        }


    }

}