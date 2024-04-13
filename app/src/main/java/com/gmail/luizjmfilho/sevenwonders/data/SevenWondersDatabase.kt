package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.model.Person

@Database(
    entities = [Person::class, Match::class],
    version = 4,
    exportSchema = true,
)
abstract class SevenWondersDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun matchDao(): MatchDao
}