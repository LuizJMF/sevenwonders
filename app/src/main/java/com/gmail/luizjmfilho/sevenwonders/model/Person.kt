package com.gmail.luizjmfilho.sevenwonders.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(
    val name: String,
    val nickname: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
)
