package com.ali.kidsfly.model

import java.io.Serializable

data class UserProfile(val username: String, val password: String, val phone: String, val email: String, val name: String, val address: String,
                       val airport: String = "IND") : Serializable