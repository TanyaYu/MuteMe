package com.tanyayuferova.muteme

import com.tanyayuferova.muteme.di.application.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
class MuteMeApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent
            .builder()
            .create(this)
    }
}