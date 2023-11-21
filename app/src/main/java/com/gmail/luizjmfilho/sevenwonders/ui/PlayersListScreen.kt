package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.model.Person
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

const val deleteIconTestTag: String = "ícone X de deletar"
const val nameTextFieldTestTag: String = "Name TextField"
const val nicknameTextFieldTestTag: String = "Nickname TextField"
const val eachPersonNameInTheListTestTag: String = "Person Name"
const val eachPersonNicknameInTheListTestTag: String = "Person Nickname"
const val playersListScreenTestTag: String = "PlayersList Screen"

@Composable
fun PlayersListScreenPrimaria(
    windowWidthSizeClass: WindowWidthSizeClass,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    playersListViewModel: PlayersListViewModel = hiltViewModel()
) {
    val playersListUiState by playersListViewModel.uiState.collectAsState()
    PlayersListScreenSecundaria(
        windowWidthSizeClass = windowWidthSizeClass,
        onBackClick = onBackClick,
        playersListUiState = playersListUiState,
        modifier = modifier,
        onNicknameChange = { playersListViewModel.updateNickname(it) },
        deletePlayer = { nome -> playersListViewModel.deletePlayer(nome) },
        cancelAddPlayer = { playersListViewModel.cancelAddPlayer()},
        onConfirmClicked = {playersListViewModel.onConfirmAddPlayerClick()}
    )
}

@Composable
fun PlayersListScreenSecundaria(
    windowWidthSizeClass: WindowWidthSizeClass,
    onBackClick: () -> Unit,
    playersListUiState: PlayersListUiState,
    onNicknameChange: (String) -> Unit,
    deletePlayer: (String) -> Unit,
    cancelAddPlayer: () -> Unit,
    onConfirmClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var addPlayerExpanded by rememberSaveable() { mutableStateOf(false) }
    var deletePlayerExpanded by rememberSaveable() { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(id = R.string.players_list_top_bar)
            )
        },
        modifier = modifier
            .testTag(playersListScreenTestTag),
    ) { scaffoldPadding ->
        if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
            PlayersListCompactContent(
                modifier = Modifier
                    .padding(scaffoldPadding),
                onAddJogadorButton = {
                    addPlayerExpanded = !addPlayerExpanded
                    cancelAddPlayer()
                },
                onDeletePlayerButton = {
                    if (playersListUiState.playersList.isEmpty()) {
                        deletePlayerExpanded = false
                    } else {
                        deletePlayerExpanded = !deletePlayerExpanded
                    }
                },
                onAddJogadorButtonEnabled = !addPlayerExpanded,
                onDeletePlayerButtonEnabled = !deletePlayerExpanded,
                addJogadorExpanded = addPlayerExpanded,
                deletePlayerExpanded = deletePlayerExpanded,
                onAddJogadorExpanded = { addPlayerExpanded = it },
                cancelAddPlayer = cancelAddPlayer,
                deletePlayer = deletePlayer,
                onNicknameChange = onNicknameChange,
                onConfirmClicked = onConfirmClicked,
                playersListUiState = playersListUiState,
            )
        } else {
            PlayersListMediumAndExpandedContent(
                modifier = Modifier
                    .padding(scaffoldPadding),
                onAddJogadorButton = {
                    addPlayerExpanded = !addPlayerExpanded
                    cancelAddPlayer()
                },
                onDeletePlayerButton = {
                    if (playersListUiState.playersList.isEmpty()) {
                        deletePlayerExpanded = false
                    } else {
                        deletePlayerExpanded = !deletePlayerExpanded
                    }
                },
                onAddJogadorButtonEnabled = !addPlayerExpanded,
                onDeletePlayerButtonEnabled = !deletePlayerExpanded,
                addJogadorExpanded = addPlayerExpanded,
                deletePlayerExpanded = deletePlayerExpanded,
                onAddJogadorExpanded = { addPlayerExpanded = it },
                cancelAddPlayer = cancelAddPlayer,
                deletePlayer = deletePlayer,
                onNicknameChange = onNicknameChange,
                onConfirmClicked = onConfirmClicked,
                playersListUiState = playersListUiState,
            )
        }
    }
}

@Composable
fun PlayersListMediumAndExpandedContent(
    playersListUiState: PlayersListUiState,
    onNicknameChange: (String) -> Unit,
    deletePlayer: (String) -> Unit,
    cancelAddPlayer: () -> Unit,
    onConfirmClicked: () -> Unit,
    onAddJogadorButton: () -> Unit,
    onDeletePlayerButton: () -> Unit,
    onAddJogadorButtonEnabled: Boolean,
    onDeletePlayerButtonEnabled: Boolean,
    addJogadorExpanded: Boolean,
    deletePlayerExpanded: Boolean,
    onAddJogadorExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row (
        modifier = modifier,
    ){
        BackgroundImage(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        )

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.Black
        )


        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AddJogadorButton(
                onClick = onAddJogadorButton,
                enabled = onAddJogadorButtonEnabled,
            )

            DeletePlayerButton(
                onClick = onDeletePlayerButton,
                enabled = onDeletePlayerButtonEnabled,
            )

            AnimatedVisibility(
                visible = addJogadorExpanded,
            ) {
                AddPlayerWindow(
                    nickname = playersListUiState.nickname,
                    onNicknameChange = onNicknameChange,
                    onCancelClick = {
                        cancelAddPlayer()
                        onAddJogadorExpanded(false)
                    },
                    onConfirmClick = onConfirmClicked,
                    nicknameError = playersListUiState.nicknameError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .alpha(0.9f)
                        .verticalScroll(rememberScrollState()),
                )
            }
        }

        PlayersList(
            playersListUiState = playersListUiState,
            deletePlayer = deletePlayer,
            deletePlayerExpanded = deletePlayerExpanded,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.9f)
                .animateContentSize()
                .padding(10.dp)
                .weight(1f),
        )
    }
}
@Composable
fun PlayersListCompactContent(
    playersListUiState: PlayersListUiState,
    onNicknameChange: (String) -> Unit,
    deletePlayer: (String) -> Unit,
    cancelAddPlayer: () -> Unit,
    onConfirmClicked: () -> Unit,
    onAddJogadorButton: () -> Unit,
    onDeletePlayerButton: () -> Unit,
    onAddJogadorButtonEnabled: Boolean,
    onDeletePlayerButtonEnabled: Boolean,
    addJogadorExpanded: Boolean,
    deletePlayerExpanded: Boolean,
    onAddJogadorExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.TopCenter
    ){
        BackgroundImage(
            modifier = Modifier
                .fillMaxSize()
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(15.dp)
        ){
            Row (
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ){
                AddJogadorButton(
                    onClick = onAddJogadorButton,
                    enabled = onAddJogadorButtonEnabled,
                    modifier = Modifier
                        .weight(1f)
                )
                DeletePlayerButton(
                    onClick = onDeletePlayerButton,
                    enabled = onDeletePlayerButtonEnabled,
                    modifier = Modifier
                        .weight(1f),
                )
            }

            AnimatedVisibility(
                visible = addJogadorExpanded
            ) {
                AddPlayerWindow(
                    nickname = playersListUiState.nickname,
                    onNicknameChange = onNicknameChange,
                    onCancelClick = {
                        cancelAddPlayer()
                        onAddJogadorExpanded(false)
                    },
                    onConfirmClick = onConfirmClicked,
                    nicknameError = playersListUiState.nicknameError,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                        .alpha(0.9f)
                        .verticalScroll(rememberScrollState()),
                )
            }

            PlayersList(
                playersListUiState = playersListUiState,
                deletePlayer = deletePlayer,
                deletePlayerExpanded = deletePlayerExpanded,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 10.dp)
                    .alpha(0.9f)
                    .animateContentSize(),
            )
        }
    }
}

@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.fundoa),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
    )
}

@Composable
fun AddJogadorButton(
    enabled: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PlayersListButton(
        onClick = onClick,
        icone = Icons.Filled.Add,
        textinho = stringResource(R.string.add_player_button),
        modifier = modifier,
        enabled = enabled,
    )
}

@Composable
fun DeletePlayerButton(
    onClick: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier,
) {
    PlayersListButton(
        onClick = onClick,
        icone = Icons.Filled.Delete,
        textinho = stringResource(R.string.delete_player_button),
        modifier = modifier,
        enabled = enabled,
    )
}

@Composable
fun PlayersList(
    playersListUiState: PlayersListUiState,
    deletePlayer: (String) -> Unit,
    deletePlayerExpanded: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color.Black),

        ) {
        if (playersListUiState.playersList.isEmpty()) {
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.empty_players_list),
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    fontStyle = FontStyle.Italic,
                    fontSize = 18.sp
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                itemsIndexed(playersListUiState.playersList) { index, person ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}."
                        )
                        PlayersListNameAndNicknameItem(
                            nickname = person.nickname,
                            modifier = Modifier
                                .weight(1f)
                        )
                        AnimatedVisibility(
                            visible = deletePlayerExpanded
                        ) {
                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp)
                            )
                            IconButton(
                                onClick = {
                                    deletePlayer(person.nickname)
                                },
                                modifier = Modifier
                                    .size(40.dp)
                                    .animateContentSize()
                                    .testTag(deleteIconTestTag)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = null,
                                    tint = Color.Red,
                                )
                            }
                        }
                    }
                    Divider(
                        thickness = 1.dp,
                        color = Color.DarkGray,
                        modifier = Modifier
                            .padding(3.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PlayersListNameAndNicknameItem(
    nickname: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = nickname,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = Color(0xFF000080),
        fontStyle = FontStyle.Italic,
        modifier = modifier
            .animateContentSize()
            .testTag(eachPersonNicknameInTheListTestTag)
    )
}

@Composable
fun AddPlayerWindow(
    nickname: String,
    onNicknameChange: (String) -> Unit,
    onCancelClick: () -> Unit,
    onConfirmClick: () -> Unit,
    modifier: Modifier = Modifier,
    nicknameError: NameOrNicknameError?,
) {
    Card (
        modifier = modifier,
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color.Black),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
            Text(
                text = stringResource(R.string.add_jogador_window_title),
            )
            TextField(
                value = nickname,
                onValueChange = onNicknameChange,
                label = {Text(text = stringResource(R.string.add_player_label_nickname_text_field))},
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(nicknameTextFieldTestTag),
                supportingText = {
                    when (nicknameError) {
                        NameOrNicknameError.Empty -> Text(text = stringResource(R.string.empty_error_message))
                        NameOrNicknameError.Exists -> Text(text = stringResource(R.string.nickname_exists_erros_message))
                        else -> {}
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Words),
                isError = nicknameError != null,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ){
                PlayersListButton(
                    onClick = onCancelClick,
                    icone = Icons.Filled.Close,
                    textinho = stringResource(R.string.add_player_window_cancel_button),
                    modifier = Modifier
                        .weight(1f)
                )
                PlayersListButton(
                    onClick = onConfirmClick,
                    icone = Icons.Filled.Done,
                    textinho = stringResource(R.string.add_player_window_confirm_button),
                    modifier = Modifier
                        .weight(1f)
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SevenWondersAppBar(
    onBackClick: () -> Unit,
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
            navigationIcon ={
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.navigation_back_content_description)
                    )
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
fun PlayersListButton(
    onClick: () -> Unit,
    icone: ImageVector,
    textinho: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val x = if (enabled) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Gray
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = x
        )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp)
        ){
            Icon(
                imageVector = icone,
                contentDescription = null
            )
            Text(
                text = textinho,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun PlayersListCompactPreview() {
    SevenWondersTheme {
        PlayersListScreenSecundaria(
            onBackClick = {},
            playersListUiState = PlayersListUiState(
                "",
                listOf(
                    Person("Zinho "),
                    Person("Deivinho"),
                    Person("Anninha"),
                )
            ),
            onNicknameChange = {},
            deletePlayer = {},
            cancelAddPlayer = {},
            onConfirmClicked = {},
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
        )
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun PlayersListMediunAndExpandedPreview() {
    SevenWondersTheme {
        PlayersListScreenSecundaria(
            onBackClick = {},
            playersListUiState = PlayersListUiState(
                "",
                listOf(
                    Person("Zinho "),
                    Person("Deivinho"),
                    Person("Anninha"),
                )
            ),
            onNicknameChange = {},
            deletePlayer = {},
            cancelAddPlayer = {},
            onConfirmClicked = {},
            windowWidthSizeClass = WindowWidthSizeClass.Medium,
        )
    }
}

@Preview
@Composable
fun PlayersListEmptyCompactPreview() {
    SevenWondersTheme {
        PlayersListScreenSecundaria(
            onBackClick = {},
            playersListUiState = PlayersListUiState(),
            onNicknameChange = {},
            deletePlayer = {},
            cancelAddPlayer = {},
            onConfirmClicked = {},
            windowWidthSizeClass = WindowWidthSizeClass.Compact
        )
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
fun PlayersListEmptyMediumAndExpandedPreview() {
    SevenWondersTheme {
        PlayersListScreenSecundaria(
            onBackClick = {},
            playersListUiState = PlayersListUiState(),
            onNicknameChange = {},
            deletePlayer = {},
            cancelAddPlayer = {},
            onConfirmClicked = {},
            windowWidthSizeClass = WindowWidthSizeClass.Medium
        )
    }
}

@Preview
@Composable
fun AddPlayerWindowPreview() {
    SevenWondersTheme {
        AddPlayerWindow(
            nickname = "",
            onNicknameChange = {},
            onCancelClick = { /*TODO*/ },
            onConfirmClick = { /*TODO*/ },
            nicknameError = null
        )
    }
}

