package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@VisibleForTesting
const val homeScreenTestTag: String = "Home Screen"

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onNewMatchClick: () -> Unit,
    onMatchesHistoryClick: () -> Unit,
    onStatsClick: () -> Unit,
    onAboutClick: () -> Unit,
    onScienceSimulatorClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WithLifecycleOwner(viewModel)

    HomeScreen(
        onNewMatchClick = onNewMatchClick,
        onMatchesHistoryClick = onMatchesHistoryClick,
        onStatsClick = onStatsClick,
        onAboutClick = onAboutClick,
        onScienceSimulatorClick = onScienceSimulatorClick,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    onNewMatchClick: () -> Unit,
    onMatchesHistoryClick: () -> Unit,
    onStatsClick: () -> Unit,
    onAboutClick: () -> Unit,
    onScienceSimulatorClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier
            .testTag(homeScreenTestTag)
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(id = R.drawable.home_screen_background),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = null,
                    modifier = Modifier
                        .height(150.dp)
                )

                Spacer(Modifier.weight(0.40f))

                HomeScreenButtons(
                    onNewMatchClick = onNewMatchClick,
                    onStatsClick = onStatsClick,
                    onMatchesHistoryClick = onMatchesHistoryClick,
                    onScienceSimulatorClick = onScienceSimulatorClick,
                    modifier = Modifier
                        .width(IntrinsicSize.Max),
                )

                Spacer(Modifier.weight(0.60f))

                AboutButton(
                    onClick = onAboutClick,
                    modifier = Modifier
                        .padding(bottom = 15.dp)
                        .align(Alignment.End),
                )
            }
        }
    }
}

@Composable
private fun HomeScreenButtons(
    onNewMatchClick: () -> Unit,
    onStatsClick: () -> Unit,
    onMatchesHistoryClick: () -> Unit,
    onScienceSimulatorClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        HomeScreenButton(
            onClick = onNewMatchClick,
            text = stringResource(R.string.new_match_button),
            modifier = Modifier
                .fillMaxWidth()
        )

        HomeScreenButton(
            onClick = onStatsClick,
            text = stringResource(R.string.stats_button),
            modifier = Modifier
                .fillMaxWidth()
        )

        HomeScreenButton(
            onClick = onMatchesHistoryClick,
            text = stringResource(R.string.match_history_button),
            modifier = Modifier
                .fillMaxWidth()
        )

        HomeScreenButton(
            onClick = onScienceSimulatorClick,
            text = stringResource(R.string.science_points_simulator),
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
private fun HomeScreenButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    Button(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(
            text = text,
        )
    }
}

@Composable
private fun AboutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = stringResource(R.string.about))

        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    SevenWondersTheme {
        HomeScreen(
            onNewMatchClick = {},
            onMatchesHistoryClick = {},
            onStatsClick = {},
            onAboutClick = {},
            onScienceSimulatorClick = {}
        )
    }
}