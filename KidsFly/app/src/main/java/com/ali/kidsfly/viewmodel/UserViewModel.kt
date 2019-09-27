package com.ali.kidsfly.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ali.kidsfly.model.DownloadedUserProfile
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.model.TripToPost
import com.ali.kidsfly.model.UserProfile
import com.ali.kidsfly.repo.UserRepo

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val userRepo = UserRepo()

    /*
        This is just for the user profiles
     */
    fun getUserProfile(parentId: Int): MutableLiveData<DownloadedUserProfile> {
        return userRepo.getUserProfile(parentId)
    }

    fun registerUserProfile(user: UserProfile): MutableLiveData<Int>{
        return userRepo.registerUserProfile(user)
    }

    fun updateUserProfile(user: UserProfile){
        userRepo.updateUserProfile(user)
    }

    /*
        This is for the trip related creation and posting
     */

    fun addTripsToCurrentTrips(trip: TripToPost){
        userRepo.addTripToCurrentTrips(trip)
    }

    fun postTripToApi(trip: TripToPost){
        userRepo.postTripToApi(trip)
    }

    fun getCurrentUserTripsAsLiveData(user: UserProfile): MutableLiveData<MutableList<Trip>> {
        return userRepo.readAllCurrentTripsAsLiveData(user)
    }

    fun getCurrentTrips(): MutableList<Trip>{
        return userRepo.currentTrips
    }
}