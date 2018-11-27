package com.tanyayuferova.muteme.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.tanyayuferova.muteme.R
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import com.tanyayuferova.muteme.data.LocationData


class MainActivity : DaggerAppCompatActivity() {
// https://medium.com/mindorks/room-with-rxjava-and-dagger-2722f4420651
    // https://proandroiddev.com/viewmodel-with-dagger2-architecture-components-2e06f06c9455

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainViewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        mainViewModel.favoriteShowsLiveData.observe(this, Observer(::showLocations))
        mainViewModel.loadLocations()
    }

    private fun showLocations(data: List<LocationData>?) {
        data?.forEach { Timber.d(it.address) }
    }
}