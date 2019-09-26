package com.ali.kidsfly

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.ali.kidsfly.model.Trip

class TripRepo(context: Context): TripDao {

    private val contxt = context.applicationContext

    override fun createTripEntry(trip: Trip) {
        if(trip.isCurrentTrip){
            currentTripsDatabase.tripDao().createTripEntry(trip) //what i really have to do is update the
        } else{
            savedTripsDatabase.tripDao().createTripEntry(trip)
        }
    }

    override fun updateTripEntry(trip: Trip) {
        if(trip.isCurrentTrip){
            currentTripsDatabase.tripDao().updateTripEntry(trip)
        } else{
            savedTripsDatabase.tripDao().updateTripEntry(trip)
        }
    }

    override fun getAllCurrentTrips(): LiveData<MutableList<Trip>> {
        return currentTripsDatabase.tripDao().getAllCurrentTrips()
    }

    override fun getAllSavedTrips(): LiveData<MutableList<Trip>> {
        return savedTripsDatabase.tripDao().getAllCurrentTrips()
    }

    override fun deleteTrip(trip: Trip) {
        if(trip.isCurrentTrip) {
            currentTripsDatabase.tripDao().deleteTrip(trip)
        }
    }

    private val currentTripsDatabase by lazy{
        Room.databaseBuilder(
            contxt,
            TripDatabase::class.java,
            "entry_database"
        ).fallbackToDestructiveMigration().build()
    }

    private val savedTripsDatabase by lazy{
        Room.databaseBuilder(
            contxt,
            TripDatabase::class.java,
            "entry_database2"
        ).fallbackToDestructiveMigration().build()
    }
}