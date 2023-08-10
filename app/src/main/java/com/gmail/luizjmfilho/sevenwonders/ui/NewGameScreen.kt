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
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.material.icons.outlined.PersonRemove
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.data.ActivePlayersNumber
import com.gmail.luizjmfilho.sevenwonders.data.NewGameUiState
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme


@Composable
fun NewGameScreenPrimaria() {

}

@Composable
fun NewGameScreenSecundaria(
    onBackClick: () -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    newGameViewModel: NewGameViewModel = viewModel()
) {
    val newGameUiState by newGameViewModel.uiState.collectAsState()
    Scaffold (
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(id = R.string.new_game_top_bar)
            )
        },
        modifier = modifier,
    ) { scaffoldPadding ->

        Box(
            modifier = modifier
                .padding(scaffoldPadding)
        ){
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
                        newGameUiState = newGameUiState,
                        onPlayerChange = { playerPosition, playerName ->
                            newGameViewModel.updatePlayer(playerPosition, playerName) },
                        textColor = Color.Blue,
                        generalColor = Color.Black,
                    )
                    Row {
                        AnimatedVisibility(
                            visible = newGameUiState.activePlayersNumber < ActivePlayersNumber.Seven
                        ) {
                            TextButton(
                                onClick = { newGameViewModel.newGameAddPlayer() },
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
                                    Text(text = "Adicionar")
                                }
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        AnimatedVisibility(
                            visible = newGameUiState.activePlayersNumber > ActivePlayersNumber.Three
                        ) {
                            TextButton(
                                onClick = { newGameViewModel.newGameRemovePlayer() }
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
                                    Text(text = "Remover", color = Color.Red)
                                }
                            }
                        }
                    }
                    Spacer(Modifier.weight(1f))
                    Row(){
                        Spacer(Modifier.weight(1f))
                        TextButton(
                            onClick = onNextClick,
                            enabled = newGameUiState.isAdvanceAndAddPlayerButtonsEnable
                        ) {
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ){
                                Text(text = "AVANÇAR")
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGameTextFieldGroup(
    newGameUiState: NewGameUiState,
    onPlayerChange: (Int, String) -> Unit,
    textColor: Color,
    generalColor: Color,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
    ) {
        for (i in 0 .. 6){
            AnimatedVisibility(
                visible = (i < newGameUiState.activePlayersNumber.numValue)
            ) {
                NewGameTextField(
                    value = newGameUiState.activePlayersList[i],
                    onValueChange = { onPlayerChange(i, it) },
                    playerNumber = i,
                    textColor = textColor,
                    generalColor = generalColor,
                    keyboardOptions = if (i < newGameUiState.activePlayersNumber.numValue - 1) {
                        KeyboardOptions(imeAction = ImeAction.Next)
                    } else {
                        KeyboardOptions(imeAction = ImeAction.Done)
                    },
                    keyboardActions = if (i < newGameUiState.activePlayersNumber.numValue - 1) {
                        KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
                    } else {
                        KeyboardActions(onDone = { focusManager.clearFocus() })
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGameTextField(
    value: String,
    onValueChange: (String) -> Unit,
    playerNumber: Int,
    textColor: Color,
    generalColor: Color,
    keyboardOptions: KeyboardOptions,
    keyboardActions: KeyboardActions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = "Jogador ${playerNumber + 1}") },
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
        leadingIcon = { Icon(imageVector = Icons.Outlined.Person, contentDescription = null) },
        trailingIcon = {
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = null)
            }
        },
        modifier = modifier
            .fillMaxWidth(),
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
            onNextClick = {}
        )
    }
}