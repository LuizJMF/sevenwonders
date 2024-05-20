package com.gmail.luizjmfilho.sevenwonders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import com.google.android.play.core.review.ReviewManagerFactory
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFeedbackDialog()
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SevenWondersTheme {
                SevenWondersNavHost()
            }
        }
    }

    private fun showFeedbackDialog() {
        val reviewManager = ReviewManagerFactory.create(applicationContext)
        reviewManager.requestReviewFlow().addOnCompleteListener {
            if(it.isSuccessful) {
                reviewManager.launchReviewFlow(this, it.result)
            }
        }
    }
}