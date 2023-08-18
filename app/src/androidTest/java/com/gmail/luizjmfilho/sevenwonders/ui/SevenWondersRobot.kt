package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import com.gmail.luizjmfilho.sevenwonders.R

class SevenWondersRobot(
    private val rule: AndroidComposeTestRule<*, ComponentActivity>
) {

    fun clickNavigationBackButton() {
        rule.onNodeWithContentDescription(rule.activity.getString(R.string.navigation_back_content_description)).performClick()
    }

}