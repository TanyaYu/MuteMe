package com.tanyayuferova.muteme.data

import javax.inject.Inject

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
class LocationsRepository @Inject constructor(
    private val locationsDao: LocationsDao
) {
    fun getAll() = locationsDao.getAll()
}