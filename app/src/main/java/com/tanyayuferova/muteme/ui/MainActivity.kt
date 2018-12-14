package com.tanyayuferova.muteme.ui

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.arch.lifecycle.Observer
import android.os.Bundle
import com.tanyayuferova.muteme.R
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import android.content.Intent
import android.content.pm.PackageManager
import android.support.design.widget.CoordinatorLayout
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.View
import android.widget.FrameLayout
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.location.places.ui.PlacePicker
import com.tanyayuferova.muteme.viewmodel.MainViewModel
import com.tanyayuferova.muteme.geofence.Geofencing
import com.tanyayuferova.muteme.data.Location
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity(), LocationsAdapter.Listener, GoogleApiClient.OnConnectionFailedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val googleApiClient by lazy {
        GoogleApiClient.Builder(this)
            .addApi(LocationServices.API)
            .addApi(Places.GEO_DATA_API)
            .enableAutoManage(this, this)
            .build()
    }
    private val geofencing: Geofencing get() = Geofencing(this, googleApiClient)
    lateinit var viewModel: MainViewModel
    private val locationsAdapter: LocationsAdapter by lazy { LocationsAdapter(this) }

    lateinit var locationsView: RecyclerView
    lateinit var coordinator: CoordinatorLayout
    lateinit var emptyView: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        initLocationsView()

        viewModel.favoriteShowsLiveData.observe(this, Observer(::showLocations))
        viewModel.loadLocations()
    }

    private fun findViews() {
        locationsView = findViewById(R.id.locations)
        coordinator = findViewById(R.id.coordinator)
        emptyView = findViewById(R.id.empty_view)
    }

    private fun initLocationsView() = with(locationsView) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        adapter = locationsAdapter
        ItemTouchHelper(LocationsTouchHelper()).attachToRecyclerView(this)
    }

    private fun showLocations(data: List<Location>?) {
        locationsAdapter.setData(data ?: emptyList())

        val isEmpty = data.isNullOrEmpty()
        emptyView.setGone(!isEmpty)
        locationsView.setGone(isEmpty)

        viewModel.updateGeofences(geofencing, isPermissionGranted(ACCESS_FINE_LOCATION))
    }

    fun onAddClick(view: View) {
        askForPermission()
    }

    private fun askForPermission() {
        when {
            isPermissionGranted(ACCESS_FINE_LOCATION) -> onPermissionGranted()
            isShouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION) -> explainPermission()
            else -> requestPermission()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACCESS_FINE_LOCATION),
            PERMISSIONS_REQUEST_FINE_LOCATION
        )
    }

    private fun explainPermission() {
        AlertDialog.Builder(this)
            .setTitle(R.string.explain_permission_title)
            .setMessage(R.string.explain_permission_message)
            .setPositiveButton(android.R.string.ok) { dialog, _ ->
                requestPermission()
            }
            .setNegativeButton(android.R.string.cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_FINE_LOCATION -> {
                if (grantResults.firstOrNull() == PackageManager.PERMISSION_GRANTED)
                    onPermissionGranted()
                else onPermissionDenied()
            }
        }
    }

    private fun onPermissionGranted() {
        try {
            val intent = PlacePicker.IntentBuilder().build(this)
            startActivityForResult(intent, PLACE_PICKER_REQUEST)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun onPermissionDenied() {
        coordinator.makeShortSnackbar(R.string.location_permission_denied_message)
    }

    override fun onLocationClick(id: String) {
        viewModel.onLocationClick(id)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST && resultCode == Activity.RESULT_OK) {
            viewModel.onPlaceSelected(PlacePicker.getPlace(this, data))
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

    inner class LocationsTouchHelper : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(parent: RecyclerView, viewHolder1: RecyclerView.ViewHolder, viewHolder2: RecyclerView.ViewHolder): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            locationsAdapter.getItem(viewHolder.adapterPosition)
                ?.let(Location::id)
                ?.let(viewModel::onLocationSwiped)
        }
    }

    companion object {
        const val PLACE_PICKER_REQUEST = 1
        const val PERMISSIONS_REQUEST_FINE_LOCATION = 111
    }
}