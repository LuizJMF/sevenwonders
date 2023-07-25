package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.model.Pessoa
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun PlayersListScreen(
    personList: List<Pessoa>,
    onAddJogadorClick: () -> Unit,
    onApagarJogadorClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
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
                        onClick = onAddJogadorClick,
                        icone = Icons.Filled.Add,
                        textinho = stringResource(R.string.adicionar_jogador_button),
                        modifier = Modifier
                            .weight(1f),
                    )
                    PlayersListButton(
                        onClick = onApagarJogadorClick,
                        icone = Icons.Filled.Delete,
                        textinho = stringResource(R.string.apagar_jogador_button),
                        modifier = Modifier
                            .weight(1f),
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 10.dp)
                        .alpha(0.9f),
                    colors = CardDefaults.cardColors(Color.White),
                    border = BorderStroke(1.dp, Color.Black),

                ){
                    if (personList.isEmpty()) {
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
                            itemsIndexed(personList) { index, person ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "${index + 1}."
                                    )
                                    Column(
                                        modifier = Modifier
                                            .padding(start = 2.dp)
                                    ) {
                                        Text(
                                            text = person.nome
                                        )
                                        Text(
                                            text = person.apelido,
                                            color = Color.Gray,
                                            fontStyle = FontStyle.Italic
                                        )
                                    }
                                }
                                Divider(
                                    thickness = 1.dp,
                                )
                            }
                        }
                    }
                }
                
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class) //adicionei isso por causa da TopAppBar que não entrou
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
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
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
fun PlayersListPreview() {
    SevenWondersTheme {
        PlayersListScreen(
            personList = listOf(
                Pessoa("Luiz José de Medeiros Filho", "Zinho"),
                Pessoa("Crístian Deives dos Santos Viana", "Deivinho"),
                Pessoa("Anna Luisa Espínola de Sena Costa", "Anninha"),
            ),
            onAddJogadorClick = {},
            onApagarJogadorClick = {},
            onBackClick = {},
        )
    }
}

@Preview
@Composable
fun PlayersListEmptyPreview() {
    SevenWondersTheme {
        PlayersListScreen(
            personList = emptyList(),
            onAddJogadorClick = {},
            onApagarJogadorClick = {},
            onBackClick = {},
        )
    }
}