package com.ali.kidsfly

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities=arrayOf(Trip::class), version = 2, exportSchema = false)
abstract class TripDatabase: RoomDatabase() {
    abstract fun tripDao(): TripDao
}