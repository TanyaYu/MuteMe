package com.tanyayuferova.muteme.di.database

import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
//@ApplicationScope
@Singleton
@Subcomponent(modules = [DatabaseModule::class])
interface DatabaseComponent {
}