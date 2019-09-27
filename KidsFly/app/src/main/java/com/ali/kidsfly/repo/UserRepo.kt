package com.ali.kidsfly.repo

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.IBinder
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.ali.kidsfly.R
import com.ali.kidsfly.api.TripApi
import com.ali.kidsfly.api.UserApi
import com.ali.kidsfly.dao.TripDao
import com.ali.kidsfly.database.TripDatabase
import com.ali.kidsfly.model.*
import com.ali.kidsfly.ui.AppLauncherActivity
import com.ali.kidsfly.ui.HomepageActivity
import com.ali.kidsfly.ui.RegisterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.UnsupportedOperationException
import java.lang.ref.WeakReference

class UserRepo(context: Context) {

    private val contxt = context.applicationContext
    val currentTrips = mutableListOf<Trip>()
    private val currentTripsLiveData = MutableLiveData <MutableList<Trip>>()

    /*
        This is the creation, retrieval and updating of any user profile information
     */

    /*call this from within AppLauncherActivity, this will get the user profile, and then switch the screen to the homepage when done, passing
    the downloaded user profile through to that activity*/
    fun getUserProfile(parentId: Int){
        GetUserSignInAsyncTask(contxt as Activity).execute(parentId)
    }

    /*call this from within the RegistrationActivity this will post the user profile to the API, and will also go to the homepage after
     fetching the ID of the just created user profile*/
    fun registerUserProfile(user: UserProfile){
        PostUserProfileAsyncTask(contxt as Activity).execute(user)
    }

    //updates the whole user profile
    fun updateUserProfile(user: UserProfile){
        val intent = Intent(contxt, UpdateUserProfileApiService::class.java)
        intent.putExtra("UserProfile", user)
        contxt.startService(intent)
    }

    //we are going to read all the current trips and wrap it in live data so that we can observe any changes to the trips list in userprofile
    fun readAllCurrentTripsAsLiveData(user: UserProfile): MutableLiveData<MutableList<Trip>>{
        //add all the trips inside user profile to current trips
        (user as DownloadedUserProfile).trips.forEach {
            val trip = it as Trip
            currentTrips.add(trip)
        }
        currentTripsLiveData.value = currentTrips
        return currentTripsLiveData
    }

    fun addTripToCurrentTrips(trip: TripToPost){
        currentTrips.add(trip as Trip)
    }

    fun postTripToApi(trip: TripToPost){
        PostTripApiAsyncTask(contxt as Activity).execute(trip)
    }

    /*
        Necessary async tasks and services to fetch and post api data
     */

    class PostUserProfileAsyncTask(activity: Activity) : AsyncTask<UserProfile, Void, Call<Unit>>() {
        private val act = WeakReference(activity)

        override fun doInBackground(vararg p0: UserProfile?): Call<Unit> {
            return UserApi.getUserApiCall().postUserProfile(p0[0]!!)
        }

        override fun onPostExecute(result: Call<Unit>?) {
            result?.let { res ->
                act.get()?.let {
                    it.findViewById<ProgressBar>(R.id.progress_bar).visibility = View.GONE

                    res.enqueue(object : Callback<Unit> {
                        override fun onFailure(call: Call<Unit>, t: Throwable) {
                            Toast.makeText(it, "User profile upload failed", Toast.LENGTH_SHORT).show()
                        }

                        override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                            val s = response.headers().get("Location")
                            val i = s!!.substring(s.lastIndexOf("/") + 1).toInt()
                            GetUserSignInAsyncTask(it).execute(i) //gets the post that's just created
                        }
                    })
                }
            }
        }
    }

    class GetUserSignInAsyncTask(activity: Activity) : AsyncTask<Int, Void, Call<DownloadedUserProfile>>(){
        private val act = WeakReference(activity)

        override fun doInBackground(vararg p0: Int?): Call<DownloadedUserProfile> {
            return UserApi.getUserApiCall().getUserProfileInformation(p0[0]!!)
        }

        override fun onPostExecute(result: Call<DownloadedUserProfile>?) {
            result?.let{
                it.enqueue(object: Callback<DownloadedUserProfile> {
                    override fun onFailure(call: Call<DownloadedUserProfile>, t: Throwable) {
                        act.get()?.let{
                            Toast.makeText(it, "Could not get this user profile", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onResponse(call: Call<DownloadedUserProfile>, response: Response<DownloadedUserProfile>) {
                        act.get()?.let{ x->
                            act.get()?.findViewById<ProgressBar>(R.id.progress_bar)?.visibility = View.GONE
                            response.body()?.let{
                                val intent = Intent(x as Context, HomepageActivity::class.java)
                                intent.putExtra("User", it)
                                x.startActivity(intent)
                            }
                        }
                    }
                })
            }
        }
    }

    class PostTripApiAsyncTask(activity: Activity): AsyncTask<TripToPost, Void, Call<Unit>>(){
        private val act = WeakReference(activity)

        override fun doInBackground(vararg trip: TripToPost?): Call<Unit> {
            return TripApi.getTripApiCall().postTrip(trip[0]!!)
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