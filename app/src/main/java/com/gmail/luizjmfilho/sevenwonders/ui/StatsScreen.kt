package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun StatsScreenPrimaria(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    statsViewModel: StatsViewModel = hiltViewModel()
) {
    WithLifecycleOwner(statsViewModel)

    val statsUiState by statsViewModel.uiState.collectAsState()
    StatsScreenSecundaria(
        onBackClick = onBackClick,
        statsUiState = statsUiState,
        modifier = modifier,
    )
}

@Composable
fun StatsScreenSecundaria(
    onBackClick: () -> Unit,
    statsUiState: StatsUiState,
    modifier: Modifier = Modifier,
) {
    Scaffold (
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.stats_screen_title)
            )
        },
    ) { scaffoldPadding ->

        Box(
            modifier = modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
            contentAlignment = Center
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
            if (statsUiState.isLoading) {
                Column(
                    horizontalAlignment = CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Text(text = stringResource(R.string.loading))
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 10.dp, end = 10.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    if (statsUiState.isDatabaseEmpty) {
                        Text(
                            text = stringResource(R.string.stats_screen_empty_database),
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentWidth()
                                .padding(top = 10.dp),
                            color = Color.Red,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        BestOrWorstScore(
                            matchList = statsUiState.bestScoresList,
                            true,
                            modifier = Modifier.padding(top = 10.dp)
                        )
                        Divider()
                        BestOrWorstScore(matchList = statsUiState.worstScoresList, false)
                        Divider()
                        AverageWinnerScore(averageScore = statsUiState.averageWinnerScore)
                        Divider()
                        BestWonder(wonderList = statsUiState.bestWondersList)
                        Divider()
                        CardsRecord(
                            blueList = statsUiState.blueList.distinct(),
                            yellowList = statsUiState.yellowList.distinct(),
                            greenList = statsUiState.greenList.distinct(),
                            purpleList = statsUiState.purpleList.distinct(),
                        )
                        Divider()
                        MostChampion(
                            absoluteList = statsUiState.mostAbsoluteChampionList,
                            relativeList = statsUiState.mostRelativeChampionList,
                        )
                        Divider()
                        BestOrWorstScorePerPlayer(matchList = statsUiState.bestScoresPerPlayerList, true)
                        Divider()
                        BestOrWorstScorePerPlayer(matchList = statsUiState.worstScoresPerPlayerList, false)
                        Divider()
                        AverageScorePerPlayer(playerAndScoreList = statsUiState.averageScorePerPlayer)
                        Divider()
                        VictoriesAbsoluteOrRelativePerPlayer(playerAndVictoriesList = statsUiState.allAbsoluteVictoriesList, true)
                        Divider()
                        VictoriesAbsoluteOrRelativePerPlayer(
                            playerAndVictoriesList = statsUiState.allRelativeVictoriesList,
                            false,
                            modifier = Modifier
                                .padding(bottom = 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BestWonder(
    wonderList: List<ResultadoDaConsultaSQLBestWonder>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(R.string.maravilha_que_mais_venceu),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = CenterHorizontally
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                for (i in wonderList.indices) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        PlayerSummaryCard(
                            wonder = wonderList[i].wonder,
                            wonderSide = wonderList[i].wonderSide
                        )
                        Column {
                            Row(
                                modifier = Modifier
                                    .padding(start = 15.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = convertWonderToString(wonder = wonderList[i].wonder),
                                    modifier = Modifier
                                        .padding(end = 10.dp)
                                )
                                Icon(
                                    imageVector = if (wonderList[i].wonderSide == WonderSide.Day) Icons.Filled.WbSunny else Icons.Filled.Bedtime,
                                    contentDescription = null,
                                    tint = if (wonderList[i].wonderSide == WonderSide.Day) Color(
                                        0xFFFF8C00
                                    ) else Color.Blue,
                                )
                            }
                            Text(
                                text = pluralStringResource(R.plurals.num_victories, wonderList[i].times, wonderList[i].times),
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier
                                    .padding(start = 15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CardsRecord(
    blueList: List<Pair<String, Int>>,
    yellowList: List<Pair<String, Int>>,
    greenList: List<Pair<String, Int>>,
    purpleList: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    val numberWidth = 40.dp
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = stringResource(R.string.mais_pontos_em),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bluecard_icon),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
            )
            Row(
                modifier = Modifier
                    .width(numberWidth)
            ) {
                Text(
                    text = if (blueList.isEmpty()) "0" else blueList[0].second.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    fontSize = 30.sp,
                )
            }
            Text(
                text = if (blueList.isEmpty()) "" else blueList.joinToString(", ") { it.first },
                fontStyle = FontStyle.Italic
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.yellowcard_icon),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
            )
            Row(
                modifier = Modifier
                    .width(numberWidth)
            ) {
                Text(
                    text = if (yellowList.isEmpty()) "0" else yellowList[0].second.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    fontSize = 30.sp,
                )
            }
            Text(
                text = if (yellowList.isEmpty()) "" else yellowList.joinToString(", ") { it.first },
                fontStyle = FontStyle.Italic
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.greencard_icon),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
            )
            Row(
                modifier = Modifier
                    .width(numberWidth)
            ) {
                Text(
                    text = if (greenList.isEmpty()) "0" else greenList[0].second.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    fontSize = 30.sp,
                )
            }
            Text(
                text = if (greenList.isEmpty()) "" else greenList.joinToString(", ") { it.first },
                fontStyle = FontStyle.Italic
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.purplecard_icon),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
            )
            Row(
                modifier = Modifier
                    .width(numberWidth)
            ) {
                Text(
                    text = if (purpleList.isEmpty()) "0" else purpleList[0].second.toString(),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF000000),
                    fontSize = 30.sp,
                )
            }
            Text(
                text = if (purpleList.isEmpty()) "" else purpleList.joinToString(", ") { it.first },
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun VictoriesAbsoluteOrRelativePerPlayer(
    playerAndVictoriesList: List<Pair<String, Int>>,
    isAbsolute: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = if (isAbsolute) stringResource(R.string.number_of_absolute_victories) else stringResource(
                R.string.relative_ratio_victories
            ),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                for (i in playerAndVictoriesList.indices) {
                    Text(
                        text = stringResource(
                            R.string.just_two_dots,
                            playerAndVictoriesList[i].first
                        ),
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                }
            }
            Column {
                for (i in playerAndVictoriesList.indices) {
                    Text(
                        text = if (isAbsolute) playerAndVictoriesList[i].second.toString() else stringResource(
                            R.string.just_percentage_symbol, playerAndVictoriesList[i].second
                        ),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF30B612),
                    )
                }
            }
        }
    }
}

@Composable
fun MostChampion(
    absoluteList: List<Pair<String, Int>>,
    relativeList: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.maior_vencedor),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Text(
            text = stringResource(R.string.generic_absoluto).uppercase(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
                .padding(bottom = 15.dp)
        ) {
            Text(
                text = absoluteList.joinToString(", ") { it.first },
                modifier = Modifier
                    .padding(end = 5.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF30B612)
            )
            Text(
                text = pluralStringResource(R.plurals.vez, absoluteList[0].second, absoluteList[0].second),
                fontStyle = FontStyle.Italic
            )
        }
        Text(
            text = stringResource(R.string.generic_relative).uppercase(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(),
            color = Color.Gray,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            Text(
                text = relativeList.joinToString(", "){ it.first },
                modifier = Modifier
                    .padding(end = 5.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF30B612)
            )
            Text(
                text = if (relativeList.isEmpty()) "-" else stringResource(R.string.percentagem_das_vezes, relativeList[0].second),
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun AverageWinnerScore(
    averageScore: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.average_winner_score),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Text(
            text = averageScore.toString(),
            fontWeight = FontWeight.Bold,
            color = Color(0xFF072FF8),
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally)
        )
    }
}

@Composable
fun AverageScorePerPlayer(
    playerAndScoreList: List<Pair<String, Int>>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.average_score_per_player),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                for (i in playerAndScoreList.indices) {
                    Text(
                        text = stringResource(R.string.just_two_dots, playerAndScoreList[i].first),
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                }
            }
            Column {
                for (i in playerAndScoreList.indices) {
                    Text(
                        text = playerAndScoreList[i].second.toString(),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF072FF8),
                    )
                }
            }
        }
    }
}

@Composable
fun BestOrWorstScorePerPlayer(
    matchList: List<Match>,
    isBest: Boolean,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = if (isBest) stringResource(R.string.best_score_per_player) else stringResource(R.string.worst_score_per_player),
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(bottom = 5.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column {
                for (i in matchList.indices) {
                    Text(
                        text = stringResource(R.string.just_two_dots, matchList[i].nickname),
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                }
            }
            Column {
                for (i in matchList.indices) {
                    Text(
                        text = matchList[i].totalScore.toString(),
                        fontWeight = FontWeight.Bold,
                        color = if (isBest) Color(0xFF30B612) else Color(0xFFF10202),
                        modifier = Modifier
                            .padding(end = 10.dp)
                    )
                }
            }
            Column {
                for (i in matchList.indices) {
                    Text(
                        text = stringResource(
                            R.string.names_separated_by_hifen,
                            convertWonderToString(matchList[i].wonder),
                            convertWonderSideToString(matchList[i].wonderSide)
                        ),
                        fontStyle = FontStyle.Italic,
                        color = Color(0xFF706E6E),
                    )
                }
            }
        }
    }
}

@Composable
fun BestOrWorstScore(
    matchList: List<Match>,
    isBest: Boolean,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = if (isBest) stringResource(R.string.best_score) else stringResource(R.string.worst_score),
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (matchList.isEmpty()) "-" else matchList[0].totalScore.toString(),
            fontWeight = FontWeight.Bold,
            color = if (isBest) Color(0xFF30B612) else Color(0xFFF10202),
            fontSize = 30.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(CenterHorizontally)
        )
        for (i in matchList.indices) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(CenterHorizontally)
            ) {
                Text(
                    text = matchList[i].nickname,
                    fontStyle = FontStyle.Italic,
                )
                Text(
                    text = " (${convertWonderToString(matchList[i].wonder)} - ${
                        convertWonderSideToString(
                            matchList[i].wonderSide
                        )
                    })",
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF706E6E),
                )
            }
        }
    }
}

@Preview
@Composable
fun StatsScreenSecundariaPreview() {
    SevenWondersTheme {
        StatsScreenSecundaria(
            onBackClick = { },
            statsUiState = StatsUiState(
                bestScoresList = listOf(
                    Match(
                        matchId = 1,
                        nickname = "Luiz",
                        wonder = Wonders.OLYMPIA,
                        wonderSide = WonderSide.Day,
                        totalScore = 58,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 2
                    ),
                ),
                worstScoresList = listOf(
                    Match(
                        matchId = 1,
                        nickname = "Anninha",
                        wonder = Wonders.OLYMPIA,
                        wonderSide = WonderSide.Day,
                        totalScore = 21,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 2
                    ),
                    Match(
                        matchId = 1,
                        nickname = "Caio",
                        wonder = Wonders.EPHESOS,
                        wonderSide = WonderSide.Night,
                        totalScore = 21,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 1
                    ),
                ),
                bestScoresPerPlayerList = listOf(
                    Match(
                        matchId = 1,
                        nickname = "Anninha",
                        wonder = Wonders.OLYMPIA,
                        wonderSide = WonderSide.Day,
                        totalScore = 81,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 2
                    ),
                    Match(
                        matchId = 1,
                        nickname = "Caio",
                        wonder = Wonders.EPHESOS,
                        wonderSide = WonderSide.Night,
                        totalScore = 73,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 1
                    ),
                ),
                worstScoresPerPlayerList = listOf(
                    Match(
                        matchId = 1,
                        nickname = "Anninha",
                        wonder = Wonders.OLYMPIA,
                        wonderSide = WonderSide.Day,
                        totalScore = 21,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 2
                    ),
                    Match(
                        matchId = 1,
                        nickname = "Caio",
                        wonder = Wonders.EPHESOS,
                        wonderSide = WonderSide.Night,
                        totalScore = 23,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 1
                    ),
                ),
                averageWinnerScore = 58,
                averageScorePerPlayer = listOf(
                    Pair("Luiz", 45),
                    Pair("Deivinho", 43),
                    Pair("Gian", 39),
                    Pair("Anninha", 31),
                ),
                isDatabaseEmpty = false,
                isLoading = false,
                mostAbsoluteChampionList = listOf(
                    "Luiz" to 3,
                    "Anna" to 3
                )
            )
        )
    }
}