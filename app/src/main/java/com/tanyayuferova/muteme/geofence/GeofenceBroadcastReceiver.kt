package com.tanyayuferova.muteme.geofence

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.media.AudioManager.RINGER_MODE_SILENT
import android.media.AudioManager.RINGER_MODE_NORMAL
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.TaskStackBuilder

import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.GeofencingEvent
import com.tanyayuferova.muteme.R
import com.tanyayuferova.muteme.ui.MainActivity
import com.tanyayuferova.muteme.ui.getDecodedResource

/**
 * Author: Tanya Yuferova
 * Date: 12/1/2018
 */
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition
        when (geofenceTransition) {
            GEOFENCE_TRANSITION_ENTER -> setRingerMode(context, RINGER_MODE_SILENT)
            GEOFENCE_TRANSITION_EXIT -> setRingerMode(context, RINGER_MODE_NORMAL)
            else -> return
        }
        sendNotification(context, geofenceTransition)
    }

    private fun sendNotification(context: Context, transitionType: Int) {
        val notificationIntent = Intent(context, MainActivity::class.java)
        val stackBuilder = TaskStackBuilder.create(context).apply {
            addParentStack(MainActivity::class.java)
            addNextIntent(notificationIntent)
        }
        val notificationPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val builder = NotificationCompat.Builder(context).apply {
            when (transitionType) {
                GEOFENCE_TRANSITION_ENTER -> setSmallIcon(R.drawable.ic_volume_off_white)
                    .setLargeIcon(context.getDecodedResource(R.drawable.ic_volume_off_white))
                    .setContentTitle(context.getString(R.string.silent_mode_activated))
                GEOFENCE_TRANSITION_EXIT -> setSmallIcon(R.drawable.ic_volume_up_white)
                    .setLargeIcon(context.getDecodedResource(R.drawable.ic_volume_up_white))
                    .setContentTitle(context.getString(R.string.back_to_normal))
            }
            setContentText(context.getString(R.string.touch_to_relaunch))
            setContentIntent(notificationPendingIntent)
            setAutoCancel(true)
        }
        val mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(0, builder.build())
    }

    private fun setRingerMode(context: Context, mode: Int) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT < 24 || Build.VERSION.SDK_INT >= 24 && !nm.isNotificationPolicyAccessGranted) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.ringerMode = mode
        }
    }
}
