package com.tanyayuferova.muteme.viewmodel

import android.arch.lifecycle.ViewModel
import com.tanyayuferova.muteme.data.LocationsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import android.arch.lifecycle.MutableLiveData
import com.google.android.gms.location.places.Place
import com.tanyayuferova.muteme.geofence.Geofencing
import com.tanyayuferova.muteme.data.Location
import com.tanyayuferova.muteme.data.toLocation
import com.tanyayuferova.muteme.data.toLocationData
import com.tanyayuferova.muteme.ui.LocationsAdapter


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
            .subscribe { favoriteShowsLiveData.value = it }
    }

    override fun onLocationClick(id: String) {
    }

    fun onPlaceSelected(place: Place?) {
        if (place != null) {
            locationsRepository.put(place.toLocationData())
        }
    }

    fun onLocationSwiped(id: String) {
        locationsRepository.delete(id)
    }

    fun updateGeofences(geofencing: Geofencing, isLocationPermissionGranted: Boolean) {
        disposes += locationsRepository.getAll()
            .firstOrError()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                geofencing.updateGeofencesList(list)
                if(isLocationPermissionGranted) geofencing.registerAllGeofences()
                else geofencing.unRegisterAllGeofences()
            }
    }

    override fun onCleared() {
        disposes.dispose()
        super.onCleared()
    }
}