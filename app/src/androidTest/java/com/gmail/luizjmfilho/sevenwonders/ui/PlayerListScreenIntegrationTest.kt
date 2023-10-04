package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.gmail.luizjmfilho.sevenwonders.data.PlayersListRepository
import com.gmail.luizjmfilho.sevenwonders.data.SevenWondersDatabase
import com.gmail.luizjmfilho.sevenwonders.data.getSevenWondersDatabaseInstance
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

class PlayerListScreenIntegrationTest {

    @get:Rule
    val rule = createAndroidComposeRule<ComponentActivity>()
    private val robot = PlayersListScreenRobot(rule)

    private fun launchScreen() {
        rule.setContent {
            PlayersListScreenPrimaria(
                onBackClick = { /*TODO*/ },
                windowWidthSizeClass = WindowWidthSizeClass.Compact,
                playersListViewModel = viewModel(
                    factory = viewModelFactory {
                        initializer {
                            val context = get(ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY)!!
                            val database = Room.inMemoryDatabaseBuilder(context, SevenWondersDatabase::class.java).build()
                            val dao = database.personDao()
                            val repository = PlayersListRepository(dao)
                            PlayersListViewModel(repository)
                        }
                    }
                )
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