package com.gmail.luizjmfilho.sevenwonders

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.luizjmfilho.sevenwonders.ui.CalculationScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.HomeScreen
import com.gmail.luizjmfilho.sevenwonders.ui.MatchDetailsScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.MatchesHistoryPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.NewGameScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.PlayersListScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.StatsScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.SummaryScreenPrimaria

@Composable
fun SevenWondersNavHost(
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenNames.HomeScreen.name,
    ) {
        composable(route = ScreenNames.HomeScreen.name) {
            HomeScreen(
                onCriarPartidaClick = { navController.navigate(ScreenNames.NewGameScreen.name) },
                onListaDeJogadoresClick = { navController.navigate(ScreenNames.PlayersListScreen.name) },
                onMatchesHistoryClick = { navController.navigate(ScreenNames.MatchesHistoryScreen.name) },
                onStatsClick = { navController.navigate(ScreenNames.StatsScreen.name) }
            )
        }

        composable(route = ScreenNames.PlayersListScreen.name) {
            PlayersListScreenPrimaria(
                windowWidthSizeClass = windowWidthSizeClass,
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(route = ScreenNames.NewGameScreen.name) {
            NewGameScreenPrimaria(
                onBackClick = {
                    navController.navigateUp()
                },
                onNextClick = { playerNicknames ->
                    navController.navigate("${ScreenNames.MatchDetailsScreen.name}/${playerNicknames.joinToString(",")}")
                }
            )
        }

        composable(route = "${ScreenNames.MatchDetailsScreen.name}/{playerNicknames}") {
            MatchDetailsScreenPrimaria(
                onBackClick = { navController.navigateUp() },
                onNextClick = { playerDetailsList ->
                    navController.navigate("${ScreenNames.CalculationScreen.name}/${playerDetailsList.joinToString(";")}")
                }
            )
        }

        composable(route = "${ScreenNames.CalculationScreen.name}/{playerDetailsList}") {
            CalculationScreenPrimaria(
                onBackClick = { navController.navigateUp() },
                onConfirmNextScreen = { navController.navigate(ScreenNames.SummaryScreen.name) }
            )
        }

        composable(route = ScreenNames.SummaryScreen.name) {
            SummaryScreenPrimaria(
                onBackClick = { navController.navigateUp() },
                onNextClick = { navController.popBackStack(route = ScreenNames.HomeScreen.name, false) }
            )
        }

        composable(route = ScreenNames.MatchesHistoryScreen.name) {
            MatchesHistoryPrimaria(
                onBackClick = {navController.navigateUp()}
            )
        }

        composable(route = ScreenNames.StatsScreen.name) {
            StatsScreenPrimaria(
                onBackClick = {navController.navigateUp()}
            )
        }
    }
}