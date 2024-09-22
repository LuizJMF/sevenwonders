package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import com.gmail.luizjmfilho.sevenwonders.ui.theme.bodyLargeEmphasis
import com.gmail.luizjmfilho.sevenwonders.ui.theme.science

@Composable
fun ScienceSimulatorScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScienceSimulatorViewModel = hiltViewModel()
) {
    WithLifecycleOwner(viewModel)

    val uiState by viewModel.uiState.collectAsState()

    ScienceSimulatorScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onScienceCountChange = viewModel::onScienceCountChange,
        modifier = modifier,
    )
}

@Composable
fun ScienceSimulatorScreen(
    uiState: ScienceSimulatorUiState,
    onBackClick: () -> Unit,
    onScienceCountChange: (ScienceSymbol, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.simulator)
            )
        },
        modifier = modifier
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.calculation_science_grid_comand),
                    style = MaterialTheme.typography.bodyLargeEmphasis,
                    color = MaterialTheme.colorScheme.science,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )

                ScienceSymbolsTable(
                    compassCount = uiState.compassCount,
                    stoneCount = uiState.stoneCount,
                    gearCount = uiState.gearCount,
                    onScienceCountChange = onScienceCountChange,
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                )

                TotalScoreSection(
                    score = uiState.score.toString(),
                    modifier = Modifier
                        .padding(top = 32.dp),
                )
            }
        }
    }
}

@Composable
private fun ScienceSymbolsTable(
    compassCount: Int,
    stoneCount: Int,
    gearCount: Int,
    onScienceCountChange: (ScienceSymbol, Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            modifier = Modifier
                .width(IntrinsicSize.Max)
        ) {
            for (symbol in ScienceSymbol.entries) {
                ScienceIconCard(
                    scienceSymbol = symbol,
                    modifier = Modifier
                        .weight(1f),
                )
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp),
        ) {
            for (symbol in ScienceSymbol.entries) {
                val currentScore = when (symbol) {
                    ScienceSymbol.Compass -> compassCount
                    ScienceSymbol.Stone -> stoneCount
                    ScienceSymbol.Gear -> gearCount
                }

                NumberInputField(
                    number = currentScore,
                    textColor = MaterialTheme.colorScheme.science,
                    onNumberChange = { number -> onScienceCountChange(symbol, number) },
                    modifier = Modifier
                        .width(150.dp)
                        .fillMaxHeight()
                        .weight(1f),
                )
            }
        }
    }
}

@Composable
private fun TotalScoreSection(
    score: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.total_score),
            fontStyle = FontStyle.Italic,
            color = MaterialTheme.colorScheme.science,
            fontSize = 25.sp
        )

        Text(
            text = score,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.science,
            modifier = Modifier
                .padding(1.dp),
            fontSize = 90.sp
        )
    }
}

@Preview
@Composable
private fun ScienceSimulatorScreenPreview() {
    SevenWondersTheme {
        ScienceSimulatorScreen(
            uiState = ScienceSimulatorUiState(
                compassCount = 1,
                stoneCount = 2,
                gearCount = 1,
                score = 13
            ),
            onBackClick = {},
            onScienceCountChange = { _, _ -> },
        )
    }
}