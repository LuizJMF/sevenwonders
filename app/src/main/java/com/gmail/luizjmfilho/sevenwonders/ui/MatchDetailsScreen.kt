package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowRightAlt
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.MoveDown
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.model.PlayerDetails
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

const val raffleRadioButtonTestTag: String = "sortear"
const val chooseRadioButtonTestTag: String = "escolher"
const val dayNightIconTestTag: String = "ícone de dia/noite"
const val chooseTextButtonTestTag: String = "botão escolher"
const val expandLessTestTag: String = "botão setinha pra baixo"
const val moveDownCardIconTestTag: String = "ícone de mover o card pra baixo"

@Composable
fun MatchDetailsScreenPrimaria(
    onBackClick: () -> Unit,
    onNextClick: (List<PlayerDetails>) -> Unit,
    modifier: Modifier = Modifier,
    matchDetailsViewModel: MatchDetailsViewModel = hiltViewModel()
) {
    val matchDetailsUiState by matchDetailsViewModel.uiState.collectAsState()
    MatchDetailsScreenSecundaria(
        onBackClick = onBackClick,
        onNextClick = onNextClick,
        onConfirmClick = matchDetailsViewModel::onConfirmMethod,
        modifier = modifier,
        onTrailingIconClick = matchDetailsViewModel::onWonderSideChange,
        onTextButtonClick = matchDetailsViewModel::onChooseWonderClick,
        matchDetailsUiState = matchDetailsUiState,
        onDialogConfirmClick = matchDetailsViewModel::onSelectWonderInDialog,
        onDeselectWonderClick = matchDetailsViewModel::onDeselectWonder,
        onMoveCardDown = matchDetailsViewModel::onMoveCardDown,
    )
}

@Composable
fun MatchDetailsScreenSecundaria(
    onBackClick: () -> Unit,
    onNextClick: (List<PlayerDetails>) -> Unit,
    onConfirmClick: (RaffleOrChoose, RaffleOrChoose) -> Unit,
    onTrailingIconClick: (Int) -> Unit,
    onTextButtonClick: () -> Unit,
    onDialogConfirmClick: (Wonders, Int) -> Unit,
    onDeselectWonderClick: (Int) -> Unit,
    onMoveCardDown: (Int) -> Unit,
    matchDetailsUiState: MatchDetailsUiState,
    modifier: Modifier = Modifier,
) {
    var positionMethod: RaffleOrChoose? by rememberSaveable { mutableStateOf(null) }
    var wonderMethod: RaffleOrChoose? by rememberSaveable { mutableStateOf(null) }
    var raffleAndChooseBoxesExpanded by rememberSaveable { mutableStateOf(true) }

    Scaffold (
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(id = R.string.match_details_top_bar)
            )
        },
        modifier = modifier
            .testTag(newGameScreenTestTag),
    ) { scaffoldPadding ->
        Box(
            modifier = modifier
                .padding(scaffoldPadding)
        ) {
            Image(
                painter = painterResource(id = R.drawable.fundoe),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .border(1.dp, Color.Gray)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = stringResource(R.string.match_details_choose_methods),
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                        )
                        IconButton(
                            onClick = {
                                raffleAndChooseBoxesExpanded = !raffleAndChooseBoxesExpanded
                                positionMethod = null
                                wonderMethod = null
                            },
                            modifier = Modifier
                                .testTag(expandLessTestTag)
                        ) {
                            val degree by animateFloatAsState(
                                targetValue = if (raffleAndChooseBoxesExpanded) 0f else 180f,
                            )
                            Icon(
                                imageVector = Icons.Filled.ExpandLess,
                                contentDescription = null,
                                modifier = Modifier
                                    .rotate(degree)
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = raffleAndChooseBoxesExpanded
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .height(IntrinsicSize.Min)
                                    .padding(bottom = 15.dp),
                            ) {
                                RaffleAndChooseBox(
                                    category = stringResource(R.string.generic_positions),
                                    modifier = Modifier
                                        .padding(end = 5.dp),
                                    onRadioButtonClick = {
                                        positionMethod = it
                                    }
                                )
                                Divider(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .width(1.dp)
                                )
                                RaffleAndChooseBox(
                                    category = stringResource(R.string.generic_wonders),
                                    modifier = Modifier
                                        .padding(start = 5.dp),
                                    onRadioButtonClick = {
                                        wonderMethod = it
                                    }
                                )
                            }
                            OutlinedButton(
                                onClick = {
                                    onConfirmClick(positionMethod!!, wonderMethod!!)
                                    raffleAndChooseBoxesExpanded = false
                                },
                                enabled = (raffleAndChooseBoxesExpanded && positionMethod != null && wonderMethod != null)
                            ) {
                                Text(text = stringResource(id = R.string.generic_confirm_text).uppercase())
                            }
                        }
                    }
                    AnimatedVisibility(
                        visible = (matchDetailsUiState.creationMethod != null)
                    ) {
                        MatchSetupBox(
                            matchPlayersDetails = matchDetailsUiState.matchPlayersDetails,
                            modifier = Modifier
                                .padding(top = 15.dp),
                            creationMethod = matchDetailsUiState.creationMethod!!,
                            onTrailingIconClick = onTrailingIconClick,
                            onTextButtonClick = onTextButtonClick,
                            onDialogConfirmClick = onDialogConfirmClick,
                            availableWondersList = matchDetailsUiState.availableWondersList.map{ wonder ->
                                convertWonderToString(wonder = wonder)
                            },
                            onDeselectWonder = onDeselectWonderClick,
                            onMoveCardDown = onMoveCardDown,
                        )
                    }
                    Spacer(Modifier.weight(1f))
                    Row {
                        Spacer(Modifier.weight(1f))
                        TextButton(
                            onClick = { onNextClick(matchDetailsUiState.matchPlayersDetails) },
                            enabled = (matchDetailsUiState.isAdvanceButtonEnabled)
                        ) {
                            Row(
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(text = stringResource(R.string.generic_advance_button).uppercase())
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
fun RaffleAndChooseBox(
    category: String,
    onRadioButtonClick: (RaffleOrChoose) -> Unit,
    modifier: Modifier = Modifier,
) {
    var raffleOrChoose: RaffleOrChoose? by rememberSaveable { mutableStateOf(null)}

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = category,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 14.dp)
        )
        Row(
            modifier = Modifier
                .selectable(
                    selected = (raffleOrChoose == RaffleOrChoose.Raffle),
                    onClick = {
                        raffleOrChoose = RaffleOrChoose.Raffle
                        onRadioButtonClick(raffleOrChoose!!)
                    }
                ),
            verticalAlignment = CenterVertically
        ) {
            RadioButton(
                selected = (raffleOrChoose == RaffleOrChoose.Raffle),
                onClick = {
                    raffleOrChoose = RaffleOrChoose.Raffle
                    onRadioButtonClick(raffleOrChoose!!)
                },
                modifier = Modifier
                    .testTag(raffleRadioButtonTestTag)
            )
            Text(
                text = stringResource(R.string.generic_raffle).uppercase(),
            )
        }
        Row(
            modifier = Modifier
                .selectable(
                    selected = (raffleOrChoose == RaffleOrChoose.Choose),
                    onClick = {
                        raffleOrChoose = RaffleOrChoose.Choose
                        onRadioButtonClick(raffleOrChoose!!)
                    }
                ),
            verticalAlignment = CenterVertically
        ) {
            RadioButton(
                selected = (raffleOrChoose == RaffleOrChoose.Choose),
                onClick = {
                    raffleOrChoose = RaffleOrChoose.Choose
                    onRadioButtonClick(raffleOrChoose!!)
                },
                modifier = Modifier
                    .testTag(chooseRadioButtonTestTag)
            )
            Text(
                text = stringResource(R.string.generic_choose).uppercase(),
            )
        }
    }
}

@Composable
fun PersonAndWonderCard(
    playerDetails: PlayerDetails,
    creationMethod: CreationMethod,
    onTrailingIconClick: () -> Unit,
    onTextButtonClick: () -> Unit,
    onMoveCardDown: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(Color.White)
    ) {
        Row(
            verticalAlignment = CenterVertically
        ) {
            when (creationMethod) {
                CreationMethod.AllChoose, CreationMethod.ChoosePositionRaffleWonder -> {
                    IconButton(
                        onClick = onMoveCardDown,
                        modifier = Modifier
                            .testTag(moveDownCardIconTestTag)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.MoveDown,
                            contentDescription = null,
                            modifier = Modifier
                                .padding(start = 14.dp),
                        )
                    }
                }
                else -> {}
            }
            Text(
                text = playerDetails.nickname,
                modifier = Modifier
                    .weight(0.4f),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = Icons.Filled.ArrowRightAlt,
                tint = Color.Red,
                contentDescription = null
            )
            if (playerDetails.wonder == null) {
                TextButton(
                    onClick = onTextButtonClick,
                    modifier = Modifier
                        .weight(0.6f)
                        .testTag(chooseTextButtonTestTag),
                ) {
                    Text(
                        text = stringResource(R.string.generic_choose).lowercase(),
                    )
                }
            } else {
                if (creationMethod == CreationMethod.AllChoose || creationMethod == CreationMethod.RafflePositionChooseWonder) {
                    TextButton(
                        onClick = onTextButtonClick,
                        modifier = Modifier
                            .weight(0.6f),
                    ) {
                        Text(
                            text = convertWonderToString(playerDetails.wonder),
                            modifier = Modifier
                                .weight(0.6f),
                            textAlign = TextAlign.Center,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } else {
                    Text(
                        text = convertWonderToString(playerDetails.wonder),
                        modifier = Modifier
                            .weight(0.6f),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            if (playerDetails.wonderSide != null) {
                IconButton(
                    onClick = onTrailingIconClick,
                    modifier = Modifier
                        .testTag(dayNightIconTestTag)
                ) {
                    Icon(
                        imageVector = when (playerDetails.wonderSide) {
                            WonderSide.Day -> Icons.Filled.WbSunny
                            WonderSide.Night -> Icons.Filled.Bedtime
                        },
                        tint = when (playerDetails.wonderSide) {
                            WonderSide.Day -> Color(0xFFFF8C00)
                            WonderSide.Night -> Color.Blue
                        },
                        contentDescription = null,
                    )
                }
            }
        }
    }
}

@Composable
fun convertWonderToString(wonder: Wonders?): String {
    val name = when (wonder) {
        Wonders.ALEXANDRIA -> stringResource(R.string.generic_wonder_alexandria)
        Wonders.BABYLON -> stringResource(R.string.generic_wonder_babylon)
        Wonders.EPHESOS -> stringResource(R.string.generic_wonder_ephesos)
        Wonders.GIZAH -> stringResource(R.string.generic_wonder_gizah)
        Wonders.HALIKARNASSOS -> stringResource(R.string.generic_wonder_halikarnassos)
        Wonders.OLYMPIA -> stringResource(R.string.generic_wonder_olympia)
        Wonders.RHODOS -> stringResource(R.string.generic_wonder_rhodos)
        null -> "" // esse caso nunca é pra ocorrer, em teoria
    }
    return name
}

@Composable
fun convertStringToWonder(wonderName: String?): Wonders {
    val name = when (wonderName) {
        stringResource(R.string.generic_wonder_alexandria) -> Wonders.ALEXANDRIA
        stringResource(R.string.generic_wonder_babylon) -> Wonders.BABYLON
        stringResource(R.string.generic_wonder_ephesos) -> Wonders.EPHESOS
        stringResource(R.string.generic_wonder_gizah) -> Wonders.GIZAH
        stringResource(R.string.generic_wonder_halikarnassos) -> Wonders.HALIKARNASSOS
        stringResource(R.string.generic_wonder_olympia) -> Wonders.OLYMPIA
        stringResource(R.string.generic_wonder_rhodos) -> Wonders.RHODOS
        else -> Wonders.RHODOS // esse caso não é pra acontecer nunca
    }
    return name
}

@Composable
fun MatchSetupBox(
    availableWondersList: List<String>,
    matchPlayersDetails: List<PlayerDetails>,
    creationMethod: CreationMethod,
    onTrailingIconClick: (Int) -> Unit,
    onTextButtonClick: () -> Unit,
    onDialogConfirmClick: (Wonders, Int) -> Unit,
    onDeselectWonder: (Int) -> Unit,
    onMoveCardDown: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    var wonderIndexBeingSelected: Int? by rememberSaveable { mutableStateOf(null) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .verticalScroll(rememberScrollState()),
    ) {
        for (player in matchPlayersDetails) {
            PersonAndWonderCard(
                playerDetails = player,
                creationMethod = creationMethod,
                onTrailingIconClick = { onTrailingIconClick(matchPlayersDetails.indexOf(player)) },
                onTextButtonClick = {
                    onTextButtonClick()
                    wonderIndexBeingSelected = matchPlayersDetails.indexOf(player)
                },
                onMoveCardDown = { onMoveCardDown(matchPlayersDetails.indexOf(player)) }
            )
        }
    }
    if (wonderIndexBeingSelected != null) {
        WondersListDialog(
            list = availableWondersList,
            onDismissRequest = { wonderIndexBeingSelected = null },
            onConfirmClick = { wonderName ->
                onDialogConfirmClick(wonderName, wonderIndexBeingSelected!!)
                wonderIndexBeingSelected = null
            },
            onDeselectWonder = {
                onDeselectWonder(wonderIndexBeingSelected!!)
                wonderIndexBeingSelected = null
            }
        )
    }
}

@Composable
fun WondersListDialog(
    list: List<String>,
    onDismissRequest: () -> Unit,
    onConfirmClick: (Wonders) -> Unit,
    onDeselectWonder: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedWonderName: String? by rememberSaveable { mutableStateOf(null) }
    val selectedWonder = convertStringToWonder(selectedWonderName)


    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.alert_dialog_match_details_wonder_selection_title))},
        text = {
            Column {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    list.forEach { wonder ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = (wonder == selectedWonderName),
                                    onClick = {
                                        selectedWonderName = wonder
                                    }
                                )
                                .padding(horizontal = 16.dp),
                            verticalAlignment = CenterVertically
                        ) {
                            RadioButton(
                                selected = (wonder == selectedWonderName),
                                onClick = {
                                    selectedWonderName = wonder
                                }
                            )
                            Text(
                                text = wonder,
                            )
                        }
                    }
                }
                Row {
                    IconButton(
                        onClick = onDeselectWonder
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                    TextButton(
                        onClick = onDeselectWonder
                    ) {
                        Text(
                            text = stringResource(R.string.match_details_deselect_wonder_dialog),
                            color = Color.Red
                        )
                    }
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(text = stringResource(R.string.generic_cancel_text))
            }
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirmClick(selectedWonder) },
                enabled = selectedWonderName != null
            ) {
                Text(text = stringResource(R.string.generic_confirm_text))
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun MatchSetupBoxPreview() {
    SevenWondersTheme {
        MatchSetupBox(
            creationMethod = CreationMethod.ChoosePositionRaffleWonder,
            matchPlayersDetails = listOf(
                PlayerDetails("Zinho", Wonders.BABYLON, wonderSide = WonderSide.Day),
                PlayerDetails("Anninha", Wonders.ALEXANDRIA, wonderSide = WonderSide.Night),
                PlayerDetails("Deivinho", Wonders.HALIKARNASSOS, wonderSide = WonderSide.Day),
                PlayerDetails("Luighi", Wonders.EPHESOS, wonderSide = WonderSide.Day),
                PlayerDetails("Iagê", Wonders.RHODOS, wonderSide = WonderSide.Night)
            ),
            onTrailingIconClick = {},
            onTextButtonClick = {},
            availableWondersList = listOf(),
            onDialogConfirmClick = {_, _ ->},
            onDeselectWonder = {},
            onMoveCardDown = {}
        )
    }
}

@Preview
@Composable
fun PersonAndWonderCardPreview() {
    SevenWondersTheme {
        PersonAndWonderCard(
            playerDetails = PlayerDetails("Zinho", Wonders.EPHESOS, WonderSide.Day),
            creationMethod = CreationMethod.AllChoose,
            onTrailingIconClick = {},
            onTextButtonClick = {},
            onMoveCardDown = {}
        )
    }
}

@Preview
@Composable
fun MatchDetailsScreenSecundariaPreview() {
    SevenWondersTheme {
        MatchDetailsScreenSecundaria(
            onBackClick = { },
            onNextClick = { },
            onConfirmClick = {_ , _ -> },
            matchDetailsUiState = MatchDetailsUiState(),
            onTrailingIconClick = {},
            onTextButtonClick = {},
            onDialogConfirmClick = {_, _ ->},
            onDeselectWonderClick = {},
            onMoveCardDown = {}
        )
    }
}

@Preview
@Composable
fun WondersListDialogPreview() {
    SevenWondersTheme {
        WondersListDialog(
            list = listOf("Alexandria","Babylon"),
            onDismissRequest = { /*TODO*/ },
            onConfirmClick = {},
            onDeselectWonder = {}
        )
    }
}