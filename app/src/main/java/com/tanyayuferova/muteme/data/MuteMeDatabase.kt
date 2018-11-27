package com.tanyayuferova.muteme.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tanyayuferova.muteme.data.MuteMeDatabase.Companion.DATABASE_VERSION

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Database(entities = [LocationData::class], version = DATABASE_VERSION)
abstract class MuteMeDatabase : RoomDatabase() {

    abstract fun locationsDao(): LocationsDao

    companion object {
        const val DATABASE_NAME = "muteme.db"
        const val DATABASE_VERSION = 1
    }
}