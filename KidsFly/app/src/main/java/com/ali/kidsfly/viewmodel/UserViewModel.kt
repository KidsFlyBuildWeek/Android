package com.ali.kidsfly.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.ali.kidsfly.model.Trip
import com.ali.kidsfly.model.UserProfile
import com.ali.kidsfly.repo.UserRepo

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val userRepo = UserRepo(application)

    fun getCurrentUserTripsAsLiveData(user: UserProfile): MutableLiveData<MutableList<Trip>> {
        return userRepo.getAllCurrentTripsAsLiveData(user)
    }

    fun getUserProfile(parentId: Int) {
        userRepo.getUserProfile(parentId)
    }

    fun registerUserProfile(user: UserProfile){
        userRepo.registerUserProfile(user)
    }

    fun updateUserProfile(user: UserProfile){
        userRepo.updateUserProfile(user)
    }
}