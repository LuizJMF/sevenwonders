package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gmail.luizjmfilho.sevenwonders.SevenWondersNavHost
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import org.junit.Rule
import org.junit.Test

class SevenWondersIntegrationTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val sevenWondersRobot = SevenWondersRobot(rule)
    private val homeScreenRobot = HomeScreenRobot(rule)
    private val playersListScreenRobot = PlayersListScreenRobot(rule)


    private fun launchScreen() {
        rule.setContent {
            SevenWondersTheme {
                SevenWondersNavHost()
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
        homeScreenRobot.assertHomeScreenIsCurrentScreen()
    }

}