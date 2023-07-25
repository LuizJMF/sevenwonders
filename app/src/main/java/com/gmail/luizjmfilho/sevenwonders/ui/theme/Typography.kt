package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.gmail.luizjmfilho.sevenwonders.R


val Kings = FontFamily(
    Font(R.font.kings_regular)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = Kings,
        fontSize = 50.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = Kings,
        fontSize = 25.sp,
    ),
)

