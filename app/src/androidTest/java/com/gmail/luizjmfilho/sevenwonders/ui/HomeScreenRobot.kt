package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gmail.luizjmfilho.sevenwonders.R

class HomeScreenRobot(
    private val rule: AndroidComposeTestRule<*, ComponentActivity>
) {

    fun clickPlayersListButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.players_list_button)).performClick()
    }

    fun assertHomeScreenIsCurrentScreen() {
        rule.onNodeWithTag(homeScreenTestTag).assertExists()
    }

    fun clickNewGameButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.criar_partida_button)).performClick()
    }

}