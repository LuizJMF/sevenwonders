package com.gmail.luizjmfilho.sevenwonders.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

//Light Theme
val darkGray = Color(0xFF474747)
val darkBlue = Color(0xFF0049A3)
val lightBlue = Color(0xFF0068E9)
val veryLightBlue = Color(0xFFB8D8FF)

//Dark Theme
val lightGray = Color(0xFFCACACA)
val lightPurple = Color(0xFFB496F1)
val darkPurple = Color(0xFF4000B3)

val ColorScheme.science: Color
    @Composable
    get() = Color(0xFF1E9923)

@Composable
fun getLightThemeColor(): ColorScheme {
    return lightColorScheme(
        surface = veryLightBlue,
        onSurface = darkBlue,
        primary = darkBlue,
        onPrimary = Color.White,
        secondary = lightBlue,
        tertiary = darkGray,
        background = Color.White,
        onBackground = Color.Black,
    )
}

@Composable
fun getDarkThemeColor(): ColorScheme {
    return darkColorScheme(
        surface = darkPurple,
        onSurface = lightPurple,
        primary = lightPurple,
        onPrimary = Color.Black,
        secondary = lightPurple,
        tertiary = lightGray,
        background = darkPurple,
        onBackground = Color.White,
    )
}

@Composable
fun sevenWondersColorScheme(darkTheme: Boolean): ColorScheme =
    if (darkTheme) getDarkThemeColor() else getLightThemeColor()
