package com.tanyayuferova.muteme.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Dao
interface LocationsDao {
    @Query("SELECT * from location")
    fun getAll(): Flowable<List<LocationData>>

    @Insert(onConflict = REPLACE)
    fun insert(location: LocationData)

    @Query("DELETE from location where id=:id")
    fun delete(id: String)
}