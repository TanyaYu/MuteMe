package com.tanyayuferova.muteme.business.geofence

import android.app.PendingIntent
import android.content.Context
import android.content.Intent

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.tanyayuferova.muteme.data.LocationData
import timber.log.Timber

import java.util.concurrent.TimeUnit


/**
 * Author: Tanya Yuferova
 * Date: 12/1/2018
 */

class Geofencing(
    private val mContext: Context,
    private val mGoogleApiClient: GoogleApiClient
)  {

    private val client = LocationServices.getGeofencingClient(mContext)

    private val mGeofenceList: MutableList<Geofence> = mutableListOf()
    private var mGeofencePendingIntent: PendingIntent? = null

    /***
     * Creates a GeofencingRequest object using the mGeofenceList ArrayList of Geofences
     * Used by `#registerGeofences`
     *
     * @return the GeofencingRequest object
     */
    private val geofencingRequest: GeofencingRequest
        get() {
            val builder = GeofencingRequest.Builder()
            builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            builder.addGeofences(mGeofenceList)
            return builder.build()
        }

    /***
     * Creates a PendingIntent object using the GeofenceTransitionsIntentService class
     * Used by `#registerGeofences`
     *
     * @return the PendingIntent object
     */
    private// Reuse the PendingIntent if we already have it.
    val geofencePendingIntent: PendingIntent
        get() {
            if (mGeofencePendingIntent != null) {
                return mGeofencePendingIntent!!
            }
            val intent = Intent(mContext, GeofenceBroadcastReceiver::class.java)
            mGeofencePendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            return mGeofencePendingIntent!!
        }

    init {
        mGeofencePendingIntent = null
        mGeofenceList.clear()
    }

    /***
     * Registers the list of Geofences specified in mGeofenceList with Google Place Services
     * Uses `#mGoogleApiClient` to connect to Google Place Services
     * Uses [.getGeofencingRequest] to get the list of Geofences to be registered
     * Uses [.getGeofencePendingIntent] to get the pending intent to launch the IntentService
     * when the Geofence is triggered
     * Triggers [.onResult] when the geofenc    es have been registered successfully
     */
    fun registerAllGeofences() {
        Timber.e("registerAllGeofences")
        // Check that the API client is connected and that the list has Geofences in it
        if (!mGoogleApiClient.isConnected || mGeofenceList.isEmpty()) {
            return
        }
        try {

            client.addGeofences(
                geofencingRequest,
                geofencePendingIntent
            ).addOnSuccessListener {
                Timber.e("success add")
            }
                .addOnCanceledListener {
                    Timber.e("add canceled")

                }
                .addOnFailureListener {
                    Timber.e("add failed")

                }
        } catch (securityException: SecurityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Timber.e(securityException)
        }

    }

    /***
     * Unregisters all the Geofences created by this app from Google Place Services
     * Uses `#mGoogleApiClient` to connect to Google Place Services
     * Uses [.getGeofencePendingIntent] to get the pending intent passed when
     * registering the Geofences in the first place
     * Triggers [.onResult] when the geofences have been unregistered successfully
     */
    fun unRegisterAllGeofences() {
        Timber.e("unRegisterAllGeofences")
        if (!mGoogleApiClient.isConnected) {
            return
        }
        try {
            client.removeGeofences(
                // This is the same pending intent that was used in registerGeofences
                geofencePendingIntent
            ).addOnSuccessListener {
                Timber.e("success add")
            }
                .addOnCanceledListener {
                    Timber.e("add canceled")

                }
                .addOnFailureListener {
                    Timber.e("add failed")

                }
        } catch (securityException: SecurityException) {
            // Catch exception generated if the app does not use ACCESS_FINE_LOCATION permission.
            Timber.e(securityException)
        }

    }


    /***
     * Updates the local ArrayList of Geofences using data from the passed in list
     * Uses the Place ID defined by the API as the Geofence object Id
     *
     * @param places the PlaceBuffer result of the getPlaceById call
     */
    fun updateGeofencesList(locations: List<LocationData>) {
        Timber.e("updateGeofencesList")
        mGeofenceList.clear()
        locations.forEach { location ->
            Geofence.Builder()
                .setRequestId(location.id)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setCircularRegion(location.lat, location.lng, GEOFENCE_RADIUS)
                .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER or Geofence.GEOFENCE_TRANSITION_EXIT)
                .build()
                .let(mGeofenceList::add)
        }
    }

    companion object {
        private const val GEOFENCE_RADIUS = 50f // meters
        private val GEOFENCE_TIMEOUT = TimeUnit.HOURS.toMillis(24)
    }

}
