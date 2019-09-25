package com.ali.kidsfly.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Trip(@PrimaryKey(autoGenerate = true)val id: Int, val destination: String){

}