package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class NumberInputFieldTest {
    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()

    private val robot = NumberInputFieldRobot(rule)

    @Test
    fun initialState() {
        val number = 42

        launchNumberInputField(number = number, onNumberChange = {})

        robot.assertNumberIs(number)
    }

    @Test
    fun whenDecreaseButtonIsClicked_thenNumberIsDecreased() {
        val number = 42
        var changedNumber = 0

        launchNumberInputField(number = number, onNumberChange = { changedNumber = it })
        robot.clickDecreaseButton()

        assertEquals(number - 1, changedNumber)
    }

    @Test
    fun whenIncreaseButtonIsClicked_thenNumberIsIncreased() {
        val number = 42
        var changedNumber = 0

        launchNumberInputField(number = number, onNumberChange = { changedNumber = it })
        robot.clickIncreaseButton()

        assertEquals(number + 1, changedNumber)
    }

    @Test
    fun whenDecreaseButtonIsClickedAndCallbackDoesNotAllowNegativeNumberAndNumberWouldBeNegative_thenNumberDoesNotChange() {
        val number = 1
        var changedNumber = 0

        launchNumberInputField(number = number, onNumberChange = { if (it >= 0) changedNumber = it })
        robot.apply {
            clickDecreaseButton()
            clickDecreaseButton()
        }

        assertEquals(number - 1, changedNumber)
    }

    @Test
    fun whenDecreaseButtonIsLongClicked_thenNumberIsChangedMultipleTimes() {
        var onNumberChangeCallCount = 0

        launchNumberInputField(number = 42, onNumberChange = { onNumberChangeCallCount++ })

        robot.longClickDecreaseButton()

        assertTrue("'onNumberChange' was called $onNumberChangeCallCount time(s) after long clicking the decrease button", onNumberChangeCallCount > 1)
    }

    @Test
    fun whenIncreaseButtonIsLongClicked_thenNumberIsChangedMultipleTimes() {
        var onNumberChangeCallCount = 0

        launchNumberInputField(number = 42, onNumberChange = { onNumberChangeCallCount++ })

        robot.longClickIncreaseButton()

        assertTrue("'onNumberChange' was called $onNumberChangeCallCount time(s) after long clicking the increase button", onNumberChangeCallCount > 1)
    }

    private fun launchNumberInputField(
        number: Int,
        onNumberChange: (Int) -> Unit,
    ) {
        rule.setContent {
            NumberInputField(
                number = number,
                textColor = Color.Black,
                onNumberChange = onNumberChange,
            )
        }
    }
}