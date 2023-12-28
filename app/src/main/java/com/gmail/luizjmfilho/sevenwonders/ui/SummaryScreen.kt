package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun SummaryScreenPrimaria(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    summaryViewModel: SummaryViewModel = hiltViewModel()
) {

    val summaryUiState by summaryViewModel.uiState.collectAsState()
    SummaryScreenSecundaria(
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        summaryUiState = summaryUiState,
        modifier = modifier,
    )
}

@Composable
fun SummaryScreenSecundaria(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    summaryUiState: SummaryUiState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.generic_podium)
            )
        },
//        modifier = modifier
//            .testTag(newGameScreenTestTag),
    ) { scaffoldPadding ->

        Box(
            modifier = modifier
                .padding(scaffoldPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fundo_principal_claro_desenho),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 15.dp),
                        contentAlignment = Alignment.Center,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.podio),
                            contentDescription = null,
                            modifier = Modifier
                                .width(110.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Column(
                            modifier = Modifier
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            for (i in 0..<summaryUiState.matchList.size) {
                                PlayerInfoRow(
                                    wonder = summaryUiState.matchList[i].wonder,
                                    wonderSide = summaryUiState.matchList[i].wonderSide,
                                    nickname = summaryUiState.matchList[i].nickname,
                                    score = summaryUiState.matchList[i].totalScore,
                                    playerPosition = "${i + 1}º"
                                )
                            }
                        }
                    }
                    Row {
                        Spacer(Modifier.weight(1f))
                        TextButton(
                            onClick = onNextClick,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(text = stringResource(R.string.summary_to_complete_match).uppercase())
                                Icon(
                                    imageVector = Icons.Filled.ArrowForward,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }
        }

        
    }
}

@Composable
fun PlayerInfoRow(
    wonder: Wonders,
    wonderSide: WonderSide,
    nickname: String,
    score: Int,
    playerPosition: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(10.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = playerPosition,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0B206B),
            )
        }
        PlayerSummaryCard(wonder = wonder, wonderSide = wonderSide)
        InfoColumn(nickname = nickname, wonderSide = wonderSide, score = score, wonder = wonder)

    }
}


@Composable
fun PlayerSummaryCard(
    wonder: Wonders,
    wonderSide: WonderSide,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .width(180.dp)
            .aspectRatio(2.037037f),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = identifyTheBackgroundImage(wonder = wonder, wonderSide = wonderSide),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun identifyTheBackgroundImage(wonder: Wonders, wonderSide: WonderSide): Painter {
    return when (wonder) {
        Wonders.ALEXANDRIA -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.alexandria_day)
                WonderSide.Night -> painterResource(id = R.drawable.alexandria_night)
            }
        }
        Wonders.BABYLON -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.babylon_day)
                WonderSide.Night -> painterResource(id = R.drawable.babylon_night)
            }
        }
        Wonders.EPHESOS -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.ephesos_day)
                WonderSide.Night -> painterResource(id = R.drawable.ephesos_night)
            }
        }
        Wonders.GIZAH -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.gizah_day)
                WonderSide.Night -> painterResource(id = R.drawable.gizah_night)
            }
        }
        Wonders.HALIKARNASSOS -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.halikarnassos_day)
                WonderSide.Night -> painterResource(id = R.drawable.halikarnassos_night)
            }
        }
        Wonders.OLYMPIA -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.olympia_day)
                WonderSide.Night -> painterResource(id = R.drawable.olympia_night)
            }
        }
        Wonders.RHODOS -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.rhodos_day)
                WonderSide.Night -> painterResource(id = R.drawable.rhodos_night)
            }
        }
    }
}

@Composable
fun InfoColumn(
    nickname: String,
    wonder: Wonders,
    wonderSide: WonderSide,
    score: Int,
    modifier: Modifier = Modifier,
) {
    val descriptionColor = MaterialTheme.colorScheme.tertiary
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Jogador: $nickname",
            color = MaterialTheme.colorScheme.secondary,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "Wonder: ${convertWonderToString(wonder = wonder)}",
            color = descriptionColor,
            fontStyle = FontStyle.Italic,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Text(
            text = "Lado: ${convertWonderSideToString(wonderSide = wonderSide)}",
            color = descriptionColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontStyle = FontStyle.Italic
        )
        Text(
            text = "Pontuação: ${score.toString()}",
            color = descriptionColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontStyle = FontStyle.Italic
        )
    }
}

@Preview
@Composable
fun InfoColumnPreview() {
    SevenWondersTheme {
        InfoColumn(nickname = "Luiz", wonderSide = WonderSide.Day, score = 59, wonder = Wonders.HALIKARNASSOS)
    }
}

@Preview
@Composable
fun PlayerSummaryCardPreview() {
    SevenWondersTheme {
        PlayerSummaryCard(
            wonder = Wonders.OLYMPIA,
            wonderSide = WonderSide.Day
        )
    }
}

@Preview
@Composable
fun SummaryScreenPreview() {
    SevenWondersTheme {
        SummaryScreenSecundaria(
            onBackClick = { /*TODO*/ },
            onNextClick = {},
            summaryUiState = SummaryUiState(
                matchList = listOf(
                    Match(
                        matchId = 1,
                        nickname = "Luiz",
                        wonder = Wonders.GIZAH,
                        wonderSide = WonderSide.Night,
                        totalScore = 62,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "oi",
                        position = 1
                    )
                )
            )
        )
    }
}

@Preview
@Composable
fun playerInfoRowPreview() {
    SevenWondersTheme {
        PlayerInfoRow(
            wonderSide = WonderSide.Day,
            wonder = Wonders.HALIKARNASSOS,
            nickname = "Luiz",
            score = 59,
            playerPosition = "1º"
        )
    }
}