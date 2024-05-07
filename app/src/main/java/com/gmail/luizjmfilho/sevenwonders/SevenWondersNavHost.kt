package com.gmail.luizjmfilho.sevenwonders

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gmail.luizjmfilho.sevenwonders.ui.AboutScreen
import com.gmail.luizjmfilho.sevenwonders.ui.CalculationScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.HomeScreen
import com.gmail.luizjmfilho.sevenwonders.ui.MatchDetailsScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.MatchesHistoryPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.NewGameScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.PlayersListScreenPrimaria
import com.gmail.luizjmfilho.sevenwonders.ui.ScienceSimulatorScreenPrimaria
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
        composable(
            route = ScreenNames.HomeScreen.name,
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
        ) {
            HomeScreen(
                onCriarPartidaClick = { navController.navigate(ScreenNames.NewGameScreen.name) },
                onMatchesHistoryClick = { navController.navigate(ScreenNames.MatchesHistoryScreen.name) },
                onStatsClick = { navController.navigate(ScreenNames.StatsScreen.name) },
                onAboutClick = { navController.navigate(ScreenNames.AboutScreen.name) },
                onScienceSimulatorClick = { navController.navigate(ScreenNames.ScienceSimulatorScreen.name) }
            )
        }

        composable(
            route = "${ScreenNames.PlayersListScreen.name}/{info}",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
        ) { backStack ->
            PlayersListScreenPrimaria(
                onSelectPlayer = { activePlayersList ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("xxx", activePlayersList)
                    navController.navigateUp()
                }
            )
        }

        composable(
            route = ScreenNames.NewGameScreen.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
        ) { backStack ->
            NewGameScreenPrimaria(
                onBackClick = {
                    navController.navigateUp()
                },
                onNextClick = { playerNicknames ->
                    navController.navigate("${ScreenNames.MatchDetailsScreen.name}/${playerNicknames.joinToString(",")}")
                },
                onChoosePlayerClick = { playerIndexBeingSelected, activePlayersList ->
                    navController.navigate("${ScreenNames.PlayersListScreen.name}/${listOf(playerIndexBeingSelected.toString(), activePlayersList.joinToString(",")).joinToString(",")}")
                },
                activePlayersListGambiarra = backStack.savedStateHandle.get<List<String>>("xxx") ?: List(7) { "" }
            )
        }

        composable(
            route = "${ScreenNames.MatchDetailsScreen.name}/{playerNicknames}",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
        ) {
            MatchDetailsScreenPrimaria(
                onBackClick = { navController.navigateUp() },
                onNextClick = { playerDetailsList ->
                    navController.navigate("${ScreenNames.CalculationScreen.name}/${playerDetailsList.joinToString(";")}")
                }
            )
        }

        composable(
            route = "${ScreenNames.CalculationScreen.name}/{playerDetailsList}",
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Right,
                    animationSpec = tween(500)
                )
            },
        ) {
            CalculationScreenPrimaria(
                onBackClick = { navController.navigateUp() },
                onConfirmNextScreen = {
                    navController.navigate(route = ScreenNames.SummaryScreen.name)
                }
            )
        }

        composable(
            route = ScreenNames.SummaryScreen.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
        ) {
            SummaryScreenPrimaria(
                onNextClick = { navController.popBackStack(route = ScreenNames.HomeScreen.name, false) }
            )
        }

        composable(
            route = ScreenNames.MatchesHistoryScreen.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500),
                )
            },
        ) {
            MatchesHistoryPrimaria(
                onBackClick = {navController.navigateUp()}
            )
        }

        composable(
            route = ScreenNames.StatsScreen.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500),

                    )
            },
        ) {
            StatsScreenPrimaria(
                onBackClick = {navController.navigateUp()}
            )
        }

        composable(
            route = ScreenNames.AboutScreen.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500),

                    )
            },
        ) {
            AboutScreen(
                onBackClick = {navController.navigateUp()}
            )
        }

        composable(
            route = ScreenNames.ScienceSimulatorScreen.name,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500),

                    )
            },
        ) {
            ScienceSimulatorScreenPrimaria(
                onBackClick = {navController.navigateUp()},
            )
        }
    }
}
