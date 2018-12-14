package com.tanyayuferova.muteme.data

import com.google.android.gms.location.places.Place

/**
 * Author: Tanya Yuferova
 * Date: 11/30/2018
 */
fun LocationData.toLocation() = Location(
    id = id,
    name = name,
    address = address
)

fun Place.toLocationData() = LocationData(
    id = id,
    name = name.toString(),
    address = address?.toString().orEmpty(),
    lat = latLng.latitude,
    lng = latLng.longitude
)