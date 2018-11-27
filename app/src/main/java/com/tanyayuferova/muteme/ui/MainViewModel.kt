package com.tanyayuferova.muteme.ui

import android.arch.lifecycle.ViewModel
import com.tanyayuferova.muteme.data.LocationsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.arch.lifecycle.MutableLiveData
import com.tanyayuferova.muteme.data.Location
import com.tanyayuferova.muteme.mapList
import com.tanyayuferova.muteme.toLocation
import timber.log.Timber


/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
class MainViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository
) : ViewModel(), LocationsAdapter.Listener {

    private val disposes = CompositeDisposable()
    val favoriteShowsLiveData: MutableLiveData<List<Location>> = MutableLiveData()

    fun loadLocations() {
        disposes += locationsRepository.getAll()
            .mapList { it.toLocation() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { favoriteShowsLiveData.value = it },
                { Timber.e(it) }
            )
    }

    fun onAddLocationClick() {

    }

    override fun onLocationClick(id: Long) {
    }

    override fun onCleared() {
        disposes.dispose()
        super.onCleared()
    }
}