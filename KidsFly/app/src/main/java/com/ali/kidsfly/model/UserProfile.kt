package com.ali.kidsfly.model

import java.io.Serializable

open class UserProfile(val username: String, val password: String, val phone: String, val email: String, val name: String, val address: String,
                       val airport: String = "IND") : Serializable

class DownloadedUserProfile(username: String, password: String, phone: String, email: String, name: String, address: String,
                            airport: String, val trips: MutableList<Trip>) : UserProfile(username, password, phone, email, name, address, airport), Serializable