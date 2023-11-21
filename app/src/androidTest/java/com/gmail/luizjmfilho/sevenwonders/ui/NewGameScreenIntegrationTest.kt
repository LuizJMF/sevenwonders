package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.gmail.luizjmfilho.sevenwonders.TestData.anna
import com.gmail.luizjmfilho.sevenwonders.TestData.cristian
import com.gmail.luizjmfilho.sevenwonders.TestData.gian
import com.gmail.luizjmfilho.sevenwonders.TestData.luiz
import com.gmail.luizjmfilho.sevenwonders.data.NewGameRepository
import com.gmail.luizjmfilho.sevenwonders.data.PersonDao
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class NewGameScreenIntegrationTest {

    @get:Rule(order = 1)
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val robot = NewGameScreenRobot(rule)

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var dao: PersonDao

    @Before
    fun beforeTests() {
        hiltRule.inject()
    }

    private fun launchScreen() {
        rule.setContent {
            NewGameScreenPrimaria(
                onBackClick = { /* TODO */ },
                onNextClick = { /* TODO */ },
                newGameViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            val repository = NewGameRepository(dao)
                            NewGameViewModel(repository)
                        }
                    }
                )
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenChooseAPlayer_ThenHeIsShownInTheList() = runTest {
        dao.addPlayer(luiz)
        dao.addPlayer(anna)
        dao.addPlayer(cristian)
        launchScreen()

        with(robot) {
            clickChoosePlayerButton(0)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(luiz.nickname)
            clickGenericConfirmButton()
            clickChoosePlayerButton(2)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(anna.nickname)
            clickGenericConfirmButton()
            clickChoosePlayerButton(1)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(cristian.nickname)
            clickGenericConfirmButton()
        }

        with(robot) {
            assertTextFieldInThePositionHasNickname(0,luiz.nickname)
            assertTextFieldInThePositionHasNickname(1, cristian.nickname)
            assertTextFieldInThePositionHasNickname(2, anna.nickname)
        }
    }

//    @OptIn(ExperimentalCoroutinesApi::class)
//    @Test
//    fun whenChooseAPlayer_ThenHeDisappearFromAvailablePlayersList() = runTest {
//        dao.addPlayer(luiz)
//        dao.addPlayer(anna)
//        launchScreen()
//
//        with(robot) {
//            clickChoosePlayerButton(2)
//            clickOnAvailablePlayersListInPlayerThatNicknameIs(luiz.nickname)
//            clickGenericConfirmButton()
//            clickChoosePlayerButton(0)
//        }
//
//        with(robot) {
//            assertThereIsNoPlayerInAvailablePlayersListWithNickname(luiz.nickname)
//        }
//    }
//    PQ ESSE TESTE DÁ CERTO? E QUAL A FORMA CORRETA DE FAZER?

    @Ignore("Esse eu até sei pq tá errado, mas não consigo pensar numa solução")
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenChooseAPlayer_ThenHeDisappearFromAvailablePlayersList() = runTest {
        dao.addPlayer(luiz)
        dao.addPlayer(anna)
        launchScreen()

        with(robot) {
            clickChoosePlayerButton(2)
            assertThereIsAPlayerInTheScreenWithThisNickname(luiz.nickname)
        }

        with(robot) {
            clickOnAvailablePlayersListInPlayerThatNicknameIs(luiz.nickname)
            clickGenericConfirmButton()
            clickChoosePlayerButton(0)
        }

        robot.assertThereIsNoPlayerInTheScreenWithThisNickname(luiz.nickname)
        // esse teste tá dando errado, pq agora eu não tenho mais o "nome" pra procurar
        // na playersListDialog, aí tenho que procurar pelo apelido. Sendo que o apelido,
        // mesmo que eu o escolha, o teste ainda vai achá-lo, já que ele saiu do Dialog,
        // mas foi pra "tela principal". Como corrigir isso?
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenAllPlacesWithPlayers_ThenAddAndAdvanceButtonsAreClickable() = runTest {
        dao.addPlayer(luiz)
        dao.addPlayer(anna)
        dao.addPlayer(cristian)
        launchScreen()

        with(robot) {
            assertAddButtonIsDisabled()
            assertAdvanceButtonIsDisabled()
        }

        with(robot) {
            clickChoosePlayerButton(0)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(luiz.nickname)
            clickGenericConfirmButton()
            clickChoosePlayerButton(1)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(anna.nickname)
            clickGenericConfirmButton()
            clickChoosePlayerButton(2)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(cristian.nickname)
            clickGenericConfirmButton()
        }

        with(robot) {
            assertAddButtonIsEnabled()
            assertAdvanceButtonIsEnabled()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun whenAPlaceWithNameIsRemovedAndAddedAgain_ThenItCameEmpty() = runTest {
        dao.addPlayer(luiz)
        dao.addPlayer(anna)
        dao.addPlayer(cristian)
        dao.addPlayer(gian)
        launchScreen()

        with(robot) {
            clickChoosePlayerButton(0)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(luiz.nickname)
            clickGenericConfirmButton()
            clickChoosePlayerButton(1)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(anna.nickname)
            clickGenericConfirmButton()
            clickChoosePlayerButton(2)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(cristian.nickname)
            clickGenericConfirmButton()
            clickAddPlayerButton()
            clickChoosePlayerButton(3)
            clickOnAvailablePlayersListInPlayerThatNicknameIs(gian.nickname)
            clickGenericConfirmButton()
        }

        robot.assertTextFieldInThePositionHasNickname(3,gian.nickname)

        with(robot) {
            clickRemovePlayerButton()
            clickAddPlayerButton()
        }

        robot.assertTextFieldInThePositionHasNickname(3,"")
    }

}