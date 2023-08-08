package com.gmail.luizjmfilho.sevenwonders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gmail.luizjmfilho.sevenwonders.ui.HomeScreen
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import androidx.navigation.compose.rememberNavController
import com.gmail.luizjmfilho.sevenwonders.ui.NewGameScreenSecundaria
import com.gmail.luizjmfilho.sevenwonders.ui.NewGameViewModel
import com.gmail.luizjmfilho.sevenwonders.ui.PlayersListScreenPrimaria


enum class ScreenNames {
    HomeScreen,
    PlayersListScreen,
    NewGameScreen,
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SevenWondersTheme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = ScreenNames.HomeScreen.name,
                ) {
                    composable(route = ScreenNames.HomeScreen.name) {
                        HomeScreen(
                            onCriarPartidaClick = {
                                navController.navigate(ScreenNames.NewGameScreen.name)
                            },
                            onListaDeJogadoresClick = {
                                navController.navigate(ScreenNames.PlayersListScreen.name)
                            }
                        )
                    }

                    composable(route = ScreenNames.PlayersListScreen.name) {
                        PlayersListScreenPrimaria(
                            onBackClick = {
                                navController.navigateUp()
                            }
                        )
                    }

                    composable(route = ScreenNames.NewGameScreen.name) {
                        NewGameScreenSecundaria(
                            onBackClick = {
                                navController.navigateUp()
                            },
                            onNextClick = {}
                        )
                    }
                }
            }
        }
    }
}