package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertHasNoClickAction
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsNotSelected
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gmail.luizjmfilho.sevenwonders.R
import org.junit.Assert.assertEquals

class MatchDetailsScreenRobot(
    private val rule: AndroidComposeTestRule<*, out ComponentActivity>
) {

    fun assertConfirmButtonIsDisabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_confirm_text), ignoreCase = true).assertIsNotEnabled()
    }

    fun assertConfirmButtonIsEnabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_confirm_text), ignoreCase = true).assertIsEnabled()
    }

    fun assertAdvanceButtonIsDisabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_advance_button), ignoreCase = true).assertIsNotEnabled()
    }

    fun assertAdvanceButtonIsEnabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_advance_button), ignoreCase = true).assertIsEnabled()
    }

    fun assertRafflePositionRadioButtonIsNotSelected() {
        rule.onAllNodesWithTag(raffleRadioButtonTestTag)[0].assertIsNotSelected()
    }

    fun assertChoosePositionRadioButtonIsNotSelected() {
        rule.onAllNodesWithTag(chooseRadioButtonTestTag)[0].assertIsNotSelected()
    }

    fun assertRaffleWonderRadioButtonIsNotSelected() {
        rule.onAllNodesWithTag(raffleRadioButtonTestTag)[1].assertIsNotSelected()
    }

    fun assertChooseWonderRadioButtonIsNotSelected() {
        rule.onAllNodesWithTag(chooseRadioButtonTestTag)[1].assertIsNotSelected()
    }

    fun assertRaffleAndChooseBoxesAreShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_confirm_text), ignoreCase = true).assertExists()
    }

    fun assertRaffleAndChooseBoxesAreNotShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_confirm_text), ignoreCase = true).assertDoesNotExist()
    }

    fun clickRafflePositionRadioButton() {
        rule.onAllNodesWithTag(raffleRadioButtonTestTag)[0].performClick()
    }


    fun clickRaffleWonderRadioButton() {
        rule.onAllNodesWithTag(raffleRadioButtonTestTag)[1].performClick()
    }


    fun clickConfirmButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_confirm_text), ignoreCase = true).performClick()
    }

    fun clickWonderDialogConfirmButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_confirm_text)).performClick()
    }

    fun selectWonder(wonder: String) {
        rule.onNodeWithText(wonder).performClick()
    }


    fun clickDayNightIconOnTheIndex(index: Int) {
        rule.onAllNodesWithTag(dayNightIconTestTag)[index].performClick()
    }

    fun clickChooseTextButtonOnTheIndex(index: Int) {
        rule.onAllNodesWithTag(chooseTextButtonTestTag)[index].performClick()
    }

    fun clickDeselectWonder() {
        rule.onNodeWithText(rule.activity.getString(R.string.match_details_deselect_wonder_dialog)).performClick()
    }

    fun clickExpandLessArrow() {
        rule.onNodeWithTag(expandLessTestTag).performClick()
    }

    fun assertMoveDownCardIconDoesNotExists() {
        rule.onAllNodesWithTag(moveDownCardIconTestTag)[0].assertDoesNotExist()
    }

    fun assertMoveDownCardIconExists() {
        rule.onAllNodesWithTag(moveDownCardIconTestTag)[0].assertExists()
    }

    fun assertWonderNameIsNotClickable(wonder: String) {
        rule.onNodeWithText(wonder).assertHasNoClickAction()
    }

    fun assertWonderNameIsClickable(wonder: String) {
        rule.onNodeWithText(wonder).assertHasClickAction()
    }

    fun assertChooseTextAppears() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_choose), ignoreCase = true).assertExists()
    }

    fun assertWonderNameIsShown(wonder: String) {
        rule.onNodeWithText(wonder).assertExists()
    }

    fun assertWonderNameIsNotShown(wonder: String) {
        rule.onNodeWithText(wonder).assertDoesNotExist()
    }

    fun assertThereIsNoDayNightIconOnTheScreen() {
        rule.onNodeWithTag(dayNightIconTestTag).assertDoesNotExist()
    }

    fun assertThereIsDayNightIconOnTheScreen() {
        rule.onAllNodesWithTag(dayNightIconTestTag)[0].assertExists()
    }

    fun assertThereIsPlayerWhoseNicknameIs(nickname: String) {
        rule.onNodeWithText(nickname).assertExists()
    }

    fun assertThereIsNoPlayerWhoseNicknameIs(nickname: String) {
        rule.onNodeWithText(nickname).assertDoesNotExist()
    }

    fun assertDayNightIconInIndexIs(icon: WonderSide, index: Int) {
        assertEquals(icon, rule.onAllNodesWithTag(dayNightIconTestTag)[index])
    }

}