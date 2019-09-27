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
import androidx.recyclerview.widget.RecyclerView
import com.ali.kidsfly.R
import com.ali.kidsfly.adapter.TripListAdapter
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_current_trips.*

class CurrentTrips : Fragment() {

    private lateinit var tripListAdapter: TripListAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_trips, container, false)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java) //gets the view model from the attached activity
        tripListAdapter = TripListAdapter(mutableListOf()) //replace with current trips

        setupRecyclerView(view)

        val fab = (view.findViewById<CoordinatorLayout>(R.id.floating_action_layout)).findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            //create the trip by opening up a pop-up fragment, and if trip is created, add it to recyclerview
            val fManager = activity!!.supportFragmentManager.beginTransaction()
            fManager.replace(R.id.container_for_add_trips, AddTripFragment())
            fManager.commit()
        }
        return view
    }

    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
            adapter = tripListAdapter
        }
    }
}
