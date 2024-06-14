package com.gmail.luizjmfilho.sevenwonders.data

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RenameColumn
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import com.gmail.luizjmfilho.sevenwonders.model.Match
import com.gmail.luizjmfilho.sevenwonders.model.Person

@Database(
    entities = [Person::class, Match::class],
    version = 4,
    autoMigrations = [
        AutoMigration(from = 3, to = 4, spec = AutoMigration3To4::class),
    ]
)
abstract class SevenWondersDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
    abstract fun matchDao(): MatchDao
}

@RenameColumn(tableName = "Person", fromColumnName = "nickname", toColumnName = "name")
class AutoMigration3To4 : AutoMigrationSpec