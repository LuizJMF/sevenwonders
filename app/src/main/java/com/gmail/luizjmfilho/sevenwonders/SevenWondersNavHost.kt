package com.gmail.luizjmfilho.sevenwonders

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.luizjmfilho.sevenwonders.ui.HomeScreen
import com.gmail.luizjmfilho.sevenwonders.ui.NewGameScreenSecundaria
import com.gmail.luizjmfilho.sevenwonders.ui.PlayersListScreenPrimaria

@Composable
fun SevenWondersNavHost() {
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