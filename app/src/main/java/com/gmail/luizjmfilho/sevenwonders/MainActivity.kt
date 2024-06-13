package com.gmail.luizjmfilho.sevenwonders

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.lifecycle.lifecycleScope
import com.gmail.luizjmfilho.sevenwonders.data.MatchCountRepository
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import com.google.android.play.core.review.ReviewManagerFactory
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


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

    @Inject
    lateinit var matchCountRepository: MatchCountRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            if (matchCountRepository.getNumberOfMatches() >= 3) {
                showFeedbackDialog()
            }
        }
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