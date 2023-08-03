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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.data.NameError
import com.gmail.luizjmfilho.sevenwonders.data.NicknameError
import com.gmail.luizjmfilho.sevenwonders.data.PlayersListUiState
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun PlayersListScreenPrimaria(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    playersListViewModel: PlayersListViewModel = viewModel(),

    ) {
    val playersListUiState by playersListViewModel.uiState.collectAsState()
    PlayersListScreenSecundaria(
        onBackClick = onBackClick,
        playersListUiState = playersListUiState,
        modifier = modifier,
        onNameChange = { playersListViewModel.updateName(it) },
        onNicknameChange = { playersListViewModel.updateNickname(it) },
        apagarJogador = { nome, apelido -> playersListViewModel.apagarJogador(nome, apelido) },
        cancelarAddJogador = { playersListViewModel.cancelarAddJogador()},
        onConfirmarClicked = {playersListViewModel.addJogador()}
    )
}

@Composable
fun PlayersListScreenSecundaria(
    onBackClick: () -> Unit,
    playersListUiState: PlayersListUiState,
    onNameChange: (String) -> Unit,
    onNicknameChange: (String) -> Unit,
    apagarJogador: (String, String) -> Unit,
    cancelarAddJogador: () -> Unit,
    onConfirmarClicked: () -> Unit,
    modifier: Modifier = Modifier,
){

    var addJogadorExpanded by rememberSaveable() { mutableStateOf(false) }
    var apagarJogadorExpanded by rememberSaveable() { mutableStateOf(false) }

    Scaffold (
        topBar = {
            PlayersListAppBar(
                onBackClick = onBackClick
            )
        },
        modifier = modifier,
    ) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .padding(scaffoldPadding),
            contentAlignment = Alignment.TopCenter
        ){
            Image(
                painter = painterResource(id = R.drawable.fundoa),
                contentDescription = null,
                contentScale = ContentScale.Crop,
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
                    PlayersListButton(
                        onClick = {
                            addJogadorExpanded = !addJogadorExpanded
                            cancelarAddJogador()
                                  },
                        icone = Icons.Filled.Add,
                        textinho = stringResource(R.string.adicionar_jogador_button),
                        modifier = Modifier
                            .weight(1f),
                        enabled = !addJogadorExpanded,
                    )
                    PlayersListButton(
                        onClick = {
                            if (playersListUiState.listaDeJogadores.isEmpty()) {
                                apagarJogadorExpanded = false
                            } else {
                                apagarJogadorExpanded = !apagarJogadorExpanded
                            }
                        },
                        icone = Icons.Filled.Delete,
                        textinho = stringResource(R.string.apagar_jogador_button),
                        modifier = Modifier
                            .weight(1f),
                        enabled = !apagarJogadorExpanded,
                    )
                }
                AnimatedVisibility(
                    visible = addJogadorExpanded
                ) {
                    AddPlayerWindow(
                        name = playersListUiState.nome,
                        onNameChange = onNameChange,
                        nickname = playersListUiState.apelido,
                        onNicknameChange = onNicknameChange ,
                        onCancelarClick = {
                            cancelarAddJogador()
                            addJogadorExpanded = false
                        },
                        onConfirmarClick = onConfirmarClicked,
                        nameError = playersListUiState.nameError,
                        nicknameError = playersListUiState.nicknameError,
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                        .alpha(0.9f)
                        .animateContentSize(),
                    colors = CardDefaults.cardColors(Color.White),
                    border = BorderStroke(1.dp, Color.Black),

                ){
                    if (playersListUiState.listaDeJogadores.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .padding(10.dp)
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ){
                            Text(
                                text = stringResource(R.string.lista_jogadores_vazia),
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
                            itemsIndexed(playersListUiState.listaDeJogadores) { index, person ->
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(2.dp) ,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(IntrinsicSize.Min),
                                ) {
                                    Text(
                                        text = "${index + 1}."
                                    )
                                    Column(
                                        modifier = Modifier
                                            .weight(1f),
                                        verticalArrangement = Arrangement.spacedBy(2.dp)
                                    ) {
                                        Text(
                                            text = person.nome,
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier
                                                .animateContentSize()
                                        )
                                        Text(
                                            text = person.apelido,
                                            color = Color.Gray,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                    AnimatedVisibility(
                                        visible = apagarJogadorExpanded
                                    ) {
                                        Divider(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .width(1.dp)
                                        )
                                        IconButton(
                                            onClick = {
                                                apagarJogador(person.nome, person.apelido)
                                            },
                                            modifier = Modifier
                                                .size(40.dp)
                                                .animateContentSize()
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
        }
    }
}


@Composable
fun AddPlayerWindow(
    name: String,
    onNameChange: (String) -> Unit,
    nickname: String,
    onNicknameChange: (String) -> Unit,
    onCancelarClick: () -> Unit,
    onConfirmarClick: () -> Unit,
    modifier: Modifier = Modifier,
    nameError: NameError?,
    nicknameError: NicknameError?,
) {

    Card (
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .alpha(0.9f),
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
                value = name,
                onValueChange = onNameChange ,
                label = {Text(text = "Nome completo")},
                modifier = Modifier
                    .fillMaxWidth(),
                supportingText = {
                    when (nameError) {
                        NameError.Empty -> Text(text = "Este campo é obrigatório")
                        NameError.Exists -> Text(text = "Este nome já está cadastrado na lista")
                        else -> {}
                    }
                },
                isError = nameError != null,
            )
            TextField(
                value = nickname,
                onValueChange = onNicknameChange,
                label = {Text(text = "Apelido")},
                modifier = Modifier
                    .fillMaxWidth(),
                supportingText = {
                    when (nicknameError) {
                        NicknameError.Empty -> Text(text = "Este campo é obrigatório")
                        NicknameError.Exists -> Text(text = "Este apelido já está cadastrado na lista")
                        else -> {}
                    }
                },
                isError = nicknameError != null,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
            ){
                PlayersListButton(
                    onClick = onCancelarClick,
                    icone = Icons.Filled.Close,
                    textinho = "Cancelar",
                    modifier = Modifier
                        .weight(1f)
                )
                PlayersListButton(
                    onClick = onConfirmarClick,
                    icone = Icons.Filled.Done,
                    textinho = "Confirmar",
                    modifier = Modifier
                        .weight(1f)
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayersListAppBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier
    ){
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.lista_de_jogadores_topbar),
                    style = MaterialTheme.typography.titleMedium
                )
            },
            navigationIcon ={
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = null
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

@Composable
fun JanelinhaJogadorAdicionado(
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(Color.White),
        border = BorderStroke(1.dp, Color.Gray)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .padding(8.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.CheckCircle,
                contentDescription = null,
                tint = Color.Green
            )
            Text(
                text = "Jogador adicionado!",
                color = Color.Gray,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Preview
@Composable
fun PlayersListPreview() {
    SevenWondersTheme {
        PlayersListScreenPrimaria(
            onBackClick = {},
        )
    }
}

@Preview
@Composable
fun PlayersListEmptyPreview() {
    SevenWondersTheme {
        PlayersListScreenPrimaria(
            onBackClick = {},
        )
    }
}
