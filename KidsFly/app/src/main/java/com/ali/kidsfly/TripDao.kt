package com.ali.kidsfly

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createTripEntry(trip: Trip)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTripEntry(trip: Trip)

    @Query("SELECT * FROM trip")
    fun getAllTrips(): LiveData<MutableList<Trip>>

    @Delete
    fun deleteTrip(trip: Trip)
}