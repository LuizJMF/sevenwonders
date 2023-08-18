package com.gmail.luizjmfilho.sevenwonders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme


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
                SevenWondersNavHost()
            }
        }
    }
}