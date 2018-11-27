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
    @PrimaryKey(autoGenerate = true) var id: Long,
    @ColumnInfo(name = "address") var address: String
)