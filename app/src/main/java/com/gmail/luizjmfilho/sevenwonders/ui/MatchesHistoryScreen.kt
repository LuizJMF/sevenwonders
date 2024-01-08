package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun MatchesHistoryPrimaria(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    matchesHistoryViewModel: MatchesHistoryViewModel = hiltViewModel()
) {
    val matchesHistoryUiState by matchesHistoryViewModel.uiState.collectAsState()
    MatchesHistorySecundaria(
        onBackClick = onBackClick,
        matchesHistoryUiState = matchesHistoryUiState,
        onDeleteMatch = matchesHistoryViewModel::onDeleteMatchWhoseIdIs,
        modifier = modifier
    )
}

@Composable
fun MatchesHistorySecundaria(
    onBackClick: () -> Unit,
    matchesHistoryUiState: MatchesHistoryUiState,
    onDeleteMatch: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold (
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.matches_history_screen_title)
            )
        },
//        modifier = modifier
//            .testTag(newGameScreenTestTag),
    ) { scaffoldPadding ->

        var alertDialogShown by rememberSaveable { mutableStateOf(false) }
        var currentMatchIdBeingDelete by rememberSaveable { mutableIntStateOf(0) }

        Box(
            modifier = modifier
                .padding(scaffoldPadding)
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
            Box(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                if (matchesHistoryUiState.matchQuantity == 0) {
                    Text(
                        text = stringResource(R.string.matches_history_empty_message),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(10.dp),
                        fontStyle = FontStyle.Italic,
                        color = Color.Red
                    )
                } else {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        for (i in matchesHistoryUiState.matchQuantity downTo 1) {
                            val playerInfoListOfMatchNumberi = matchesHistoryUiState.playerInfoList.filter {
                                    it.matchId == i
                                }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .padding(
                                        top = if (i == matchesHistoryUiState.matchQuantity) 10.dp else 0.dp,
                                        bottom = if (i == 1) 10.dp else 0.dp
                                    )
                            ) {
                                MatchCard(
                                    playersMatchInfoList = playerInfoListOfMatchNumberi,
                                    onDeleteMatch = {
                                        alertDialogShown = false
                                        onDeleteMatch(currentMatchIdBeingDelete)
                                    },
                                    alertDialogShown = alertDialogShown,
                                    onCancelDialog = { alertDialogShown = false },
                                    modifier = Modifier.weight(1f),
                                    onDeleteClick = {
                                        currentMatchIdBeingDelete = i
                                        alertDialogShown = true
                                    }
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
fun MatchCard(
    playersMatchInfoList: List<Match>,
    onDeleteMatch: () -> Unit,
    onCancelDialog: () -> Unit,
    alertDialogShown: Boolean,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }
    var itemHeight by remember {
        mutableStateOf(0.dp)
    }
    val density = LocalDensity.current

    val interactionSource = remember {
        MutableInteractionSource()
    }

    if (playersMatchInfoList.isNotEmpty()) {
        Card(
            modifier = modifier
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                }
                .indication(interactionSource, LocalIndication.current)
                .pointerInput(true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        }
                    )
                },
            elevation = CardDefaults.cardElevation(5.dp),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
        ) {
            var expanded by rememberSaveable { mutableStateOf(false)}
            var visualizationMode by rememberSaveable { mutableStateOf(VisualizationMode.GeneralInfo)}
            Column(
                modifier = Modifier
                    .padding(
                        start = 5.dp,
                        top = 5.dp,
                        end = 5.dp,
                        bottom = if (expanded) 0.dp else 5.dp
                    )
                    .fillMaxWidth(),
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(3.dp),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = R.string.match_number, playersMatchInfoList[0].matchId),
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier
                                        .padding(end = 30.dp)
                                )
                                Text(
                                    text = playersMatchInfoList[0].dataAndTime,
                                    fontStyle = FontStyle.Italic,
                                    color = Color(0xFFA2A0A0),
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Person,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                )
                                Text(
                                    text = "${playersMatchInfoList.size}",
                                    modifier = Modifier
                                        .padding(end = 15.dp)
                                )
                                Text(
                                    text = stringResource(R.string.winner),
                                    color = MaterialTheme.colorScheme.secondary,
                                )
                                Text(
                                    text = playersMatchInfoList.minBy { it.position }.nickname,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis,
                                    modifier = Modifier
                                        .widthIn(max = 100.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(
                            onClick = {
                                expanded = !expanded
                            },
                        ) {
                            val degree by animateFloatAsState(
                                targetValue = if (expanded) 0f else 180f,
                            )
                            Icon(
                                imageVector = Icons.Filled.ExpandLess,
                                contentDescription = null,
                                modifier = Modifier
                                    .rotate(degree)
                            )
                        }
                    }
                }
                AnimatedVisibility(
                    visible = expanded
                ) {
                    Column(
                        modifier = Modifier
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                    ) {
                        Divider()
                        when (visualizationMode) {
                            VisualizationMode.GeneralInfo -> Row(
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                            ) {
                                Column(
                                    modifier = Modifier
                                        .width(IntrinsicSize.Max)
                                ) {
                                    Text(
                                        text = stringResource(R.string.position_acronym),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    for (i in 1..playersMatchInfoList.size) {
                                        Text(
                                            text = stringResource(R.string.position, i),
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            color = MaterialTheme.colorScheme.secondary
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .width(100.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.player),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    for (i in 1..playersMatchInfoList.size) {
                                        Text(
                                            text = playersMatchInfoList.filter { it.position == i }[0].nickname,
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .width(IntrinsicSize.Max)
                                ) {
                                    Text(
                                        text = stringResource(R.string.points_acronym),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    for (i in 1..playersMatchInfoList.size) {
                                        Text(
                                            text = playersMatchInfoList.filter { it.position == i }[0].totalScore.toString(),
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .width(90.dp)
                                ) {
                                    Text(
                                        text = stringResource(R.string.wonder),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    for (i in 1..playersMatchInfoList.size) {
                                        Text(
                                            text = convertWonderToString(wonder = playersMatchInfoList.filter { it.position == i }[0].wonder),
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                                Column(
                                    modifier = Modifier
                                        .width(IntrinsicSize.Max)
                                ) {
                                    Text(
                                        text = stringResource(R.string.side),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    for (i in 1..playersMatchInfoList.size) {
                                        Text(
                                            text = convertWonderSideToString(wonderSide = playersMatchInfoList.filter { it.position == i }[0].wonderSide),
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                }
                            }
                            VisualizationMode.DetailsInfo -> Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 3.dp, bottom = 3.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                SummaryGrid(playersMatchInfoList = playersMatchInfoList)
                            }
                        }
                        Divider()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            TextButton(
                                onClick = {
                                    visualizationMode = if (visualizationMode == VisualizationMode.GeneralInfo) {
                                        VisualizationMode.DetailsInfo
                                    } else {
                                        VisualizationMode.GeneralInfo
                                    }
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth()
                            ) {
                                Text(
                                    text = when(visualizationMode) {
                                        VisualizationMode.GeneralInfo -> stringResource(R.string.show_details)
                                        VisualizationMode.DetailsInfo -> stringResource(R.string.show_all_info)
                                    }
                                )
                            }
                        }
                    }
                }
                if (alertDialogShown) {
                    AlertDialog(
                        onDismissRequest = onCancelDialog,
                        confirmButton = {
                            TextButton(
                                onClick = onDeleteMatch,
                            ) {
                                Text(
                                    text = stringResource(R.string.matches_history_confirm_dialog_button),
                                    color = Color.Red
                                )
                            }
                        },
                        text = { Text(text = stringResource(R.string.matches_history_dialog_text)) },
                        dismissButton = {
                            TextButton(
                                onClick = onCancelDialog
                            ) {
                                Text(
                                    text = stringResource(R.string.generic_cancel_text),
                                    color = Color(0xFFA2A0A0),
                                    fontStyle = FontStyle.Italic
                                )
                            }
                        }
                    )
                }
            }
            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = { isContextMenuVisible = false },
                offset = pressOffset.copy(
                    y = pressOffset.y - itemHeight
                )
            ) {
                DropdownMenuItem(
                    text = { Text(text = stringResource(id = R.string.generic_delete)) },
                    onClick = {
                        onDeleteClick()
                        isContextMenuVisible = false
                    }
                )
            }
        }
    }
}

@Composable
fun SummaryGrid(
    playersMatchInfoList: List<Match>,
    modifier: Modifier = Modifier,
) {
    val spaceBetweenCards = 3.dp
    val cardsHeight = 30.dp
    Column(
        modifier = modifier
            .width(IntrinsicSize.Max)
            .horizontalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(1.dp),
            modifier = Modifier
                .height(IntrinsicSize.Max)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(spaceBetweenCards),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ) {
                for (player in playersMatchInfoList) {
                    NicknameCard(
                        nickname = player.nickname,
                        modifier = Modifier
                            .height(cardsHeight)
                    )
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(spaceBetweenCards),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ) {
                Card(
                    shape = RoundedCornerShape(8.dp, 0.dp, 0.dp, 0.dp),
                    colors = CardDefaults.cardColors(Color.White),
                    border = BorderStroke(1.dp, Color.Black),
                    modifier = Modifier
                        .height(40.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.general_socring_acronym).uppercase(),
                            modifier = Modifier
                                .padding(3.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                for (player in playersMatchInfoList) {
                    Card(
                        shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
                        colors = CardDefaults.cardColors(Color.White),
                        border = BorderStroke(1.dp, Color.Black),
                        modifier = Modifier
                            .height(cardsHeight)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = player.totalScore.toString(),
                                modifier = Modifier
                                    .padding(3.dp)
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            for (i in 1..7) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(spaceBetweenCards),
                    modifier = Modifier
                        .width(IntrinsicSize.Max)
                ) {
                    IconCategoryCard(
                        category = when (i) {
                            1 -> PointCategory.WonderBoard
                            2 -> PointCategory.Coin
                            3 -> PointCategory.War
                            4 -> PointCategory.BlueCard
                            5 -> PointCategory.YellowCard
                            6 -> PointCategory.GreenCard
                            else -> PointCategory.PurpleCard
                        },
                        lastCardInTheGrid = i == 7,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    for (player in playersMatchInfoList) {
                        Card(
                            shape = RoundedCornerShape(0.dp, 0.dp, 0.dp, 0.dp),
                            border = BorderStroke(1.dp, Color.Black),
                            modifier = Modifier
                                .height(cardsHeight)
                                .fillMaxWidth(),
                            colors = when (i) {
                                1 -> if(isSystemInDarkTheme()) CardDefaults.cardColors(Color(0xFF4D4D4D)) else CardDefaults.cardColors(Color(0xFFC4C4C4))
                                2 -> if(isSystemInDarkTheme()) CardDefaults.cardColors(Color(0xFFCA9D16)) else CardDefaults.cardColors(Color(0xFFF0D995))
                                3 -> if(isSystemInDarkTheme()) CardDefaults.cardColors(Color(0xFF960404)) else CardDefaults.cardColors(Color(0xFFFFA7A7))
                                4 -> if(isSystemInDarkTheme()) CardDefaults.cardColors(Color(0xFF0028B8)) else CardDefaults.cardColors(Color(0xFFB3BFFF))
                                5 -> if(isSystemInDarkTheme()) CardDefaults.cardColors(Color(0xFFA37E10)) else CardDefaults.cardColors(Color(0xFFFFEB3B))
                                6 -> if(isSystemInDarkTheme()) CardDefaults.cardColors(Color(0xFF17770C)) else CardDefaults.cardColors(Color(0xFFB8FFBA))
                                else -> if(isSystemInDarkTheme()) CardDefaults.cardColors(Color(0xFF670D72)) else CardDefaults.cardColors(Color(0xFFDCC9FF))
                            },
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = when (i) {
                                        1 -> playersMatchInfoList[playersMatchInfoList.indexOf(player)].wonderBoardScore.toString()

                                        2 -> playersMatchInfoList[playersMatchInfoList.indexOf(player)].coinScore.toString()

                                        3 -> playersMatchInfoList[playersMatchInfoList.indexOf(player)].warScore.toString()

                                        4 -> playersMatchInfoList[playersMatchInfoList.indexOf(player)].blueCardScore.toString()

                                        5 -> playersMatchInfoList[playersMatchInfoList.indexOf(player)].yellowCardScore.toString()

                                        6 -> playersMatchInfoList[playersMatchInfoList.indexOf(player)].greenCardScore.toString()

                                        else -> playersMatchInfoList[playersMatchInfoList.indexOf(player)].purpleCardScore.toString()
                                    },
                                    modifier = Modifier
                                        .padding(1.dp),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MatchCardPreview() {
    SevenWondersTheme {
        MatchCard(
            playersMatchInfoList = listOf(
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
                Match(
                    matchId = 1,
                    nickname = "Anninha",
                    wonder = Wonders.EPHESOS,
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
                    dataAndTime = "23/12/23 - 12:40",
                    position = 1
                ),
                Match(
                    matchId = 1,
                    nickname = "Deivinho",
                    wonder = Wonders.HALIKARNASSOS,
                    wonderSide = WonderSide.Day,
                    totalScore = 41,
                    wonderBoardScore = 10,
                    coinScore = 3,
                    warScore = 10,
                    blueCardScore = 10,
                    yellowCardScore = 10,
                    greenCardScore = 2,
                    purpleCardScore = 2,
                    coinQuantity = 5,
                    dataAndTime = "23/12/23 - 12:40",
                    position = 4
                ),
                Match(
                    matchId = 1,
                    nickname = "Gian",
                    wonder = Wonders.GIZAH,
                    wonderSide = WonderSide.Night,
                    totalScore = 49,
                    wonderBoardScore = 10,
                    coinScore = 3,
                    warScore = 10,
                    blueCardScore = 10,
                    yellowCardScore = 10,
                    greenCardScore = 2,
                    purpleCardScore = 2,
                    coinQuantity = 5,
                    dataAndTime = "23/12/23 - 12:40",
                    position = 3
                ),
            ),
            onDeleteMatch = {},
            onCancelDialog = {},
            alertDialogShown = false,
            onDeleteClick = {}
        )
    }
}

@Preview
@Composable
fun MatchesHistorySecundariaPreview() {
    SevenWondersTheme {
        MatchesHistorySecundaria(
            onBackClick = { /*TODO*/ },
            matchesHistoryUiState = MatchesHistoryUiState(
                matchQuantity = 1,
                playerInfoList = listOf(
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
                    Match(
                        matchId = 1,
                        nickname = "Eu Sou O Foda Para Ganhar Tudo",
                        wonder = Wonders.EPHESOS,
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
                        dataAndTime = "23/12/23 - 12:40",
                        position = 1
                    ),
                    Match(
                        matchId = 1,
                        nickname = "Deivinho",
                        wonder = Wonders.HALIKARNASSOS,
                        wonderSide = WonderSide.Day,
                        totalScore = 41,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 4
                    ),
                    Match(
                        matchId = 1,
                        nickname = "Gian",
                        wonder = Wonders.GIZAH,
                        wonderSide = WonderSide.Night,
                        totalScore = 49,
                        wonderBoardScore = 10,
                        coinScore = 3,
                        warScore = 10,
                        blueCardScore = 10,
                        yellowCardScore = 10,
                        greenCardScore = 2,
                        purpleCardScore = 2,
                        coinQuantity = 5,
                        dataAndTime = "23/12/23 - 12:40",
                        position = 3
                    )
                )
            ),
            onDeleteMatch = {}
        )
    }
}