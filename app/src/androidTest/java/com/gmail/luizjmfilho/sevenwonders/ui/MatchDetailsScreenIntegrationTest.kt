package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gmail.luizjmfilho.sevenwonders.TestData.anna
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.gian
import com.gmail.luizjmfilho.sevenwonders.TestData.ivana
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class MatchDetailsScreenIntegrationTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val robot = MatchDetailsScreenRobot(rule)

    private fun launchScreen() {
        rule.setContent {
            MatchDetailsScreenPrimaria(
                onBackClick = { /*TODO*/ },
                onNextClick = { /*TODO*/ },
                viewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            MatchDetailsViewModel(SavedStateHandle(mapOf("playerNicknames" to "${luiz.name},${anna.name},${cristian.name},${gian.name}")))
                        }
                    }
                )
            )
        }
    }

    @Test
    fun whenAllRaffle() {
        launchScreen()

        with(robot) {
            assertConfirmButtonIsDisabled()
            assertAdvanceButtonIsDisabled()
            clickRafflePositionRadioButton()
            assertConfirmButtonIsDisabled()
            clickRaffleWonderRadioButton()
            assertConfirmButtonIsEnabled()
            clickConfirmButton()
            assertAdvanceButtonIsEnabled()
            assertThereIsPlayerWhoseNicknameIs(luiz.name)
            assertThereIsPlayerWhoseNicknameIs(anna.name)
            assertThereIsPlayerWhoseNicknameIs(cristian.name)
            assertThereIsPlayerWhoseNicknameIs(gian.name)
            assertThereIsNoPlayerWhoseNicknameIs(ivana.name)
            assertThereIsDayNightIconOnTheScreen()
            assertMoveDownCardIconDoesNotExists()
//            assertDayNightIconInIndexIs(WonderSide.Day, 0)
//            clickDayNightIconOnTheIndex(0)
//            assertDayNightIconInIndexIs(WonderSide.Night, 0)
//            essa parte acima não funciona. Como fazer?
        }

        @Test
        fun whenAllChoose() {
            launchScreen()



        }

    }

}