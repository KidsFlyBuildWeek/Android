package com.ali.kidsfly.fragment


import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ali.kidsfly.R
import com.ali.kidsfly.adapter.TripListAdapter
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.viewmodel.UserViewModel
import java.lang.ref.WeakReference

class PastTrips : Fragment() {

    lateinit var tripListAdapter: TripListAdapter
    lateinit var userViewModel: UserViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_past_trips, container, false)
        userViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)
        recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        ReadAllSavedTripsAsyncTask(viewLifecycleOwner, this, userViewModel).execute()
        return view
    }

    private fun updateSavedTrips(it: MutableList<Trip>) {
        tripListAdapter = TripListAdapter(userViewModel, it)
        tripListAdapter.notifyDataSetChanged()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val manager = LinearLayoutManager(activity!!.applicationContext, LinearLayoutManager.VERTICAL, false)

        recyclerView.apply {
            adapter = tripListAdapter
            layoutManager = manager
        }
    }

    class ReadAllSavedTripsAsyncTask(lifecycleOwner: LifecycleOwner, fragment: Fragment, userViewModel: UserViewModel): AsyncTask<Void, Void, LiveData<MutableList<Trip>>>(){
        val uvm = WeakReference(userViewModel)
        val life = WeakReference(lifecycleOwner)
        val frag = WeakReference(fragment)

        override fun doInBackground(vararg p0: Void?): LiveData<MutableList<Trip>> {
            return uvm.get()?.getAllSavedTrips()!!
        }

        override fun onPostExecute(result: LiveData<MutableList<Trip>>?) {
            result?.let{liv->
                life.get()?.let{ li->
                    liv.observe(li, object: Observer<MutableList<Trip>>{
                        override fun onChanged(t: MutableList<Trip>?) {
                            t?.let{
                                frag.get()?.let{fr ->
                                    (fr as PastTrips).updateSavedTrips(it)
                                }
                            }
                        }
                    })
                }
            }
        }
    }
}
