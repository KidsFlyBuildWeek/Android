package com.ali.kidsfly.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

open class UserProfile(val username: String, val password: String, val phone: String, val email: String, val name: String, val address: String,
                       val airport: String = "IND") : Serializable


@Entity
class DownloadedUserProfile(username: String, password: String, phone: String, email: String, name: String, address: String,
                            airport: String, val trips: MutableList<Trip>, @PrimaryKey(autoGenerate = true) val parentid: String)
                            : UserProfile(username, password, phone, email, name, address, airport), Serializable

@Entity
class Trip(@PrimaryKey(autoGenerate = true) val tripid: Int, val date: String, val airport: String, val passengercount: Int, val childcount: Int,
           val luggagetype: String, val isCurrentTrip: Boolean = true)

