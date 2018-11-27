package com.tanyayuferova.muteme.ui

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.tanyayuferova.muteme.R
import com.tanyayuferova.muteme.data.Location

/**
 * Author: Tanya Yuferova
 * Date: 11/27/2018
 */
class LocationsAdapter(
    private val listener: Listener?
) : RecyclerView.Adapter<LocationsAdapter.LocationsViewHolder>() {

    private var data: List<Location> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationsViewHolder {
        return LayoutInflater.from(parent.context)
            .inflate(R.layout.row_location, parent, false)
            .let(::LocationsViewHolder)
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(viewHolder: LocationsViewHolder, position: Int) {
        val item = data[position]
        with(viewHolder) {
            addressView.text = item.address
            itemView.setOnClickListener { listener?.onLocationClick(item.id) }
        }
    }

    fun setData(data: List<Location>) {
        this.data = data
        notifyDataSetChanged()
    }

    class LocationsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val addressView: TextView = view.findViewById(R.id.address)
    }

    interface Listener {
        fun onLocationClick(id: Long)
    }
}