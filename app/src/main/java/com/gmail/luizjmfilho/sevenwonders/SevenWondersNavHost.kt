package com.gmail.luizjmfilho.sevenwonders

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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
fun SevenWondersNavHost() {
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
                onScienceSimulatorClick = { navController.navigate(ScreenNames.ScienceSimulatorScreen.name) },
                homeViewModel = hiltViewModel(),
            )
        }

        composable(
            route = "${ScreenNames.PlayersListScreen.name}?alreadySelectedPlayerIds={alreadySelectedPlayerIds}",
            arguments = listOf(
                navArgument("alreadySelectedPlayerIds") { nullable = true },
            ),
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Companion.Left,
                    animationSpec = tween(500)
                )
            },
        ) {
            PlayersListScreenPrimaria(
                onSelectPlayer = { selectedPlayerId ->
                    navController.previousBackStackEntry?.savedStateHandle?.set("selectedPlayerId", selectedPlayerId)
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
                    navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("selectedPlayerId")
                    navController.navigate("${ScreenNames.MatchDetailsScreen.name}/${playerNicknames.joinToString(",")}")
                },
                onChoosePlayerClick = { alreadySelectedPlayerIds ->
                    navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("selectedPlayerId")
                    if (alreadySelectedPlayerIds.isEmpty()) {
                        navController.navigate(ScreenNames.PlayersListScreen.name)
                    } else {
                        navController.navigate(
                            "${ScreenNames.PlayersListScreen.name}?alreadySelectedPlayerIds=${alreadySelectedPlayerIds.joinToString(",")}"
                        )
                    }
                },
                selectedIdFromPlayerListScreen = backStack.savedStateHandle["selectedPlayerId"]
            )
        }

        composable(
            route = "${ScreenNames.MatchDetailsScreen.name}/{playerIds}",
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
                onBackClick = {
                    navController.navigateUp()
                },
                // "CalculationScreen/3,12,5,11/EPHESOS,GIZAH,RHODOS,HALIKARNASSOS/Day,Day,Night,Day"
                onNextClick = { playerIds, wonders, wonderSides ->
                    navController.navigate("${ScreenNames.CalculationScreen.name}/${playerIds.joinToString(",")}/${wonders.joinToString(",")}/${wonderSides.joinToString(",")}")
                }
            )
        }

        composable(
            route = "${ScreenNames.CalculationScreen.name}/{playerIds}/{wonders}/{wonderSides}",
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
                onBackClick = {navController.navigateUp()},
                aboutViewModel = hiltViewModel(),
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
