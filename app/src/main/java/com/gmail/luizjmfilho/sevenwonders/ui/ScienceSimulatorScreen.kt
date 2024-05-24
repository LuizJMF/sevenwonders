package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@Composable
fun ScienceSimulatorScreenPrimaria(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    scienceSimulatorViewModel: ScienceSimulatorViewModel = hiltViewModel()
) {
    WithLifecycleOwner(scienceSimulatorViewModel)

    val scienceSimulatorUiState by scienceSimulatorViewModel.uiState.collectAsState()

    ScienceSimulatorScreenSecundaria(
        onBackClick = onBackClick,
        scienceSimulatorUiState = scienceSimulatorUiState,
        onQuantityChange = scienceSimulatorViewModel::onQuantityChange,
        modifier = modifier,
    )
}

@Composable
fun ScienceSimulatorScreenSecundaria(
    onBackClick: () -> Unit,
    onQuantityChange: (ScienceSymbol, Int) -> Unit,
    scienceSimulatorUiState: ScienceSimulatorUiState,
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
    ) { scaffoldPaddings ->
        Box(
            modifier = Modifier
                .padding(scaffoldPaddings)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = if (isSystemInDarkTheme()) {
                    painterResource(id = R.drawable.fundo_desenho_dark)
                }  else {
                    painterResource(id = R.drawable.fundo_principal_claro_desenho)
                },
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Text(
                    text = stringResource(R.string.calculation_science_grid_comand),
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF1E9923),
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                )
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier
                            .width(IntrinsicSize.Max)
                    ) {
                        ScienceIconCard(
                            scienceSymbol = ScienceSymbol.Compass,
                            modifier = Modifier
                                .weight(1f)
                        )
                        ScienceIconCard(
                            scienceSymbol = ScienceSymbol.Stone,
                            modifier = Modifier
                                .weight(1f)
                        )
                        ScienceIconCard(
                            scienceSymbol = ScienceSymbol.Gear,
                            modifier = Modifier
                                .weight(1f)
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(3.dp),
                    ) {
                        for (i in 0..2) {
                            val symbol = when (i) {
                                0 -> ScienceSymbol.Compass
                                1 -> ScienceSymbol.Stone
                                else -> ScienceSymbol.Gear
                            }

                            val currentScore = when (symbol) {
                                ScienceSymbol.Compass -> scienceSimulatorUiState.compassQuantity
                                ScienceSymbol.Stone -> scienceSimulatorUiState.stoneQuantity
                                ScienceSymbol.Gear -> scienceSimulatorUiState.gearQuantity
                            }

                            NumberInputField(
                                number = currentScore,
                                textColor = Color(0xFF1E9923),
                                onNumberChange = { number -> onQuantityChange(symbol, number) },
                                modifier = Modifier
                                    .width(150.dp)
                                    .fillMaxHeight()
                                    .weight(1f),
                            )
                        }
                    }
                }
                Text(
                    text = "total de pontos:",
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF1E9923),
                    modifier = Modifier
                        .padding(top = 32.dp),
                    fontSize = 25.sp
                )
                Text(
                    text = scienceSimulatorUiState.totalScore.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E9923),
                    modifier = Modifier
                        .padding(1.dp),
                    fontSize = 90.sp
                )
            }
        }
    }
}

@Preview
@Composable
private fun ScienceSimulatorScreenPreview() {
    SevenWondersTheme {
        ScienceSimulatorScreenSecundaria(
            onBackClick = {},
            scienceSimulatorUiState = ScienceSimulatorUiState(
                compassQuantity = 1,
                stoneQuantity = 2,
                gearQuantity = 1,
                totalScore = 13
            ),
            onQuantityChange = { _, _ -> },
        )
    }
}