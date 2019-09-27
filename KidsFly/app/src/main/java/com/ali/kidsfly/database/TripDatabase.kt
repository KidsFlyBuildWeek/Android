package com.ali.kidsfly.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ali.kidsfly.dao.TripDao
import com.ali.kidsfly.model.Trip

@Database(entities=arrayOf(Trip::class), version = 2, exportSchema = false)
abstract class TripDatabase: RoomDatabase() {
    abstract fun tripDao(): TripDao
}