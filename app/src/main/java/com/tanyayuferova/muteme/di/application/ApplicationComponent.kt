package com.tanyayuferova.muteme.di.application

import com.tanyayuferova.muteme.MuteMeApplication
import com.tanyayuferova.muteme.di.database.DatabaseModule
import com.tanyayuferova.muteme.di.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    DatabaseModule::class,
    ViewModelModule::class
])
//@ApplicationScope
@Singleton
interface ApplicationComponent : AndroidInjector<MuteMeApplication> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<MuteMeApplication>()
}