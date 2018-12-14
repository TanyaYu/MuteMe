package com.tanyayuferova.muteme.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.tanyayuferova.muteme.data.LocationData

/**
 * Author: Tanya Yuferova
 * Date: 12/1/2018
 */

class Geofencing(
    private val mContext: Context,
    private val mGoogleApiClient: GoogleApiClient
)  {

    private val geofencingClient = LocationServices.getGeofencingClient(mContext)
    private var gofenceList: List<Geofence> = emptyList()
    private var _geofencePendingIntent: PendingIntent? = null

    private val geofencingRequest: GeofencingRequest
        get() {
            val builder = GeofencingRequest.Builder()
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            builder.addGeofences(gofenceList)
            return builder.build()
        }

    private val geofencePendingIntent: PendingIntent
        get() {
            if (_geofencePendingIntent == null) {
                val intent = Intent(mContext, GeofenceBroadcastReceiver::class.java)
                _geofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
            return _geofencePendingIntent!!
        }

    fun registerAllGeofences() {
        if (!mGoogleApiClient.isConnected || gofenceList.isEmpty()) {
            return
        }
        try {
            geofencingClient.addGeofences(geofencingRequest, geofencePendingIntent)
        } catch (securityException: SecurityException) {
            securityException.printStackTrace()
        }
    }

    fun unRegisterAllGeofences() {
        if (!mGoogleApiClient.isConnected) {
            return
        }
        try {
            geofencingClient.removeGeofences(geofencePendingIntent)
        } catch (securityException: SecurityException) {
            securityException.printStackTrace()
        }
    }

    fun updateGeofencesList(locations: List<LocationData>) {
        gofenceList = locations.map { location ->
            Geofence.Builder()
                .setRequestId(location.id)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(location.lat, location.lng, GEOFENCE_RADIUS)
                .setTransitionTypes(GEOFENCE_TRANSITION_ENTER or GEOFENCE_TRANSITION_EXIT)
                .build()
        }
    }

    companion object {
        private const val GEOFENCE_RADIUS = 50f
    }
}
