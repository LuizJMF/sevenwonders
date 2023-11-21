package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChild
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.gmail.luizjmfilho.sevenwonders.R

class NewGameScreenRobot(
    private val rule: AndroidComposeTestRule<*, out ComponentActivity>
) {
    fun assertThereIsNoTextFieldPlayerThatPositionIs(textFieldPosition: Int) {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_text_field_label, textFieldPosition + 1)).assertDoesNotExist()
    }

    fun assertThereIsTextFieldPlayerThatPositionIs(textFieldPosition: Int) {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_text_field_label, textFieldPosition + 1)).assertExists()
    }

    fun assertTextFieldInPositionIsEmpty(textFieldPosition: Int) {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_text_field_label, textFieldPosition + 1)).assert(hasEditableText(""))
    }

    fun assertAddButtonIsDisabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_add_player_button)).assertIsNotEnabled()
    }

    fun assertAddButtonIsEnabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_add_player_button)).assertIsEnabled()
    }

    fun assertAdvanceButtonIsDisabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_advance_button), ignoreCase = true).assertIsNotEnabled()
    }

    fun assertAdvanceButtonIsEnabled() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_advance_button), ignoreCase = true).assertIsEnabled()
    }

    fun assertAddPlayerButtonIsNotShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_add_player_button)).assertDoesNotExist()
    }

    fun assertRemoveButtonIsNotShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_remove_player_button)).assertDoesNotExist()
    }

    fun assertRemoveButtonIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_remove_player_button)).assertExists()
    }

    fun clickAdvanceButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_advance_button), ignoreCase = true).performClick()
    }

    fun clickChoosePlayerButton(index: Int) {
        rule.onAllNodesWithTag(choosePlayerTrailingIconTestTag, true)[index].performClick()
    }

    fun clickAddPlayerButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_add_player_button)).performClick()
    }

    fun clickRemovePlayerButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.new_game_remove_player_button)).performClick()
    }

    fun assertTextFieldInThePositionHasNickname(position: Int, nickname: String) {
        rule.onAllNodesWithTag(newGameTextFieldTestTag)[position].assert(hasEditableText(nickname))
    }

    fun clickOnAvailablePlayersListInPlayerThatNicknameIs(nickname: String) {
        rule.onNodeWithText(nickname).performClick()
    }

    fun clickGenericConfirmButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.generic_confirm_text)).performClick()
    }

    fun assertThereIsNoPlayerInAvailablePlayersListWithNickname(nickname: String) {
        rule.onNodeWithTag(eachPersonNicknameInTheListTestTag).assertDoesNotExist()
    }

    fun assertThereIsNoPlayerInTheScreenWithThisName(name: String) {
        rule.onNodeWithText(name).assertDoesNotExist()
    }

    fun assertThereIsAPlayerInTheScreenWithThisName(name: String) {
        rule.onNodeWithText(name).assertExists()
    }

    fun assertNewGameScreenIsCurrentScreen() {
        rule.onNodeWithTag(newGameScreenTestTag).assertExists()
    }

    fun assertTextForEmptyAvailablePlayersListIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.alert_dialog_new_game_text_when_list_is_empty)).assertExists()
    }

    fun assertPlayerIsSelected(nickname: String) {
        rule.onNodeWithText(nickname, substring = true).onChild().assertIsSelected()
    }
}