package com.tanyayuferova.muteme.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Entity(tableName = "location")
data class LocationData (
    @PrimaryKey(autoGenerate = false) var id: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "address") var address: String
)