package com.tanyayuferova.muteme

import android.app.Application
import com.tanyayuferova.muteme.di.application.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber.DebugTree
import timber.log.Timber

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
class MuteMeApplication : DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        initTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
            .builder()
            .create(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}