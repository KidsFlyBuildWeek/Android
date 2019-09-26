package com.ali.kidsfly.fragment


import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali.kidsfly.R
import com.ali.kidsfly.SwipeRightToDelete
import com.ali.kidsfly.TripListAdapter
import com.ali.kidsfly.TripViewModel
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.ui.HomepageActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.floating_action_bar.view.*
import kotlinx.android.synthetic.main.fragment_current_trips.*
import java.lang.ref.WeakReference

class CurrentTrips : Fragment() {

    companion object {
        var tripSize = 0 //change upon loading in data from endpoint
    }
    private lateinit var tripListAdapter: TripListAdapter
    private lateinit var tripViewModel: TripViewModel
    private lateinit var swipeRightToDelete: SwipeRightToDelete

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_trips, container, false)

        tripViewModel = ViewModelProvider(requireActivity()).get(TripViewModel::class.java) //gets the view model from the attached activity
        tripListAdapter = TripListAdapter(mutableListOf()) //replace with current trips
        swipeRightToDelete = SwipeRightToDelete(tripListAdapter)

        setupRecyclerView(view)
        ItemTouchHelper(swipeRightToDelete).attachToRecyclerView(recycler_view)

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


    private fun updateCurrentTrips(it: MutableList<Trip>){
        tripSize = it.size

    }

//    inner class ReadAllAsyncTask(activity: Activity) : AsyncTask<Void, Void, LiveData<MutableList<Trip>>>(){
//
//        private val activity = WeakReference(activity)
//
//        override fun onPostExecute(result: LiveData<MutableList<Trip>>?) {
//            super.onPostExecute(result)
//        }
//
//        override fun doInBackground(vararg p0: Void?): LiveData<MutableList<Trip>> {
//            return (activity as HomepageActivity).tripViewModel.getAllTrips()
//        }
//    }
}
