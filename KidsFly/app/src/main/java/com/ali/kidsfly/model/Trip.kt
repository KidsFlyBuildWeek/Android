package com.ali.kidsfly.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Trip(@PrimaryKey(autoGenerate = true) val tripid: Int, val date: String, val destination: String, val passengercount: Int, val childcount: Int,
           val luggagetype: String, val isCurrentTrip: Boolean = true){
}