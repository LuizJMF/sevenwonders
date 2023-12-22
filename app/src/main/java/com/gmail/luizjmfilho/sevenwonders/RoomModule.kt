package com.gmail.luizjmfilho.sevenwonders

import android.content.Context
import androidx.room.Room
import com.gmail.luizjmfilho.sevenwonders.data.MatchDao
import com.gmail.luizjmfilho.sevenwonders.data.PersonDao
import com.gmail.luizjmfilho.sevenwonders.data.SevenWondersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun createDatabase(@ApplicationContext context: Context): SevenWondersDatabase {
        return Room.databaseBuilder(context, SevenWondersDatabase::class.java, "SevenWonders.db").build()
    }

    @Provides
    fun createDao(database: SevenWondersDatabase): PersonDao {
        return database.personDao()
    }

    @Provides
    fun matchDao(database: SevenWondersDatabase): MatchDao {
        return database.matchDao()
    }
}


