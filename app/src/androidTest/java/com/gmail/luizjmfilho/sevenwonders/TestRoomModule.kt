package com.gmail.luizjmfilho.sevenwonders

import android.content.Context
import androidx.room.Room
import com.gmail.luizjmfilho.sevenwonders.data.PersonDao
import com.gmail.luizjmfilho.sevenwonders.data.SevenWondersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RoomModule::class]
)
object TestRoomModule {
    @Singleton
    @Provides
    fun createInMemoryDatabase(
        @ApplicationContext context: Context
    ): SevenWondersDatabase {
        return Room.inMemoryDatabaseBuilder(context, SevenWondersDatabase::class.java).build()
    }

    @Provides
    fun createDao(database: SevenWondersDatabase): PersonDao {
        return database.personDao()
    }
}