package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTextExactly
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.gmail.luizjmfilho.sevenwonders.R

class PlayersListScreenRobot(
    private val rule: AndroidComposeTestRule<*, out ComponentActivity>
) {
    fun clickAddPlayerButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.add_player_button)).performClick()
    }

    fun typeName(name: String) {
        rule.onNodeWithText(rule.activity.getString(R.string.add_player_label_name_text_field)).performTextInput(name)
    }

    fun typeNickname(nickname: String) {
        rule.onNodeWithText(rule.activity.getString(R.string.add_player_label_nickname_text_field)).performTextInput(nickname)
    }

    fun clickDeletePlayerButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.delete_player_button)).performClick()
    }

    fun clickIconDelete(index: Int) {
        rule.onAllNodesWithTag(deleteIconTestTag)[index].performClick()
    }

    fun clickCancelAddPlayerButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.add_player_window_cancel_button)).performClick()
    }

    fun clickConfirmAddPlayerButton() {
        rule.onNodeWithText(rule.activity.getString(R.string.add_player_window_confirm_button)).performClick()
    }

    fun assertPlayersListIsEmpty() {
        rule.onNodeWithText(rule.activity.getString(R.string.empty_players_list)).assertExists()
    }

    fun assertAddPlayerWindowIsExpanded() {
        rule.onNodeWithText(rule.activity.getString(R.string.add_player_window_confirm_button)).assertExists()
    }

    fun assertAddPlayerWindowIsNotExpanded() {
        rule.onNodeWithText(rule.activity.getString(R.string.add_player_window_confirm_button)).assertDoesNotExist()
    }

    fun assertPlayerNameInTheListIs(name: String, position: Int) {
        rule.onAllNodesWithTag(eachPersonNameInTheListTestTag)[position].assertTextEquals(name)
    }

    fun assertPlayerNicknameInTheListIs(nickname: String, position: Int) {
        rule.onAllNodesWithTag(eachPersonNicknameInTheListTestTag)[position].assertTextEquals(nickname)
    }

    fun assertDeleteIconsAreNotShown() {
        rule.onAllNodesWithTag(deleteIconTestTag).assertCountEquals(0)
    }

    fun assertDeleteIconsAreShown() {
        rule.onAllNodesWithTag(deleteIconTestTag)[0].assertExists()
    }

    fun assertNicknameTextFieldHasNickname(nickname: String) {
        rule.onNodeWithTag(nicknameTextFieldTestTag).assert(hasEditableText(nickname))
    }

    fun assertEmptyErrorMessageIsShownInNicknameTextField() {
        rule.onNodeWithTag(nicknameTextFieldTestTag).assertTextContains(rule.activity.getString(R.string.empty_error_message))
    }

    fun assertNicknameExistsErrorMessageIsShown() {
        rule.onNodeWithText(rule.activity.getString(R.string.nickname_exists_erros_message)).assertExists()
    }

    fun assertPlayersListScreenIsCurrentScreen() {
        rule.onNodeWithTag(playersListScreenTestTag).assertExists()
    }

    fun assertPlayerIsNotInTheList(name: String) {
        rule.onAllNodesWithTag(eachPersonNameInTheListTestTag).filter(hasTextExactly(name)).assertCountEquals(0)
    }

    fun assertThereIsNoOneInTheListInPosition(position: Int) {
        rule.onAllNodesWithTag(eachPersonNameInTheListTestTag)[position].assertDoesNotExist()
        rule.onAllNodesWithTag(eachPersonNicknameInTheListTestTag)[position].assertDoesNotExist()
    }


}