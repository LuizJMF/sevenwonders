package com.gmail.luizjmfilho.sevenwonders.ui

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun AboutScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(R.string.about_the_app)
            )
        },
    ) { scaffoldPadding ->
        Box(
            modifier = modifier
                .padding(scaffoldPadding)
                .fillMaxSize(),
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
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(5.dp),
                    ) {
                        val context = LocalContext.current
                        val version = context.packageManager.getPackageInfo(context.packageName, 0).versionName
                        Text(text = stringResource(id = R.string.app_name))
                        Text(
                            text = stringResource(R.string.app_version, version),
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Image(
                            painter = painterResource(id = R.drawable.official_icon),
                            contentDescription = null
                        )
                        Text(text = stringResource(R.string.email_to_suggestions))
                        Text(
                            text = stringResource(R.string.email_para_contato),
                            fontStyle = FontStyle.Italic,
                            color = MaterialTheme.colorScheme.tertiary
                        )

                    }
                }
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    Text(text = stringResource(R.string.desenvolvido_por))
                    Text(
                        text = stringResource(R.string.zinho_nome_completo),
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Text(
                        text = stringResource(R.string.deivinho_nome_completo),
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun AboutScreenPreview() {
    SevenWondersTheme {
        AboutScreen(
            onBackClick = {}
        )
    }
}