package com.tanyayuferova.muteme.di.activity

import android.app.Activity
import android.content.Context
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.tanyayuferova.muteme.business.geofence.Geofencing
import com.tanyayuferova.muteme.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Module
abstract class MainActivityModule {

    @Binds
    internal abstract fun provideActivity(activity: MainActivity): Activity

//    @Module
//    companion object {
//
//        @JvmStatic
//        @Provides
//        @Singleton
//        @ActivityScope
//        internal fun bindGoogleApiClient(context: Context): GoogleApiClient {
//            return GoogleApiClient.Builder(context)
////    .addConnectionCallbacks(context)
////    .addOnConnectionFailedListener(context)
//                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
////    .enableAutoManage(this, this)
//                .build()
//        }
//
//        @JvmStatic
//        @Provides
//        @Singleton
//        @ActivityScope
//        fun bindGeofencing(context: Context, googleApiClient: GoogleApiClient): Geofencing {
//            return Geofencing(context, googleApiClient)
//        }
//    }

}