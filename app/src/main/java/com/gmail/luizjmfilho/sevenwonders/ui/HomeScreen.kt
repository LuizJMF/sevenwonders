package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun HomeScreen(
    onListaDeJogadoresClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ){
        Image(
            painter = painterResource(id = R.drawable.fundob),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
                ){
            Box (
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .weight(2f)
            ){
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge
                )
            }
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(8f)
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    HomeScreenButton(
                        onClick = { /*TODO*/ },
                        textinho = stringResource(R.string.criar_partida_button)
                    )
                    HomeScreenButton(
                        onClick = onListaDeJogadoresClick,
                        textinho = stringResource(R.string.lista_de_jogadores_button)
                    )
                    HomeScreenButton(
                        onClick = { /*TODO*/ },
                        textinho = stringResource(R.string.estatisticas_button)
                    )
                    HomeScreenButton(
                        onClick = { /*TODO*/ },
                        textinho = stringResource(R.string.historico_de_partidas_button)
                    )

                }
            }
            Spacer(Modifier.weight(2f))
        }
    }

}

@Composable
fun HomeScreenButton(
    onClick: () -> Unit,
    textinho: String,
    modifier: Modifier = Modifier,
){
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = textinho,
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    SevenWondersTheme {
        HomeScreen(
            onListaDeJogadoresClick = {}
        )
    }
}


