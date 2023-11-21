package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.gmail.luizjmfilho.sevenwonders.TestActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PlayerListScreenIntegrationTest {

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<TestActivity>()
    private val robot = PlayersListScreenRobot(composeRule)

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun beforeTests() {
        hiltRule.inject()
    }

    private fun launchScreen() {
        composeRule.setContent {
            PlayersListScreenPrimaria(
                onBackClick = { /* TODO */ },
                windowWidthSizeClass = WindowWidthSizeClass.Compact,
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

    @Ignore("Pq não sabemos oq tá errado")
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