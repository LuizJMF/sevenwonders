package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonAddAlt1
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun PlayersListScreenPrimaria(
    onSelectPlayer: (List<String>) -> Unit,
    modifier: Modifier = Modifier,
    playersListViewModel: PlayersListViewModel = hiltViewModel()
) {
    WithLifecycleOwner(playersListViewModel)

    val playersListUiState by playersListViewModel.uiState.collectAsState()

    PlayersListScreenSecundaria(
        playersListUiState = playersListUiState,
        onAddPlayer = playersListViewModel::onAddPlayer,
        onTypeNewPlayer = playersListViewModel::onTypeNewPlayer,
        onSelectPlayer = { playerName ->
            val index = playersListViewModel.getPlayerIndexBeingSelected().toInt()
            val activePlayersList = playersListViewModel.getActivePlayersList().toMutableList()
            activePlayersList[index] = playerName
            onSelectPlayer(activePlayersList)
        },
        onFloatingButtonClick = playersListViewModel::onFloatingButtonClick,
        onDismissDialog = playersListViewModel::onDismissDialog,
        onDeletePlayer = playersListViewModel::onDeletePlayer,
        modifier = modifier,
    )
}

@Composable
fun PlayersListScreenSecundaria(
    playersListUiState: PlayersListUiState,
    onAddPlayer: () -> Unit,
    onTypeNewPlayer: (String) -> Unit,
    onSelectPlayer: (String) -> Unit,
    onDeletePlayer: (String) -> Unit,
    onFloatingButtonClick: () -> Unit,
    onDismissDialog: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onFloatingButtonClick,
                containerColor = Color(0xFF021CC5),
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Filled.PersonAddAlt1,
                    contentDescription = null
                )
            }
        },
    ) { scaffoldPaddings ->
        Box(
            modifier = Modifier
                .padding(scaffoldPaddings)
                .fillMaxSize()
        ) {
            if (playersListUiState.playersList.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.alert_dialog_new_game_text_when_list_is_empty),
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentWidth()
                        .wrapContentHeight()
                        .padding(20.dp),
                    textAlign = TextAlign.Justify,
                    color = Color(0xFF585858),
                )
            } else {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    for (player in playersListUiState.playersList) {
                        var pressOffset by remember { mutableStateOf(DpOffset.Zero) }
                        var itemHeight by remember { mutableStateOf(0.dp) }
                        val density = LocalDensity.current
                        val interactionSource = remember { MutableInteractionSource() }
                        var isDropMenuExpanded by rememberSaveable { mutableStateOf(false) }
                        Text(
                            text = player,
                            modifier = Modifier
                                .height(48.dp)
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .indication(interactionSource, LocalIndication.current)
                                .pointerInput(playersListUiState.playersList) {
                                    detectTapGestures(
                                        onLongPress = {
                                            isDropMenuExpanded = true
                                            pressOffset = DpOffset(
                                                it.x.toDp(),
                                                it.y.toDp() - (48.dp * (playersListUiState.playersList.indices.max() - playersListUiState.playersList.indexOf(
                                                    player
                                                )))
                                            )
                                        },
                                        onTap = {
                                            onSelectPlayer(player)
                                        },
                                        onPress = {
                                            val press = PressInteraction.Press(it)
                                            interactionSource.emit(press)
                                            tryAwaitRelease()
                                            interactionSource.emit(PressInteraction.Release(press))
                                        }
                                    )
                                }
                                .onSizeChanged {
                                    itemHeight = with(density) { it.height.toDp() }
                                }
                                .wrapContentHeight(),
                            color = Color(0xFF052497),
                        )
                        DropdownMenu(
                            expanded = isDropMenuExpanded,
                            onDismissRequest = { isDropMenuExpanded = false },
                            offset = pressOffset.copy(
                                y = pressOffset.y - itemHeight
                            ),

                        ) {
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(
                                            5.dp
                                        )
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.DeleteForever,
                                            contentDescription = null,
                                            tint = Color(0xFFBB0303),
                                        )
                                        Text(
                                            text = stringResource(id = R.string.generic_delete),
                                            color = Color(0xFFBB0303),
                                        )
                                    }
                                },
                                onClick = {
                                    isDropMenuExpanded = false
                                    onDeletePlayer(player)
                                },
                            )
                        }
                        Divider()
                    }
                }
            }
            if (playersListUiState.isDialogShown) {
                AddPlayerDialog(
                    onDismissRequest = onDismissDialog,
                    value = playersListUiState.playerBeingAdded,
                    onValueChange = {
                        onTypeNewPlayer(it)
                    },
                    onAddPlayer = {
                        onAddPlayer()
                    },
                    isError = (playersListUiState.nameError != null),
                    supportingText = {
                        when (playersListUiState.nameError) {
                            NameOrNicknameError.Empty -> Text(text = stringResource(R.string.empty_name))
                            NameOrNicknameError.Exists -> Text(text = stringResource(R.string.name_exists_erros_message))
                            else -> {}
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SevenWondersAppBar(
    onBackClick: (() -> Unit)?,
    title: String,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ){
        TopAppBar(
            title = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon = {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigation_back_content_description)
                        )
                    }
                }
            }
        )
        Divider(
            thickness = 2.dp,
            color = Color.Black
        )
    }
}

@Composable
fun AddPlayerDialog(
    value: String,
    onValueChange: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onAddPlayer: () -> Unit,
    isError: Boolean,
    supportingText: @Composable () -> Unit,
    modifier: Modifier = Modifier,
) {
    val labelAndIconSchoolTextFieldColor = Color(0xFF021CC5)
    val containerTextFieldColor = Color(0xFF7DDAFF)

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(text = stringResource(R.string.add_player_button))},
        text = {
            Column(
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = null,
                            tint = Color(0xFF021CC5)
                        )
                    },
                    label = { Text(text = stringResource(R.string.new_player))},
                    colors = TextFieldDefaults.colors(
                        unfocusedLabelColor = labelAndIconSchoolTextFieldColor,
                        focusedLabelColor = labelAndIconSchoolTextFieldColor,
                        focusedContainerColor = containerTextFieldColor,
                        unfocusedContainerColor = containerTextFieldColor,
                        unfocusedTrailingIconColor = labelAndIconSchoolTextFieldColor,
                        focusedTrailingIconColor = labelAndIconSchoolTextFieldColor,
                        unfocusedIndicatorColor = labelAndIconSchoolTextFieldColor,
                        focusedIndicatorColor = labelAndIconSchoolTextFieldColor,
                        focusedTextColor = labelAndIconSchoolTextFieldColor,
                        unfocusedTextColor = labelAndIconSchoolTextFieldColor,
                    ),
                    isError = isError,
                    supportingText = supportingText
                )
                Text(
                    text = "${value.length}/10",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(Alignment.End)
                )

            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissRequest
            ) {
                Text(
                    text = stringResource(R.string.generic_cancel_text),
                    color = Color.Red
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = onAddPlayer
            ) {
                Text(
                    text = stringResource(R.string.new_game_add_player_button),
                    color = Color(0xFF021CC5)
                )
            }
        },
        modifier = modifier,
    )
}

@Preview
@Composable
fun PlayersListScreenPreview() {
    SevenWondersTheme {
        PlayersListScreenSecundaria(
            playersListUiState = PlayersListUiState(
                playersList = listOf("Zinho", "Gian", "Luiz (pai)", "Minesa"),
                playerBeingAdded = "Bonitão"
            ),
            onAddPlayer = {},
            onTypeNewPlayer = {},
            onSelectPlayer = {},
            onDismissDialog = {},
            onFloatingButtonClick = {},
            onDeletePlayer = {}
        )
    }
}


