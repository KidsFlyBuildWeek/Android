package com.ali.kidsfly.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//i will post a new profile with this model using POST
open class UserProfile(val username: String, val password: String, val phone: String, val email: String, val name: String, val address: String,
                       val airport: String = "IND") : Serializable


class DownloadedUserProfile(username: String, password: String, phone: String, email: String, name: String, address: String,
                            airport: String, val trips: MutableList<TripWithId>, val parentid: Int, val status: String)
                            : UserProfile(username, password, phone, email, name, address, airport), Serializable


open class Trip(val date: String, val airport: String, val passengercount: Int, val childcount: Int,
           val luggagetype: String, val isCurrentTrip: Boolean = true)

//this is the trip that I will be posting
class TripToPost(date: String, airport: String, passengercount: Int, childcount: Int,
                 luggagetype: String, parentuser: DownloadedUserProfile)
                : Trip(date, airport, passengercount, childcount, luggagetype)

//this is the trip that we will receive when we download a new user profile
@Entity
class TripWithId(date: String, airport: String, passengercount: Int, childcount: Int,
                 luggagetype: String, isCurrentTrip: Boolean = true, @PrimaryKey val tripid: String)
                : Trip(date, airport, passengercount, childcount, luggagetype, isCurrentTrip)