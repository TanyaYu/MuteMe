package com.tanyayuferova.muteme.di.viewmodel

import android.arch.lifecycle.ViewModel
import com.tanyayuferova.muteme.business.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Author: Tanya Yuferova
 * Date: 12/14/2018
 */
@Module
abstract class MainViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

}