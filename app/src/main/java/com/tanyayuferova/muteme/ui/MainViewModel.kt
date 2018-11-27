package com.tanyayuferova.muteme.ui

import android.arch.lifecycle.ViewModel
import com.tanyayuferova.muteme.data.LocationsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.arch.lifecycle.MutableLiveData
import com.tanyayuferova.muteme.data.LocationData
import timber.log.Timber


/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
class MainViewModel @Inject constructor(
    private val locationsRepository: LocationsRepository
) : ViewModel() {

    private val disposes = CompositeDisposable()
    val favoriteShowsLiveData: MutableLiveData<List<LocationData>> = MutableLiveData()

    fun loadLocations() {
        disposes += locationsRepository.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { favoriteShowsLiveData.value = it },
                { Timber.e(it) }
            )
    }

    override fun onCleared() {
        disposes.dispose()
        super.onCleared()
    }
}