package com.ali.kidsfly.adapter

import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.ali.kidsfly.R
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.trip_view.view.*


class TripListAdapter(userViewModel: UserViewModel, val data: MutableList<Trip>): RecyclerView.Adapter<TripListAdapter.ViewHolder>() {

    val uvm = userViewModel

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvAirport = view.tv_airport_name_view
        val tvDate = view.tv_date_view
        val tvNumPassengers = view.tv_num_passengers_view
        val tvNumChildren = view.tv_num_children_view
        val btn = view.btn_alert as Button
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

        holder.btn.setOnClickListener {
            //write this trip into the saved trips database and read it in past trips fragment
            CreateSavedTripAsyncTask(uvm).execute(data[position])
        }
    }

    companion object{

        class CreateSavedTripAsyncTask(userViewModel: UserViewModel): AsyncTask<Trip, Void, Unit>(){
            val uvm = userViewModel

            override fun doInBackground(vararg p0: Trip?): Unit {
                uvm.createSavedTripEntry(p0[0]!!)
            }
        }
    }
}