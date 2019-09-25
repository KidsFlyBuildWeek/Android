package com.ali.kidsfly

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.ali.kidsfly.model.Trip

class TripViewModel(application: Application): AndroidViewModel(application), TripDao {

    val tripRepo = TripRepo(application)

    override fun createTripEntry(trip: Trip) {
        tripRepo.createTripEntry(trip)
    }

    override fun updateTripEntry(trip: Trip) {
        tripRepo.updateTripEntry(trip)
    }

    override fun getAllTrips(): LiveData<MutableList<Trip>> {
        return tripRepo.getAllTrips()
    }

    override fun deleteTrip(trip: Trip) {
        tripRepo.deleteTrip(trip)
    }
}