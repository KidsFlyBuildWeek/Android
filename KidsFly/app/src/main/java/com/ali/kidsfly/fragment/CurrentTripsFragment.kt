package com.ali.kidsfly.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali.kidsfly.R
import com.ali.kidsfly.adapter.TripListAdapter
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.ui.HomepageActivity
import com.ali.kidsfly.viewmodel.UserViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CurrentTrips : Fragment() {

    private lateinit var currentTripsAdapter: TripListAdapter
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_trips, container, false)

        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java) //gets the view model from the attached activity
        //will be able to observe any changes to the list in user if its modified
        setTripsAsObservable(view)
        currentTripsAdapter = TripListAdapter(userViewModel.getCurrentTrips())

        setupRecyclerView(view)

        val fab = (view.findViewById<CoordinatorLayout>(R.id.floating_action_layout)).findViewById<FloatingActionButton>(R.id.fab)

        fab.setOnClickListener {
            //create the trip by opening up a pop-up fragment, and if trip is created, add it to recyclerview
            val fManager = activity!!.supportFragmentManager.beginTransaction()
            fManager.replace(R.id.container_for_add_trips, AddTripFragment())
            fManager.addToBackStack(null)
            fManager.commit()
        }
        return view
    }

    private fun setTripsAsObservable(view: View) {
        userViewModel.getCurrentUserTripsAsLiveData(HomepageActivity.user).observe(this,
            object: Observer<MutableList<Trip>> {
                override fun onChanged(t: MutableList<Trip>?) {
                    currentTripsAdapter.notifyDataSetChanged()
                }
            })
    }

    private fun setupRecyclerView(view: View) {
        view.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
            adapter = currentTripsAdapter
        }
    }
}
