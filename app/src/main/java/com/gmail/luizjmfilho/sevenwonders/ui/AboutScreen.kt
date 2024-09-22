package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme
import com.gmail.luizjmfilho.sevenwonders.ui.theme.bodyLargeEmphasis

@Composable
fun AboutScreen(
    viewModel: AboutViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    WithLifecycleOwner(viewModel)

    val uiState by viewModel.uiState.collectAsState()

    AboutScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
fun AboutScreen(
    uiState: AboutUiState,
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
        modifier = modifier,
    ) { scaffoldPadding ->
        Box(
            modifier = Modifier
                .padding(scaffoldPadding),
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_image),
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
                        AppNameAndVersionSection(
                            appVersion = uiState.appVersion,
                        )

                        Image(
                            painter = painterResource(id = R.drawable.official_icon),
                            contentDescription = null
                        )

                        ContactEmailSection()
                    }
                }

                DeveloperNamesSection(
                    modifier = Modifier
                        .padding(10.dp)
                        .align(Alignment.End),
                )
            }
        }
    }
}

@Composable
private fun AppNameAndVersionSection(
    appVersion: String?,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(text = stringResource(id = R.string.app_name))

        if (appVersion != null) {
            Text(
                text = stringResource(R.string.app_version, appVersion),
                style = MaterialTheme.typography.bodyLargeEmphasis,
            )
        }
    }
}

@Composable
private fun ContactEmailSection(
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(text = stringResource(R.string.email_to_suggestions_text))

        Text(
            text = stringResource(R.string.email_address),
            style = MaterialTheme.typography.bodyLargeEmphasis,
        )
    }
}

@Composable
private fun DeveloperNamesSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        Text(text = stringResource(R.string.developed_by))

        Text(
            text = stringResource(R.string.zinho_full_name),
            style = MaterialTheme.typography.bodyLargeEmphasis,
        )

        Text(
            text = stringResource(R.string.deivinho_full_name),
            style = MaterialTheme.typography.bodyLargeEmphasis,
        )
    }
}

@Preview
@Composable
private fun AboutScreenWithVersionPreview() {
    SevenWondersTheme {
        AboutScreen(
            uiState = AboutUiState(
                appVersion = "1.0.0",
            ),
            onBackClick = {}
        )
    }
}

@Preview
@Composable
private fun AboutScreenWithoutVersionPreview() {
    SevenWondersTheme {
        AboutScreen(
            uiState = AboutUiState(
                appVersion = null,
            ),
            onBackClick = {}
        )
    }
}