package com.ali.kidsfly.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.ali.kidsfly.R
import com.ali.kidsfly.SwipeRightToDelete
import com.ali.kidsfly.TripListAdapter
import com.ali.kidsfly.TripViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.floating_action_bar.view.*
import kotlinx.android.synthetic.main.fragment_current_trips.*

class CurrentTrips : Fragment() {

    private lateinit var tripListAdapter: TripListAdapter
    private lateinit var tripViewModel: TripViewModel
    private lateinit var swipeRightToDelete: SwipeRightToDelete

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_trips, container, false)

        tripViewModel = ViewModelProvider(requireActivity()).get(TripViewModel::class.java) //gets the view model from the attached activity
        tripListAdapter = TripListAdapter(mutableListOf())
        swipeRightToDelete = SwipeRightToDelete(tripListAdapter)

        setupRecyclerView()
        ItemTouchHelper(swipeRightToDelete).attachToRecyclerView(recycler_view)

        val fab = (floating_action_layout as CoordinatorLayout).fab as FloatingActionButton
        fab.setOnClickListener {
            //create the trip by opening up a pop-up fragment, and if trip is created, add it to recyclerview
        }

        return view
    }

    private fun setupRecyclerView() {

        recycler_view.apply{
            layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
            adapter = tripListAdapter
        }
    }
}
