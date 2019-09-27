package com.ali.kidsfly.repo

import android.app.Service
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import androidx.lifecycle.MutableLiveData
import com.ali.kidsfly.api.TripApi
import com.ali.kidsfly.api.UserApi
import com.ali.kidsfly.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.UnsupportedOperationException

class UserRepo() {

    val currentTrips = mutableListOf<Trip>()
    private val currentTripsLiveData = MutableLiveData <MutableList<Trip>>()
    val registrationHasBeenMade = MutableLiveData<Int>(-1) // change to ID of integer once we get a good response in PostUserProfileAsyncTask
    val userProfileFromSignIn = MutableLiveData<DownloadedUserProfile>()

    /*
        This is the creation, retrieval and updating of any user profile information
     */

    /*call this from within AppLauncherActivity, this will get the user profile, and then switch the screen to the homepage when done, passing
    the downloaded user profile through to that activity*/
    fun getUserProfile(parentId: Int): MutableLiveData<DownloadedUserProfile> {
        UserApi.getUserApiCall().getUserProfileInformation(parentId).enqueue(object : Callback<DownloadedUserProfile> {
            override fun onFailure(call: Call<DownloadedUserProfile>, t: Throwable) {
                userProfileFromSignIn.value = DownloadedUserProfile("", "", "", "", "", ""
                , "", mutableListOf(), -1, "")
            }
            override fun onResponse(call: Call<DownloadedUserProfile>, response: Response<DownloadedUserProfile>) {
                response.body()?.let{
                    userProfileFromSignIn.value = it
                }
            }
        })
        return userProfileFromSignIn
    }

    /*call this from within the RegistrationActivity this will post the user profile to the API, and will also go to the homepage after
     fetching the ID of the just created user profile*/
    fun registerUserProfile(user: UserProfile): MutableLiveData<Int>{
        UserApi.getUserApiCall().postUserProfile(user).enqueue( object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                throw t
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                response.body()?.let{
                    val s = response.headers().get("Location")
                    val i = s!!.substring(s.lastIndexOf("/") + 1).toInt()
                    registrationHasBeenMade.value = i
                }
            }
        })

        return registrationHasBeenMade
    }

    //updates the whole user profile
    fun updateUserProfile(user: UserProfile){
        //val intent = Intent(contxt, UpdateUserProfileApiService::class.java)
        //intent.putExtra("UserProfile", user)
        //contxt.startService(intent)
    }

    //we are going to read all the current trips and wrap it in live data so that we can observe any changes to the trips list in userprofile
    fun readAllCurrentTripsAsLiveData(user: UserProfile): MutableLiveData<MutableList<Trip>>{
        //add all the trips inside user profile to current trips
        (user as DownloadedUserProfile).trips.forEach {
            currentTrips.add(it)
        }
        currentTripsLiveData.value = currentTrips
        return currentTripsLiveData
    }

    fun addTripToCurrentTrips(trip: TripToPost){
        currentTrips.add(trip as Trip)
    }

    fun postTripToApi(trip: TripToPost){
        TripApi.getTripApiCall().postTrip(trip).enqueue(object: Callback<Unit>{
            override fun onFailure(call: Call<Unit>, t: Throwable) {
                throw t
            }

            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
            }
        })
    }

    /*
        Necessary async tasks and services to fetch and post api data
     */
    companion object {

        class PostUserProfileAsyncTask(var regMade: MutableLiveData<Int>) : AsyncTask<UserProfile, Void, Call<Unit>>() {

            override fun doInBackground(vararg p0: UserProfile?): Call<Unit> {
                return UserApi.getUserApiCall().postUserProfile(p0[0]!!)
            }

            override fun onPostExecute(result: Call<Unit>?) {
                result?.enqueue(object : Callback<Unit> {
                    override fun onFailure(call: Call<Unit>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        val s = response.headers().get("Location")
                        val i = s!!.substring(s.lastIndexOf("/") + 1).toInt()
                        regMade.value = i
                    }
                })
            }
        }

        class GetUserSignInAsyncTask(var userFromSignIn: MutableLiveData<DownloadedUserProfile?>) : AsyncTask<Int, Void, Call<DownloadedUserProfile>>() {

            override fun doInBackground(vararg p0: Int?): Call<DownloadedUserProfile> {
                return UserApi.getUserApiCall().getUserProfileInformation(p0[0]!!)
            }

            override fun onPostExecute(result: Call<DownloadedUserProfile>?) {
                result?.enqueue(object : Callback<DownloadedUserProfile> {
                    override fun onFailure(call: Call<DownloadedUserProfile>, t: Throwable) {
                        userFromSignIn.value = null
                    }

                    override fun onResponse(call: Call<DownloadedUserProfile>, response: Response<DownloadedUserProfile>) {
                        response.body()?.let{
                            userFromSignIn.value = it
                        }
                    }
                })
            }
        }
    }

    class UpdateUserProfileApiService: Service(){

        override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

            intent?.getSerializableExtra("UserProfile")?.let{
                val userProfile = it as DownloadedUserProfile
                UpdateUserProfile().execute(userProfile.parentid)
            }
            return super.onStartCommand(intent, flags, startId)
        }

        override fun onBind(p0: Intent?): IBinder? {
            throw(UnsupportedOperationException())
        }

        class UpdateUserProfile: AsyncTask<Int, Void, Unit>() {
            override fun doInBackground(vararg p0: Int?): Unit {
                return UserApi.getUserApiCall().updateUserProfile(p0[0]!!)
            }
        }
    }
}