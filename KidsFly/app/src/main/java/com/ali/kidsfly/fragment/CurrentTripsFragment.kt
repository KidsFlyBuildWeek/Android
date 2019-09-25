package com.ali.kidsfly.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_trips, container, false)

        tripViewModel = ViewModelProvider(requireActivity()).get(TripViewModel::class.java) //gets the view model from the attached activity

        setupRecyclerView()

        val fab = (floating_action_layout as CoordinatorLayout).fab as FloatingActionButton

        tripListAdapter = TripListAdapter(mutableListOf())

        fab.setOnClickListener {
            //create the trip, add it to recyclerview
        }

        return view
    }

    private fun setupRecyclerView() {

        recycler_view.apply{
            layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
            adapter = tripListAdapter
            //addOnItemTouchListener(SwipeRightToDelete(tripListAdapter))
        }
    }
}
