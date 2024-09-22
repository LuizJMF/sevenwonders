package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.gmail.luizjmfilho.sevenwonders.R

val Kings = FontFamily(
    Font(R.font.kings_regular)
)

val sevenWondersTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 50.sp,
    ),
    titleMedium = TextStyle(
        fontSize = 25.sp,
    ),
)

val Typography.bodyLargeEmphasis: TextStyle
    @Composable
    get() = MaterialTheme.typography.bodyLarge.copy(
        color = MaterialTheme.colorScheme.tertiary,
        fontStyle = FontStyle.Italic,
    )