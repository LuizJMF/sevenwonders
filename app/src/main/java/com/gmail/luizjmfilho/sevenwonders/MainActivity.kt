package com.gmail.luizjmfilho.sevenwonders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.gmail.luizjmfilho.sevenwonders.ui.HomeScreen
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gmail.luizjmfilho.sevenwonders.ui.PlayersListScreen


enum class ScreenNames() {
    TelaInicial,
    TelaListaDeJogadores,
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
                    startDestination = ScreenNames.TelaInicial.name,
                ) {
                    composable(route = ScreenNames.TelaInicial.name) {
                        HomeScreen(
                            onListaDeJogadoresClick = {
                                navController.navigate(ScreenNames.TelaListaDeJogadores.name)
                            }
                        )
                    }

                    composable(route = ScreenNames.TelaListaDeJogadores.name) {
                        PlayersListScreen(
                            personList = emptyList(),
                            onAddJogadorClick = { /*TODO*/ },
                            onApagarJogadorClick = { /*TODO*/ },
                            onBackClick = {
                                navController.navigateUp()
                            }
                        )
                    }
                }
            }
        }
    }
}