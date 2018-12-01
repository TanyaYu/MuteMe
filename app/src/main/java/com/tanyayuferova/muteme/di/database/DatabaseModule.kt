package com.tanyayuferova.muteme.di.database

import android.arch.persistence.room.Room
import android.content.Context
import com.tanyayuferova.muteme.data.LocationsDao
import com.tanyayuferova.muteme.data.MuteMeDatabase

import javax.inject.Singleton
import dagger.Module
import dagger.Provides

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Module
class DatabaseModule {

    @Singleton
    @Provides
    internal fun provideMuteMeDatabase(context: Context): MuteMeDatabase {
        return Room.databaseBuilder(
            context,
            MuteMeDatabase::class.java,
            MuteMeDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build()
    }

    @Singleton
    @Provides
    internal fun provideShowDao(muteMeDatabase: MuteMeDatabase): LocationsDao {
        return muteMeDatabase.locationsDao()
    }
}
