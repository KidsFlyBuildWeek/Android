package com.ali.kidsfly.fragment


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_trip.*


class AddTripFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(com.ali.kidsfly.R.layout.fragment_add_trip, container, false)

        edit_date.setOnClickListener {
            //adds calendar object to the relative layout
            val relativeLayout = relative_layout as RelativeLayout
            val calendarView = CalendarView(activity as Context)

            calendarView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            (relativeLayout.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.BELOW, com.ali.kidsfly.R.id.edit_date)
            relativeLayout.addView(calendarView)

            calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
                //Note that months are indexed from 0. So, 0 means january, 1 means February, 2 means march etc.
                //must add the date to the edit_date
                
            }
        }

        return view
    }
}
