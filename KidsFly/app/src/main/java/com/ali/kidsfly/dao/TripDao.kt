package com.ali.kidsfly.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ali.kidsfly.model.Trip

@Dao
interface TripDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createSavedTripEntry(trip: Trip)

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateTripEntry(trip: Trip)

//    @Query("SELECT * FROM trip")
//    fun getAllCurrentTrips(): LiveData<MutableList<Trip>>

    @Query("SELECT * FROM trip")
    fun getAllSavedTrips(): LiveData<MutableList<Trip>>

//    @Delete
//    fun deleteTrip(trip: Trip)
}