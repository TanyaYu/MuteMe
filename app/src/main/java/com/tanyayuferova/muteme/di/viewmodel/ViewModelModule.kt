package com.tanyayuferova.muteme.di.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.tanyayuferova.muteme.ui.MainViewModel
import com.tanyayuferova.muteme.ui.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun postListViewModel(viewModel: MainViewModel): ViewModel
}