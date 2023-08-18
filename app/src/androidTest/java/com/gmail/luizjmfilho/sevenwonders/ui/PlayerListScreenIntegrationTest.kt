package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import org.junit.Rule
import org.junit.Test

class PlayerListScreenIntegrationTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val robot = PlayersListScreenRobot(rule)

    private fun launchScreen() {
        rule.setContent {
            PlayersListScreenPrimaria(
                onBackClick = { /*TODO*/ }
            )
        }
    }

    @Test
    fun whenIRegisterAPlayer_thenHeAppearsOnTheList(){
        launchScreen()

        with(robot) {
            clickAddPlayerButton()
            typeName("Luiz")
            typeNickname("Zinho")
            clickConfirmAddPlayerButton()
            typeName("Crístian Deives")
            typeNickname("Deivinho")
            clickConfirmAddPlayerButton()
        }

        with(robot) {
            assertPlayerNameInTheListIs("Crístian Deives", 0)
            assertPlayerNicknameInTheListIs("Deivinho", 0)
            assertPlayerNameInTheListIs("Luiz", 1)
            assertPlayerNicknameInTheListIs("Zinho", 1)
        }
    }

    @Test
    fun whenIRegisterEmptyNameAndNickname_thenEmptyErrorsAppear() {
        launchScreen()

        with(robot){
            clickAddPlayerButton()
            typeName("")
            typeNickname("")
            clickConfirmAddPlayerButton()
        }


        with(robot) {
            assertEmptyErrorMessageIsShownInNameTextField()
            assertEmptyErrorMessageIsShownInNicknameTextField()
        }
    }

    @Test
    fun whenITryToDeleteSomePlayerInTheList_thenHeShouldBeDeleted(){
        launchScreen()

        with(robot) {
            clickAddPlayerButton()
            typeName("Luiz")
            typeNickname("Zinho")
            clickConfirmAddPlayerButton()
            typeName("Crístian Deives")
            typeNickname("Deivinho")
            clickConfirmAddPlayerButton()
            clickDeletePlayerButton()
            clickIconDelete(0)
        }

        with(robot) {
            assertPlayerIsNotInTheList("Crístian Deives")
            assertPlayerNameInTheListIs("Luiz", 0)
            assertPlayerNicknameInTheListIs("Zinho", 0)
            assertThereIsNoOneInTheListInPosition(1)
        }

    }
}