package com.tanyayuferova.muteme.di.activity

import android.app.Activity
import com.tanyayuferova.muteme.ui.MainActivity
import dagger.Binds
import dagger.Module

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Module
abstract class MainActivityModule {

    @Binds
    internal abstract fun provideActivity(activity: MainActivity): Activity
}