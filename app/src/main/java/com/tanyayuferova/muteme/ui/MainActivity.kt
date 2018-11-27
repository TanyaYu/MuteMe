package com.tanyayuferova.muteme.ui

import android.arch.lifecycle.Observer
import android.os.Bundle
import com.tanyayuferova.muteme.R
import javax.inject.Inject
import android.arch.lifecycle.ViewModelProvider
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import butterknife.BindView
import com.tanyayuferova.muteme.data.Location


class MainActivity : BaseActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override val layout: Int = R.layout.activity_main
    lateinit var viewModel: MainViewModel
    //todo fix view model
    private val locationsAdapter:LocationsAdapter by lazy { LocationsAdapter(viewModel) }

    @BindView(R.id.locations)
    lateinit var locationsView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        initLocationsView()

        viewModel.favoriteShowsLiveData.observe(this, Observer(::showLocations))
        viewModel.loadLocations()
    }

    private fun initLocationsView() = with(locationsView) {
        setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        adapter = locationsAdapter
//        addItemDecoration()
    }
    private fun showLocations(data: List<Location>?) {
        locationsAdapter.setData(data ?: emptyList())
    }
}