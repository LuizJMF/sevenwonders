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
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.model.Person
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

const val choosePlayerTrailingIconTestTag: String = "botão escolher jogador"
const val newGameTextFieldTestTag: String = "new game textField"
const val newGameScreenTestTag: String = "NewGame Screen"

@Composable
fun NewGameScreenPrimaria(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    newGameViewModel: NewGameViewModel = viewModel(
        factory = NewGameViewModel.Factory
    ),
) {
    val newGameUiState by newGameViewModel.uiState.collectAsState()
    NewGameScreenSecundaria(
        onBackClick = onBackClick,
        onAdvanceClick = onNextClick,
        onPlayerChange = newGameViewModel::updatePlayer,
        onChoosePlayerClick = newGameViewModel::updateAvailablePlayersList,
        onAddPlayerTextButtonClick = newGameViewModel::newGameAddPlayer,
        onRemovePlayerTextButtonClick = newGameViewModel::newGameRemovePlayer,
        newGameUiState = newGameUiState,
        modifier = modifier,
    )
}

@Composable
fun NewGameScreenSecundaria(
    onBackClick: () -> Unit,
    onAdvanceClick: () -> Unit,
    onPlayerChange: (Int, String) -> Unit,
    onChoosePlayerClick: () -> Unit,
    onAddPlayerTextButtonClick: () -> Unit,
    onRemovePlayerTextButtonClick: () -> Unit,
    newGameUiState: NewGameUiState,
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
                painter = painterResource(id = R.drawable.fundoe),
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
                    Text(text = "Insira os jogadores pelo nome:")
                    NewGameTextFieldGroup(
                        activePlayersList = newGameUiState.activePlayersList,
                        activePlayersNumber = newGameUiState.activePlayersNumber,
                        availablePlayersList = newGameUiState.availablePlayersList,
                        onPlayerChange = onPlayerChange,
                        onChoosePlayerClick = onChoosePlayerClick,
                    )
                    Row {
                        AnimatedVisibility(
                            visible = newGameUiState.activePlayersNumber < ActivePlayersNumber.Seven
                        ) {
                            TextButton(
                                onClick = onAddPlayerTextButtonClick,
                                enabled = newGameUiState.isAdvanceAndAddPlayerButtonsEnable
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
                            visible = newGameUiState.activePlayersNumber > ActivePlayersNumber.Three
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
                            onClick = onAdvanceClick,
                            enabled = newGameUiState.isAdvanceAndAddPlayerButtonsEnable
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ){
                                Text(text = stringResource(R.string.new_game_advance_button))
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
    activePlayersList: List<String>,
    activePlayersNumber: ActivePlayersNumber,
    availablePlayersList: List<Person>,
    onChoosePlayerClick: () -> Unit,
    onPlayerChange: (Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
    ) {
        var playerIndexBeingSelected: Int? by rememberSaveable { mutableStateOf(null) }
        for (i in 0 .. 6) {
            AnimatedVisibility(
                visible = (i < activePlayersNumber.numValue)
            ) {
                NewGameTextField(
                    value = activePlayersList[i],
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
                        playerIndexBeingSelected = i
                        onChoosePlayerClick()
                    },
                )
            }
        }
        if (playerIndexBeingSelected != null) {
            PlayersListDialog(
                list = availablePlayersList,
                onDismissRequest = { playerIndexBeingSelected = null },
                onConfirmClick = { selectedPersonNickname ->
                    onPlayerChange(playerIndexBeingSelected!!, selectedPersonNickname)
                    playerIndexBeingSelected = null
                }
            )
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
    val textColor = Color.Blue
    val generalColor = Color.Black
    TextField(
        value = value,
        onValueChange = {},
        label = { Text(text = stringResource(id = R.string.new_game_text_field_label, playerNumber + 1 )) },
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

@Composable
fun PlayersListDialog(
    list: List<Person>,
    onDismissRequest: () -> Unit,
    onConfirmClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var selectedPerson: Person? by remember { mutableStateOf(null) }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.alert_dialog_new_game_player_selection_title)) },
        text = {
            if (list.isEmpty()) {
                Text(
                    text = stringResource(R.string.alert_dialog_new_game_text_when_list_is_empty),
                    textAlign = TextAlign.Justify
                )
            } else {
                Column {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                    ) {
                        list.forEach { person ->
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .selectable(
                                        selected = (person == selectedPerson),
                                        onClick = {
                                            selectedPerson = person
                                        }
                                    )
                                    .padding(horizontal = 16.dp)
                            ) {
                                RadioButton(
                                    selected = (person == selectedPerson),
                                    onClick = {
                                        selectedPerson = person
                                    }
                                )
                                PlayersListNameAndNicknameItem(
                                    name = person.name,
                                    nickname = person.nickname,
                                )
                            }
                        }
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
                onClick = { onConfirmClick(selectedPerson!!.nickname) },
                enabled = selectedPerson != null
            ) {
                Text(text = stringResource(R.string.generic_confirm_text))
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun NewGamePreview() {
    SevenWondersTheme {
        NewGameScreenSecundaria(
            onBackClick = {},
            onAdvanceClick = {},
            onPlayerChange = {_ , _ -> },
            onChoosePlayerClick = {},
            onAddPlayerTextButtonClick = {},
            onRemovePlayerTextButtonClick = {},
            newGameUiState = NewGameUiState()
        )
    }
}

@Preview
@Composable
fun AlertDialogPreview() {
    PlayersListDialog(
        list = listOf(
            /*Pessoa("Luiz","Zinho"),
            Pessoa("Deives", "Deivinho"),
            Pessoa("Anna Luisa", "Anninha")*/
        ),
        onDismissRequest = {},
        onConfirmClick = {}
    )
}