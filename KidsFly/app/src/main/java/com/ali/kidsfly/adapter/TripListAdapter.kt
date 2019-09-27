package com.ali.kidsfly.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ali.kidsfly.R
import com.ali.kidsfly.model.Trip
import kotlinx.android.synthetic.main.fragment_add_trip.view.*
import kotlinx.android.synthetic.main.fragment_add_trip.view.tv_date
import kotlinx.android.synthetic.main.trip_view.view.*


class TripListAdapter(val data: MutableList<Trip>): RecyclerView.Adapter<TripListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvAirport = view.tv_airport_name_view
        val tvDate = view.tv_date_view
        val tvNumPassengers = view.tv_num_passengers_view
        val tvNumChildren = view.tv_num_children_view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvAirport.text = data[position].airport
        holder.tvDate.text = data[position].date
        holder.tvNumChildren.text = "# of Children: ${data[position].childcount}"
        holder.tvNumPassengers.text = "# of Passengers: ${data[position].passengercount}"
    }

    public fun removeItem(holder: ViewHolder) {
        val newPosition = holder.adapterPosition
        data.removeAt(newPosition)
        notifyItemRemoved(newPosition)
        notifyItemRangeChanged(newPosition, data.size)
    }
}