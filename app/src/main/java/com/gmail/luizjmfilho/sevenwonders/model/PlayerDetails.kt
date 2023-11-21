package com.gmail.luizjmfilho.sevenwonders.model

import com.gmail.luizjmfilho.sevenwonders.ui.WonderSide
import com.gmail.luizjmfilho.sevenwonders.ui.Wonders

data class PlayerDetails(
    val nickname: String,
    val wonder: Wonders?,
    val wonderSide: WonderSide?,
)
