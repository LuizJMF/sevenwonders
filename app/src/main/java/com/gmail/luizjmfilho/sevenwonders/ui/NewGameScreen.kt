package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

const val choosePlayerTrailingIconTestTag: String = "botão escolher jogador"
const val newGameTextFieldTestTag: String = "new game textField"
const val newGameScreenTestTag: String = "NewGame Screen"

@Composable
fun NewGameScreenPrimaria(
    onBackClick: () -> Unit,
    onNextClick: (List<Int>) -> Unit,
    onChoosePlayerClick: (List<Int>) -> Unit,
    selectedIdFromPlayerListScreen: Int?,
    modifier: Modifier = Modifier,
    viewModel: NewGameViewModel = hiltViewModel()
) {
    WithLifecycleOwner(viewModel)


    LaunchedEffect(selectedIdFromPlayerListScreen) {
        if (selectedIdFromPlayerListScreen != null) {
            viewModel.setPlayerNames(selectedIdFromPlayerListScreen)
        }
    }


    val uiState by viewModel.uiState.collectAsState()
    NewGameScreenSecundaria(
        onBackClick = onBackClick,
        onNextClick = {
            onNextClick(viewModel.getPlayerIds())
        },
        onChoosePlayerClick = { playerIndexBeingSelected ->
            onChoosePlayerClick(
                viewModel.saveIndexAndGetPlayerIds(playerIndexBeingSelected)
            )
        },
        onAddPlayerTextButtonClick = viewModel::newGameAddPlayer,
        onRemovePlayerTextButtonClick = viewModel::newGameRemovePlayer,
        uiState = uiState,
        modifier = modifier,
    )
}

@Composable
fun NewGameScreenSecundaria(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    onChoosePlayerClick: (Int) -> Unit,
    onAddPlayerTextButtonClick: () -> Unit,
    onRemovePlayerTextButtonClick: () -> Unit,
    uiState: NewGameUiState,
    modifier: Modifier = Modifier,
) {
    Scaffold (
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(id = R.string.new_game_top_bar)
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
                painter = painterResource(id = R.drawable.background_image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Box (
                modifier = Modifier
                    .padding(10.dp)
                    .border(1.dp, Color.Gray)
                    .fillMaxSize()
            ){
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(
                        text = stringResource(R.string.new_game_screen_subtitle),
                        color = MaterialTheme.colorScheme.primary
                    )
                    NewGameTextFieldGroup(
                        playerNames = uiState.playerNames,
                        activePlayersNumber = uiState.activePlayersNumber,
                        onChoosePlayerClick = { playerIndexBeingSelected ->
                            onChoosePlayerClick(playerIndexBeingSelected)
                        },
                    )
                    Row {
                        AnimatedVisibility(
                            visible = uiState.activePlayersNumber < ActivePlayersNumber.Seven
                        ) {
                            TextButton(
                                onClick = onAddPlayerTextButtonClick,
                                enabled = uiState.isAdvanceAndAddPlayerButtonsEnable
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.PersonAdd,
                                        contentDescription = null
                                    )
                                    Text(text = stringResource(R.string.new_game_add_player_button))
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        AnimatedVisibility(
                            visible = uiState.activePlayersNumber > ActivePlayersNumber.Three
                        ) {
                            TextButton(
                                onClick = onRemovePlayerTextButtonClick
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.PersonRemove,
                                        contentDescription = null,
                                        tint = Color.Red
                                    )
                                    Text(text = stringResource(R.string.new_game_remove_player_button), color = Color.Red)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Row {
                        Spacer(Modifier.weight(1f))
                        TextButton(
                            onClick = onNextClick,
                            enabled = uiState.isAdvanceAndAddPlayerButtonsEnable
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ){
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
fun NewGameTextFieldGroup(
    playerNames: List<String>,
    activePlayersNumber: ActivePlayersNumber,
    onChoosePlayerClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
    ) {
        for (i in 0 .. 6) {
            AnimatedVisibility(
                visible = (i < activePlayersNumber.numValue)
            ) {
                NewGameTextField(
                    value = playerNames[i],
                    playerNumber = i,
                    keyboardOptions = if (i < activePlayersNumber.numValue - 1) {
                        KeyboardOptions(imeAction = ImeAction.Next)
                    } else {
                        KeyboardOptions(imeAction = ImeAction.Done)
                    },
                    keyboardActions = if (i < activePlayersNumber.numValue - 1) {
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                    } else {
                        KeyboardActions(onDone = { focusManager.clearFocus() })
                    },
                    onTrailingIconClick = {
                        onChoosePlayerClick(i)
                    },
                )
            }
        }
    }
}

@Composable
fun NewGameTextField(
    value: String,
    playerNumber: Int,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    onTrailingIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val textColor = MaterialTheme.colorScheme.primary
    val generalColor = MaterialTheme.colorScheme.onBackground
    TextField(
        value = value,
        onValueChange = {},
        label = {
            Text(
                text = stringResource(
                    id = R.string.new_game_text_field_label,
                    playerNumber + 1
                )
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedLeadingIconColor = generalColor,
            unfocusedLeadingIconColor = generalColor,
            focusedLabelColor = generalColor,
            unfocusedLabelColor = generalColor,
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,

            ),
        readOnly = true,
        leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = null) },
        trailingIcon = {
            TextButton(
                onClick = onTrailingIconClick
            ) {
                Text(
                    text = stringResource(R.string.generic_choose).lowercase(),
                    modifier = Modifier
                        .testTag(choosePlayerTrailingIconTestTag)
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .testTag(newGameTextFieldTestTag),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}

@Preview
@Composable
fun NewGamePreview() {
    SevenWondersTheme {
        NewGameScreenSecundaria(
            onBackClick = {},
            onNextClick = {},
            onChoosePlayerClick = {},
            onAddPlayerTextButtonClick = {},
            onRemovePlayerTextButtonClick = {},
            uiState = NewGameUiState()
        )
    }
}