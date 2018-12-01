package com.tanyayuferova.muteme.di.application

import android.content.Context

import com.tanyayuferova.muteme.MuteMeApplication
import com.tanyayuferova.muteme.di.activity.ActivityScope
import com.tanyayuferova.muteme.di.activity.MainActivityModule
import com.tanyayuferova.muteme.ui.MainActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Module
abstract class ApplicationModule {
    @Binds
    internal abstract fun provideContext(application: MuteMeApplication): Context

    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    @ActivityScope
    internal abstract fun bindMainActivity(): MainActivity
}
