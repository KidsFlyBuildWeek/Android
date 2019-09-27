package com.ali.kidsfly.fragment


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ali.kidsfly.R
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_add_trip.*
import java.lang.ref.WeakReference
import java.util.*


class AddTripFragment : Fragment() {

    private var tripSize = 0 //change upon loading in data from endpoint
    private lateinit var tripViewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_trip, container, false)
        tripViewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java) //gets the view model from the attached activity

        val edit_date = view.findViewById<EditText>(R.id.edit_date)
        val edit_airport = view.findViewById<EditText>(R.id.et_airport)
        val edit_num_passengers = view.findViewById<EditText>(R.id.edit_num_passengers)
        val edit_num_children = view.findViewById<EditText>(R.id.edit_num_children)
        val edit_luggage = view.findViewById<EditText>(R.id.edit_luggage)

        edit_date.setOnClickListener {
            //adds calendar object to the relative layout
            val r = view.findViewById<RelativeLayout>(R.id.relative_layout)
            r.addView(createCalendarView(r))
        }

        view.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            activity!!.onBackPressed()
        }

        view.findViewById<Button>(R.id.btn_submit).setOnClickListener {
            if(edit_airport.text.toString() != "" && edit_date.text.toString() != "" && edit_num_passengers.text.toString() != ""
                && edit_num_children.text.toString() != "" && edit_luggage.text.toString() != ""){

                val trip = Trip(tripSize, edit_date.text.toString(), edit_airport.text.toString(), edit_num_passengers.text.toString().toInt(),
                    edit_num_children.text.toString().toInt(), edit_luggage.toString())

                //CreateAsyncTask(tripViewModel).execute(trip)
                activity!!.onBackPressed()
            }
        }
        return view
    }

    //returns a calendar with today's time as minimum time
    private fun createCalendarView(relativeLayout: RelativeLayout): CalendarView{
        val calendarView = CalendarView(activity as Context)
        calendarView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        calendarView.setMinDate(Date().time)
        (relativeLayout.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.BELOW, R.id.edit_date)

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            //Note that months are indexed from 0. So, 0 means january, 1 means February, 2 means march etc.
            //must add the date to the edit_date
            val dayMonth: String = dayOfMonth.toString()
            val mon: String = month.toString()

            edit_date.setText("$dayMonth / $mon / $year")
            relativeLayout.removeView(view) //get rid of calendar view
        }
        return calendarView
    }

    class CreateAsyncTask(viewModel: UserViewModel): AsyncTask<Trip, Void, Unit>(){
        private val viewModel = WeakReference(viewModel)

        override fun doInBackground(vararg trip: Trip?) {
            if(trip.isNotEmpty()){
                trip[0]?.let{
                    //viewModel.get()?
                    CurrentTrips.tripSize += 1
                }
            }
        }
    }

}
