package com.gmail.luizjmfilho.sevenwonders.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gmail.luizjmfilho.sevenwonders.ui.WonderSide
import com.gmail.luizjmfilho.sevenwonders.ui.Wonders

@Entity
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val matchId: Int,
    val nickname: String,
    val wonder: Wonders,
    val wonderSide: WonderSide,
    val totalScore: Int,
    val wonderBoardScore: Int,
    val coinScore: Int,
    val coinQuantity: Int,
    val warScore: Int,
    val blueCardScore: Int,
    val yellowCardScore: Int,
    val greenCardScore: Int,
    val purpleCardScore: Int,
)
