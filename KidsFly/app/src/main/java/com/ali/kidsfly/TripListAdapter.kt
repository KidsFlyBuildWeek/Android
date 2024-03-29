package com.ali.kidsfly

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ali.kidsfly.model.Trip
import java.nio.file.Files.size



class TripListAdapter(val data: MutableList<Trip>): RecyclerView.Adapter<TripListAdapter.ViewHolder>() {

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.trip_view, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    public fun removeItem(holder: TripListAdapter.ViewHolder) {
        val newPosition = holder.adapterPosition
        data.removeAt(newPosition)
        notifyItemRemoved(newPosition)
        notifyItemRangeChanged(newPosition, data.size)
    }
}