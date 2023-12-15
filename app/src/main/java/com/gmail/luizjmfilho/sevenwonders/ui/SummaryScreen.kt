package com.gmail.luizjmfilho.sevenwonders.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gmail.luizjmfilho.sevenwonders.R
import com.gmail.luizjmfilho.sevenwonders.ui.theme.SevenWondersTheme

@Composable
fun SummaryScreenPrimaria(

) {

}

@Composable
fun SummaryScreenSecundaria(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SevenWondersAppBar(
                onBackClick = onBackClick,
                title = stringResource(id = R.string.summary_top_bar)
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
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize()
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(3.dp)
                ) {
                    Text(
                        text = stringResource(R.string.summary_configuration_subtitle)
                    )
                    PlayerSummaryCard(
                        wonder = Wonders.HALIKARNASSOS,
                        wonderSide = WonderSide.Day
                    )
                    PlayerSummaryCard(
                        wonder = Wonders.OLYMPIA,
                        wonderSide = WonderSide.Day
                    )
                    PlayerSummaryCard(
                        wonder = Wonders.GIZAH,
                        wonderSide = WonderSide.Night
                    )

                }
            }
        }

        
    }
}


@Composable
fun PlayerSummaryCard(
    wonder: Wonders,
    wonderSide: WonderSide,
) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .width(180.dp)
            .aspectRatio(2.037037f),
        border = BorderStroke(1.dp, Color.Gray)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = identifyTheBackgroundImage(wonder = wonder, wonderSide = wonderSide),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(Color.White.copy(0.8f)),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Text(
                        text = "Luiz",
                        modifier = Modifier
                            .padding(3.dp),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Card(
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(Color.White.copy(0.8f)),
                    border = BorderStroke(1.dp, Color.Black),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(3.dp),
                        modifier = Modifier
                            .padding(3.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = convertWonderToString(wonder = wonder),
                            modifier = Modifier
                                .padding(3.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Icon(
                            imageVector = when (wonderSide) {
                                WonderSide.Day -> Icons.Filled.WbSunny
                                WonderSide.Night -> Icons.Filled.Bedtime
                            },
                            tint = when (wonderSide) {
                                WonderSide.Day -> Color(0xFFFF8C00)
                                WonderSide.Night -> Color.Blue
                            },
                            contentDescription = null,
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun identifyTheBackgroundImage(wonder: Wonders, wonderSide: WonderSide): Painter {
    return when (wonder) {
        Wonders.ALEXANDRIA -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.alexandria_day)
                WonderSide.Night -> painterResource(id = R.drawable.alexandria_night)
            }
        }
        Wonders.BABYLON -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.babylon_day)
                WonderSide.Night -> painterResource(id = R.drawable.babylon_night)
            }
        }
        Wonders.EPHESOS -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.ephesos_day)
                WonderSide.Night -> painterResource(id = R.drawable.ephesos_night)
            }
        }
        Wonders.GIZAH -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.gizah_day)
                WonderSide.Night -> painterResource(id = R.drawable.gizah_night)
            }
        }
        Wonders.HALIKARNASSOS -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.halikarnassos_day)
                WonderSide.Night -> painterResource(id = R.drawable.halikarnassos_night)
            }
        }
        Wonders.OLYMPIA -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.olympia_day)
                WonderSide.Night -> painterResource(id = R.drawable.olympia_night)
            }
        }
        Wonders.RHODOS -> {
            when (wonderSide) {
                WonderSide.Day -> painterResource(id = R.drawable.rhodos_day)
                WonderSide.Night -> painterResource(id = R.drawable.rhodos_night)
            }
        }
    }
}

@Preview
@Composable
fun PlayerSummaryCardPreview() {
    SevenWondersTheme {
        PlayerSummaryCard(
            wonder = Wonders.OLYMPIA,
            wonderSide = WonderSide.Day
        )
    }
}

@Preview
@Composable
fun SummaryScreenPreview() {
    SevenWondersTheme {
        SummaryScreenSecundaria(
            onBackClick = { /*TODO*/ }
        )
    }
}