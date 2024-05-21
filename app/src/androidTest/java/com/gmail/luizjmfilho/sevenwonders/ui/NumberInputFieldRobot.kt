package com.gmail.luizjmfilho.sevenwonders.ui

import android.content.Context
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.longClick
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import com.gmail.luizjmfilho.sevenwonders.R

class NumberInputFieldRobot(
    private val rule: AndroidComposeTestRule<*, *>,
) {
    private val context: Context
        get() = rule.activity

    fun assertNumberIs(number: Int) {
        onNumberText().assertTextEquals(number.toString())
    }

    fun clickDecreaseButton() {
        onDecreaseButton().performClick()
    }

    fun longClickDecreaseButton() {
        onDecreaseButton().performTouchInput {
            longClick(durationMillis = LONG_CLICK_DURATION_MILLIS)
        }
    }

    fun clickIncreaseButton() {
        onIncreaseButton().performClick()
    }

    fun longClickIncreaseButton() {
        onIncreaseButton().performTouchInput {
            longClick(durationMillis = LONG_CLICK_DURATION_MILLIS)
        }
    }

    private fun onDecreaseButton(): SemanticsNodeInteraction =
        rule.onNodeWithContentDescription(context.getString(R.string.decrease_number_button_content_description))

    private fun onIncreaseButton(): SemanticsNodeInteraction =
        rule.onNodeWithContentDescription(context.getString(R.string.increase_number_button_content_description))

    private fun onNumberText(): SemanticsNodeInteraction =
        rule.onNodeWithTag(NumberInputFieldNumberTestTag)

    companion object {
        private const val LONG_CLICK_DURATION_MILLIS = 1_000L
    }
}