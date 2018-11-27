package com.tanyayuferova.muteme.di.database;

import android.arch.persistence.room.Room;
import android.content.Context;
import com.tanyayuferova.muteme.data.LocationsDao;
import com.tanyayuferova.muteme.data.MuteMeDatabase;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Module
public abstract class DatabaseModule {

    @Singleton
    @Provides
    static MuteMeDatabase provideMuteMeDatabase(Context context) {
        return Room.databaseBuilder(
            context,
            MuteMeDatabase.class,
            MuteMeDatabase.DATABASE_NAME
        )
            .allowMainThreadQueries()
            .build();
    }

    @Singleton
    @Provides
    static LocationsDao provideShowDao(MuteMeDatabase muteMeDatabase) {
        return muteMeDatabase.locationsDao();
    }
}
