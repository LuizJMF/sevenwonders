package com.gmail.luizjmfilho.sevenwonders.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gmail.luizjmfilho.sevenwonders.model.Person

@Database(
    entities = [Person::class],
    version = 1,
    exportSchema = true,
)
abstract class SevenWondersDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}

private var sevenWondersDatabaseInstance: SevenWondersDatabase? = null

private fun createSevenWondersDatabase(context: Context): SevenWondersDatabase {
    return Room.databaseBuilder(context, SevenWondersDatabase::class.java, "SevenWonders.db").build()
}

fun getSevenWondersDatabaseInstance(context: Context): SevenWondersDatabase {
    if (sevenWondersDatabaseInstance != null) {
        return sevenWondersDatabaseInstance!!
    }

    sevenWondersDatabaseInstance = createSevenWondersDatabase(context)
    return sevenWondersDatabaseInstance!!
}