package com.ali.kidsfly.fragment


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.ali.kidsfly.R
import kotlinx.android.synthetic.main.fragment_current_trips.*

class CurrentTrips : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_trips, container, false)
        setupRecyclerView()
        return view
    }

    private fun setupRecyclerView() {
//        recycler_view.apply{
//            layoutManager = LinearLayoutManager(activity as Context, LinearLayoutManager.VERTICAL, false)
//
//        }
    }
}
