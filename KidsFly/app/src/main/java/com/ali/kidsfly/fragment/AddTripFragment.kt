package com.ali.kidsfly.fragment


import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.LinearLayout
import android.widget.RelativeLayout

import com.ali.kidsfly.R
import kotlinx.android.synthetic.main.fragment_add_trip.*

class AddTripFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_trip, container, false)

        edit_date.setOnClickListener {
            //adds calendar object to the relative layout
            val relativeLayout = relative_layout as RelativeLayout
            val calendarView = CalendarView(activity as Context)
            calendarView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)

            (relativeLayout.layoutParams as RelativeLayout.LayoutParams).addRule(RelativeLayout.BELOW, R.id.edit_date)
            relativeLayout.addView(calendarView)

            calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->

            }
        }

        return view
    }
}
