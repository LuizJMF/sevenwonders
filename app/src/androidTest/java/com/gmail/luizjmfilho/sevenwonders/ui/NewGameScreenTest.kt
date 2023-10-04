package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule

class NewGameScreenTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val robot = NewGameScreenRobot(rule)



}