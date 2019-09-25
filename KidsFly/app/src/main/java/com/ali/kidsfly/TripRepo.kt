package com.ali.kidsfly

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.ali.kidsfly.model.Trip

class TripRepo(context: Context): TripDao {

    private val contxt = context.applicationContext

    override fun createTripEntry(trip: Trip) {
        database.tripDao().createTripEntry(trip)
    }

    override fun updateTripEntry(trip: Trip) {
        database.tripDao().updateTripEntry(trip)
    }

    override fun getAllTrips(): LiveData<MutableList<Trip>> {
        return database.tripDao().getAllTrips()
    }

    override fun deleteTrip(trip: Trip) {
        database.tripDao().deleteTrip(trip)
    }

    private val database by lazy{
        Room.databaseBuilder(
            contxt,
            TripDatabase::class.java,
            "entry_database"
        ).fallbackToDestructiveMigration().build()
    }
}