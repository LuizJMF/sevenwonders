package com.gmail.luizjmfilho.sevenwonders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.core.view.WindowCompat
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import dagger.hilt.android.AndroidEntryPoint


enum class ScreenNames {
    HomeScreen,
    PlayersListScreen,
    NewGameScreen,
    MatchDetailsScreen,
    CalculationScreen,
    SummaryScreen,
    MatchesHistoryScreen,
    StatsScreen,
    AboutScreen,
    ScienceSimulatorScreen,
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            SevenWondersTheme {
                SevenWondersNavHost(
                    windowWidthSizeClass = windowSizeClass.widthSizeClass,
                )
            }
        }
    }
}